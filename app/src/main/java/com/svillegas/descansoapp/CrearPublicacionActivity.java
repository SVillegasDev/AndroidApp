package com.svillegas.descansoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.svillegas.descansoapp.models.SpinnerData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrearPublicacionActivity extends AppCompatActivity {

    EditText txtNombre, txtUbicacion, txtTelefono, txtDescripcion;
    Button btnPublicar;
    Spinner spTipoServicio;

    FirebaseFirestore dbStore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_publicacion);

        txtNombre = findViewById(R.id.txtNombre);
        txtUbicacion = findViewById(R.id.txtUbicacion);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnPublicar = findViewById(R.id.btnPublicar);
        spTipoServicio = findViewById(R.id.spTipoServicio);

        cargarSpinner();
        btnPublicar.setOnClickListener(view -> {

            Map<String, Object> sMap = new HashMap<>();
            sMap.put("nombre", txtNombre.getText().toString());
            sMap.put("ubicacion", txtUbicacion.getText().toString());
            sMap.put("telefono", txtTelefono.getText().toString());
            sMap.put("descripcion", txtDescripcion.getText().toString());
            SpinnerData spinnerServicio = (SpinnerData) spTipoServicio.getSelectedItem();
            sMap.put("tipo_servicio", spinnerServicio.referencia);

            dbStore.collection("servicios")
                    .add(sMap)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Servicio publicado!!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(this, OpcionesActivity.class));
                        }
                    });
        });
    }

    private void cargarSpinner() {
        dbStore.collection("tipo_servicio")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<SpinnerData> tpSpinner = new ArrayList<>();
                        for (QueryDocumentSnapshot documento : task.getResult()) {
                            SpinnerData spinnerData = new SpinnerData(
                                    documento.getReference(),
                                    String.valueOf(documento.get("nombre"))
                            );
                            tpSpinner.add(spinnerData);
                        }
                        ArrayAdapter<SpinnerData> adapterSpinner = new ArrayAdapter<>(
                                this, android.R.layout.simple_spinner_dropdown_item, tpSpinner);
                        spTipoServicio.setAdapter(adapterSpinner);
                    }
                });
    }
}
