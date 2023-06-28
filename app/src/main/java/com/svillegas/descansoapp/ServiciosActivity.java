package com.svillegas.descansoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.svillegas.descansoapp.models.Servicios;
import com.svillegas.descansoapp.recyclerviews.RvServicios;

import java.util.ArrayList;
import java.util.List;

public class ServiciosActivity extends AppCompatActivity {

    Button btnOpciones;

    RecyclerView rvServicios;
    FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
    List<Servicios> datos = new ArrayList<>();
    RvServicios adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        btnOpciones = findViewById(R.id.btnOpciones);
        rvServicios = findViewById(R.id.rvServicios);


        dbStore.collection("servicios")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot servicio : task.getResult()) {
                            Servicios s = servicio.toObject(Servicios.class);
                            s.id = servicio.getId();
                            datos.add(s);
                        }


                        adapter = new RvServicios(this, datos);
                        rvServicios.setAdapter(adapter);
                        rvServicios.setHasFixedSize(true);
                        rvServicios.setLayoutManager(new LinearLayoutManager(this));
                    }
                });

        btnOpciones.setOnClickListener(view -> {
            startActivity(new Intent(this, OpcionesActivity.class));
        });
    }

}
