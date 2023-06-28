package com.svillegas.descansoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class OpcionesActivity extends AppCompatActivity {

    Button btnCrearPublicacion, btnCrearCategoria,
            btnCerrarSesion, btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        btnCrearPublicacion = findViewById(R.id.btnCrearPublicacion);

        btnCrearCategoria = findViewById(R.id.btnCrearCategoria);
        btnVolver = findViewById(R.id.btnVolver);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        FirebaseAuth fbAuth = FirebaseAuth.getInstance();

        btnCrearPublicacion.setOnClickListener(view -> {
            startActivity(new Intent(this, CrearPublicacionActivity.class));
        });



        btnCrearCategoria.setOnClickListener(view -> {
            startActivity(new Intent(this, CrearCategoriaActivity.class));
        });

        btnVolver.setOnClickListener(view -> {
            startActivity(new Intent(this, ServiciosActivity.class));
        });

        btnCerrarSesion.setOnClickListener(view -> {
            fbAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        });



    }
}
