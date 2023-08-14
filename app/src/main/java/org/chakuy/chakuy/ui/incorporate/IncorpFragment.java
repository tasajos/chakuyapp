package org.chakuy.chakuy.ui.incorporate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.chakuy.chakuy.R;
import org.chakuy.chakuy.databinding.FragmentIncorpBinding;

import java.util.HashMap;
import java.util.Map;

public class IncorpFragment extends Fragment {

    private EditText nombrepost, telefonopost, ciudadpost;
    private Spinner tipopost;
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
        tipopost = root.findViewById(R.id.tipopost);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.selec_post, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipopost.setAdapter(adapter);

        binding.btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namep = nombrepost.getText().toString().trim();
                String typep = tipopost.getSelectedItem().toString().trim();
                String telpost = telefonopost.getText().toString().trim();
                String cpost = ciudadpost.getText().toString().trim();

                if (namep.isEmpty() || typep.isEmpty() || telpost.isEmpty() ||
                        cpost.isEmpty()) {
                    Toast.makeText(requireContext(), "Ingresar todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    saveRecord(namep, typep, telpost, cpost);
                }
            }
        });

        return root;
    }

    private void saveRecord(String namep, String typep, String telpost, String cpost) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", namep);
        map.put("experiencia", typep);
        map.put("telefono", telpost);
        map.put("ciudad", cpost);

        mfirestore.collection("postyunka").add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
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

    private void clearForm() {
        nombrepost.setText("");
        telefonopost.setText("");
        tipopost.setSelection(0);
        ciudadpost.setText("");
    }
}
