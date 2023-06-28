package com.svillegas.descansoapp.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.svillegas.descansoapp.R;
import com.svillegas.descansoapp.models.Servicios;

import java.util.List;

public class RvServicios extends RecyclerView.Adapter<RvServicios.ViewHolder>{

    Context context;
    List<Servicios> lServicios;

    public RvServicios(Context context, List<Servicios> lServicios) {
        this.context = context;
        this.lServicios = lServicios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rv_servicios, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Servicios servicio = lServicios.get(position);
        holder.tvNombre.setText(servicio.nombre);
        holder.tvUbicacion.setText("Ubicado en " + servicio.ubicacion);
        holder.tvTelefono.setText("Teléfono: " + servicio.telefono);
        holder.tvDescripcion.setText(servicio.descripcion);
        DocumentReference referencia = servicio.tipo_servicio;
        if (referencia != null) {
            referencia.addSnapshotListener((document, error) -> {
                if (document != null) {
                    holder.tvTipoServicio.setText(document.get("nombre").toString());
                }
            });
        } else {
            holder.tvTipoServicio.setText("");
        }
        holder.btnEliminar.setOnClickListener(view -> {
            Toast.makeText(context, "holi", Toast.LENGTH_SHORT).show();
            FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
            dbStore.collection("servicios")
                    .document(lServicios.get(position).id)
                    .delete()
                    .addOnCompleteListener(task -> {
                        lServicios.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Eliminación completada!", Toast.LENGTH_SHORT).show();
                    });
        });
    }


    @Override
    public int getItemCount() {
        return lServicios.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{

        TextView tvTipoServicio, tvNombre, tvUbicacion, tvTelefono, tvDescripcion;
        CardView cvServicio;
        Button btnEliminar;


        public ViewHolder(@NonNull View view){
            super(view);
            tvTipoServicio = view.findViewById(R.id.tvTipoServicio);
            tvNombre = view.findViewById(R.id.tvNombre);
            tvUbicacion = view.findViewById(R.id.tvUbicacion);
            tvTelefono = view.findViewById(R.id.tvTelefono);
            tvDescripcion = view.findViewById(R.id.tvDescripcion);
            cvServicio = view.findViewById(R.id.cvServicio);
            btnEliminar = view.findViewById(R.id.btnEliminar);
        }
    }
}
