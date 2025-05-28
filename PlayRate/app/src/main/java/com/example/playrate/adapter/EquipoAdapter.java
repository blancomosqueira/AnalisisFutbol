package com.example.playrate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.playrate.R;
import com.example.playrate.model.Equipo;
import java.util.List;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.ViewHolder> {

    private List<Equipo> equipos;
    private final OnEquipoClickListener listener;

    public EquipoAdapter(List<Equipo> equipos, OnEquipoClickListener listener) {
        this.equipos = equipos;
        this.listener = listener;
    }

    public void setEquipos(List<Equipo> nuevos) {
        this.equipos = nuevos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_equipo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Equipo equipo = equipos.get(position);
        holder.tvNombreEquipo.setText(equipo.getNombre());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(equipo));
        holder.btnEliminarEquipo.setOnClickListener(v -> listener.onDeleteClick(equipo));
    }

    @Override
    public int getItemCount() {
        return equipos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreEquipo;
        ImageButton btnEliminarEquipo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreEquipo = itemView.findViewById(R.id.tvNombreEquipo);
            btnEliminarEquipo = itemView.findViewById(R.id.btnEliminarEquipo);
        }
    }
}
