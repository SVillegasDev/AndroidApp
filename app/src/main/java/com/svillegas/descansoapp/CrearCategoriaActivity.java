package com.svillegas.descansoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.svillegas.descansoapp.models.TipoServicio;
import com.svillegas.descansoapp.recyclerviews.RvTipoServicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrearCategoriaActivity extends AppCompatActivity {

    EditText txtCategoria;
    Button btnGuardarCategoria;
    RecyclerView rvTipoServicio;

    FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
    List<TipoServicio> datos = new ArrayList<>();
    RvTipoServicio adapterTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_categoria);

        txtCategoria = findViewById(R.id.txtCategoria);
        btnGuardarCategoria = findViewById(R.id.btnGuardarCategoria);
        rvTipoServicio = findViewById(R.id.rvTipoServicio);


        cargarDatos();
        btnGuardarCategoria.setOnClickListener(view -> {

            Map<String, Object> dato = new HashMap<>();
            dato.put("nombre", txtCategoria.getText().toString());
            dbStore.collection("tipo_servicio")
                    .add(dato)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            datos.add(new TipoServicio(txtCategoria.getText().toString()));
                            adapterTS.notifyDataSetChanged();
                            finish();
                            startActivity(new Intent(CrearCategoriaActivity.this, OpcionesActivity.class));
                            Toast.makeText(CrearCategoriaActivity.this, "Categoria ingresada", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }
    private void cargarDatos(){
        dbStore.collection("tipo_servicio")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for(QueryDocumentSnapshot documento : task.getResult()){
                            TipoServicio ts = new TipoServicio(String.valueOf(documento.get("nombre")));
                            datos.add(ts);
                        }
                        adapterTS = new RvTipoServicio(this, datos);
                        rvTipoServicio.setAdapter(adapterTS);
                        rvTipoServicio.setHasFixedSize(true);
                        rvTipoServicio.setLayoutManager(new LinearLayoutManager(this));
                    }
                });
    }
}
