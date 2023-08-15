package org.chakuy.chakuy.ui.incorporate;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.button.MaterialButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.chakuy.chakuy.R;
import org.chakuy.chakuy.databinding.FragmentIncorpBinding;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class IncorpFragment extends Fragment {

    private MaterialButton btnFechaNacimiento;
    private EditText nombrepost, telefonopost, ciudadpost;
    private ImageButton btn_registrar;
    private Spinner tipopost;

    private String namep, typep, telpost, cpost, fechaNacimiento;

    private FirebaseFirestore mfirestore;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentIncorpBinding binding = FragmentIncorpBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mfirestore = FirebaseFirestore.getInstance();

        nombrepost = root.findViewById(R.id.nombrepost);
        telefonopost = root.findViewById(R.id.telefonopost);
        ciudadpost = root.findViewById(R.id.ciudadpost);
        btn_registrar = root.findViewById(R.id.btn_registrar);
        tipopost = root.findViewById(R.id.tipopost);
        btnFechaNacimiento = root.findViewById(R.id.btnFechaNacimiento);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.selec_post, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipopost.setAdapter(adapter);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namep = nombrepost.getText().toString().trim();
                typep = tipopost.getSelectedItem().toString().trim();
                telpost = telefonopost.getText().toString().trim();
                cpost = ciudadpost.getText().toString().trim();
                fechaNacimiento = btnFechaNacimiento.getText().toString();

                if (namep.isEmpty() || typep.isEmpty() || telpost.isEmpty() || cpost.isEmpty()) {
                    Toast.makeText(requireContext(), "Ingresar todos los datos", Toast.LENGTH_SHORT).show();
                } else if (fechaNacimiento.equals("Seleccionar Fecha de Nacimiento")) {
                    Toast.makeText(requireContext(), "Seleccionar fecha de nacimiento", Toast.LENGTH_SHORT).show();
                } else {
                    saveRecord(namep, typep, telpost, cpost, fechaNacimiento);
                }
            }
        });

        // Configura el listener para el botón Fecha de Nacimiento
        btnFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        return root;
    }

    private void showDatePickerDialog() {
        // Obtiene la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Muestra el diálogo del selector de fecha
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Aquí puedes manejar la fecha seleccionada
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        btnFechaNacimiento.setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void saveRecord(String namep, String typep, String telpost, String cpost, String fechaNacimiento) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", namep);
        map.put("experiencia", typep);
        map.put("telefono", telpost);
        map.put("ciudad", cpost);
        map.put("fecha", fechaNacimiento);

        mfirestore.collection("postyunka").add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        showSuccessDialog(); // Mostrar el cuadro de diálogo de éxito
                        Toast.makeText(requireContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                        clearForm();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Error al Ingresar", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Registro Exitoso");
        builder.setMessage("El Departamento de Personal se pondrá en contacto contigo.");

        // Crear la cadena de texto con los datos registrados
        String message = "El Departamento de Personal se pondrá en contacto contigo.\n\n" +
                "Nombre: " + namep + "\n" +
                "Experiencia: " + typep + "\n" +
                "Teléfono: " + telpost + "\n" +
                "Ciudad: " + cpost + "\n" +
                "Fecha de Nacimiento: " + fechaNacimiento;

        builder.setMessage(message);

        // Agregar la imagen al cuadro de diálogo
        ImageView imageView = new ImageView(requireContext());
        imageView.setImageResource(R.drawable.yunka256); // Cambia "ic_success" al nombre de tu imagen
        builder.setView(imageView);

        builder.setPositiveButton("Aceptar", null); // Puedes agregar algún listener si lo deseas
        builder.create().show();
    }

    private void clearForm() {
        nombrepost.setText("");
        telefonopost.setText("");
        tipopost.setSelection(0);
        ciudadpost.setText("");
        btnFechaNacimiento.setText("Seleccionar Fecha de Nacimiento");
    }
}
