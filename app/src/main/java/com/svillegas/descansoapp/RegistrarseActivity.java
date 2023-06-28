package com.svillegas.descansoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Timer;

public class RegistrarseActivity extends AppCompatActivity {

    EditText txtUsuario, txtCorreo, txtPassword;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        txtUsuario = findViewById(R.id.txtUsuario);
        txtCorreo = findViewById(R.id.txtcorreo);
        txtPassword = findViewById(R.id.txtpassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();



        btnRegistrar.setOnClickListener(view -> {

            String nombre = txtUsuario.getText().toString().trim();
            String correo = txtCorreo.getText().toString().trim();
            String pass = txtPassword.getText().toString().trim();

            if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty())
                Toast.makeText(RegistrarseActivity.this, "Complete los datos solicitados", Toast.LENGTH_SHORT).show();
            if (pass.length() < 6){
                Toast.makeText(RegistrarseActivity.this, "Contraseña debe contener minimo 6 caracteres", Toast.LENGTH_SHORT).show();

            }else{
                fbAuth.createUserWithEmailAndPassword(correo, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String id = fbAuth.getCurrentUser().getUid();
                        HashMap<String, Object> d = new HashMap<>();
                        d.put("id", id);
                        d.put("nombre", nombre);
                        d.put("email", correo);
                        d.put("password", pass);

                        dbStore.collection("usuarios")
                                .add(d)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(RegistrarseActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(RegistrarseActivity.this, MainActivity.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("Firebase ERROR", "No se ha podido registrar, intente mas tarde", e);
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ERROR EN EL REGISTRO", "Error al registrar", e);
                    }
                });
            }
        });

    }




}
