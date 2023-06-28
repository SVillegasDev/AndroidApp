package com.svillegas.descansoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText txtMail, txtPass;
    Button btnIniciarSesion, btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMail = findViewById(R.id.txtMail);
        txtPass = findViewById(R.id.txtPass);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();

        btnIniciarSesion.setOnClickListener(view -> {

            String mail = txtMail.getText().toString().trim();
            String pass = txtPass.getText().toString().trim();

            if (mail.isEmpty() || pass.isEmpty()) {
                Toast.makeText(MainActivity.this, "Ingrese su correo y contraseña para iniciar sesión", Toast.LENGTH_SHORT).show();
            }else{fbAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        finish();
                        startActivity(new Intent(MainActivity.this, ServiciosActivity.class));
                    }else{
                        Toast.makeText(MainActivity.this, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            }
        });


        btnRegistrar.setOnClickListener(view -> {
            startActivity(new Intent(this, RegistrarseActivity.class));
        });
    }
}