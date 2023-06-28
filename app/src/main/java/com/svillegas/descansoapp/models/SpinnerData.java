package com.svillegas.descansoapp.models;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentReference;

public class SpinnerData {

    public DocumentReference referencia;
    public String valor;

    public SpinnerData(DocumentReference referencia, String valor) {
        this.referencia = referencia;
        this.valor = valor;
    }

    @NonNull
    @Override
    public String toString() {return this.valor;}
}
