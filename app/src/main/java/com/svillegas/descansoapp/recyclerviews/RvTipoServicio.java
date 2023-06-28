package com.svillegas.descansoapp.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svillegas.descansoapp.R;
import com.svillegas.descansoapp.models.TipoServicio;

import java.util.List;

public class RvTipoServicio extends RecyclerView.Adapter<RvTipoServicio.ViewHolder>{

    Context context;
    List<TipoServicio> lTipoServicio;


    public RvTipoServicio(Context context, List<TipoServicio> lTipoServicio) {
        this.context = context;
        this.lTipoServicio = lTipoServicio;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rv_tipo_servicio, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTipoServicio.setText((lTipoServicio.get(position).nombre));
    }

    @Override
    public int getItemCount() { return lTipoServicio.size(); }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView txtTipoServicio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTipoServicio = itemView.findViewById(R.id.txtTipoServicio);
    }

    }
}