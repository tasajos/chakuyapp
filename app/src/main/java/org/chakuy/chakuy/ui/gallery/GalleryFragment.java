package org.chakuy.chakuy.ui.gallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.chakuy.chakuy.R;
import org.chakuy.chakuy.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment implements OnMapReadyCallback {

    private static final int PERMISSION_REQUEST_CODE = 200;

    private EditText nombre, descripcion, ubicacion, fecha;
    private Button btnalarma;

    private ImageButton btn_registrar;
    private Spinner tipoSpinner;

    private FirebaseFirestore mfirestore;
    private FusedLocationProviderClient fusedLocationClient;
    private Location lastKnownLocation;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentGalleryBinding binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        mfirestore = FirebaseFirestore.getInstance();

        // Find views by their IDs
        nombre = root.findViewById(R.id.nombrepost);
        tipoSpinner = root.findViewById(R.id.tipopost);
        descripcion = root.findViewById(R.id.telefonopost);
        ubicacion = root.findViewById(R.id.ciudadpost);
        fecha = root.findViewById(R.id.fechanacimientopost);
        btn_registrar = root.findViewById(R.id.btn_registrar);
        btnalarma = root.findViewById(R.id.btnalarma);

        // Set up the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.tipos_producto, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoSpinner.setAdapter(adapter);

        // Set up the click listeners
        btnalarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = ubicacion.getText().toString();
                // Envía mensajes de WhatsApp si es necesario
                //sendWhatsAppMessages(Arrays.asList("+59177087685", "+59163962491"), message);
            }
        });

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nombre.getText().toString().trim();
                String type = tipoSpinner.getSelectedItem().toString().trim();
                String quantity = descripcion.getText().toString().trim();
                String locationValue = ubicacion.getText().toString().trim();
                String date = fecha.getText().toString().trim();

                if (name.isEmpty() || type.isEmpty() ||  quantity.isEmpty() ||
                        locationValue.isEmpty() || date.isEmpty()) {
                    Toast.makeText(requireContext(), "Ingresar todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    saveRecord(name, type, quantity, locationValue, date);

                    // Enviar mensaje de WhatsApp
                    String whatsappMessage = "Nombre: " + name + "\n" +
                            "Tipo: " + type + "\n" +
                            "Cantidad: " + quantity + "\n" +
                            "Ubicación: " + locationValue + "\n" +
                            "Fecha: " + date;

                    sendWhatsAppMessage("+59168503758", whatsappMessage);
                }
            }
        });

        // Obtener fecha actual
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentDate = sdf.format(new Date());

        // Establecer fecha actual en EditText y desactivar edición
        fecha.setText(currentDate);
        fecha.setEnabled(false);

        ubicacion.setEnabled(false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView2);
        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        updateMapLocation();
    }

    private void updateMapLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                lastKnownLocation = location;
                                ubicacion.setText(location.getLatitude() + ", " + location.getLongitude());
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                googleMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                            }
                        }
                    });
        }
    }

    private void saveRecord(String name, String type, String quantity, String locationValue, String date) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", name);

        map.put("tipo", type);

        map.put("cantidad", quantity);

        map.put("ubicacion", locationValue);
        map.put("fecha", date);

        mfirestore.collection("chakuydb").add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(requireContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                        clearForm(); // Limpia el formulario después de guardar
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Error al Ingresar", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearForm() {
        // Restablecer los campos de texto a cadenas vacías
        nombre.setText("");
        descripcion.setText("");
        ubicacion.setText("");

        // Restablecer el spinner a la posición 0 (primer elemento)
        tipoSpinner.setSelection(0);

        // Actualizar el mapa para mostrar la ubicación actual
        updateMapLocation();
    }

    private void sendWhatsAppMessage(String phoneNumber, String message) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + message));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error al enviar el mensaje de WhatsApp", Toast.LENGTH_SHORT).show();
        }
    }
}
