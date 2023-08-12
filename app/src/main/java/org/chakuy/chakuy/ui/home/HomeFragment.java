package org.chakuy.chakuy.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.chakuy.chakuy.R;
//import org.chakuy.chakuy.login;
//import org.chakuy.chakuy.ui.verlista.slideshow.hbdbdata;
//import org.chakuy.chakuy.ui.verlista.slideshow.vlista;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private LinearLayout linearLayoutAgregar;
    private LinearLayout listado;
    private LinearLayout descargar;
    private LinearLayout cerrarsesion;

    private LinearLayout listadopc;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference hbdbRef = db.collection("chakuydb");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        linearLayoutAgregar = root.findViewById(R.id.linearLayoutAgregar);
        listado = root.findViewById(R.id.listado);
        descargar = root.findViewById(R.id.descargar);
        cerrarsesion = root.findViewById(R.id.cerrarsesion);
        listadopc = root.findViewById(R.id.listadopc);

        setupCardClickListeners();

        return root;
    }

    private void setupCardClickListeners() {
        linearLayoutAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navegar a GalleryFragment
                Navigation.findNavController(view).navigate(R.id.nav_gallery);
            }
        });


  //*      listado.setOnClickListener(new View.OnClickListener() {
     //       @Override
        //    public void onClick(View view) {
                // Acción para la tarjeta "Listado"
                // Aquí puedes implementar el comportamiento deseado
          //      Navigation.findNavController(view).navigate(R.id.nav_lista);
            //    Toast.makeText(getActivity(), "Cargando Listado.....", Toast.LENGTH_SHORT).show();
            //}
        //});

        //listadopc.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View view) {
                // Acción para la tarjeta "Listado PC"
                //Intent intent = new Intent(getActivity(), DatosPC.class);

              //  Navigation.findNavController(view).navigate(R.id.nav_pc);
            //}
        //});
        //descargar.setOnClickListener(new View.OnClickListener() {
          //  @Override
          //public void onClick(View view) {
                // Acción para la tarjeta "Descargar"
                // Aquí puedes implementar el comportamiento deseado

            //List<chakuydb> dataList = .
              //  new ArrayList<>();
             //Query query = hbdbRef;
             //query.get().addOnSuccessListener(queryDocumentSnapshots -> {
               //for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                 //chakuydb data = document.toObject(chakuydb.class);
               //dataList.add(data);
             //}

             //Workbook workbook = new XSSFWorkbook();
             //Sheet sheet = workbook.createSheet("Datos MTP");

             //int rowNum = 0;
             //for (chakuydb data : dataList) {
              //Row row = sheet.createRow(rowNum++);

             //row.createCell(0).setCellValue(data.getArea());
             //row.createCell(1).setCellValue(data.getDescripcion());
             //row.createCell(2).setCellValue(data.getEstado());
             //row.createCell(3).setCellValue(data.getNombre());
             //row.createCell(4).setCellValue(data.getFecha());

             }

             //String filePath = getExternalFilesDir(null).getPath().toString() + "/datos_hbdb.xlsx";
             //String filePath = requireActivity().getExternalFilesDir(null).getPath() + "/datos_registros.xlsx";
             //try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
             //workbook.write(outputStream);
             //} catch (IOException e) {
              //e.printStackTrace();
             //}

             //Uri fileUri = FileProvider.getUriForFile(requireContext(), requireContext().getApplicationContext().getPackageName() + ".provider", new File(filePath));

             //Intent intent = new Intent(Intent.ACTION_SEND);
             //intent.setType("application/vnd.ms-excel");
             //intent.putExtra(Intent.EXTRA_STREAM, fileUri);
             //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
             //startActivity(Intent.createChooser(intent, "Compartir archivo"));

             //});



             //Toast.makeText(getActivity(), "Descargando....espera unos segundos", Toast.LENGTH_SHORT).show();
            //}
        //});

        //cerrarsesion.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View view) {


                // Acción para la tarjeta "Cerrar Sesión"
              //  logout();
            //}
    //    });
    }

    //private void logout() {
      //  FirebaseAuth.getInstance().signOut();
        //Toast.makeText(getActivity(), "Cierre de sesión exitoso", Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(getActivity(), login.class));
        //getActivity().finish();
    //}
//}