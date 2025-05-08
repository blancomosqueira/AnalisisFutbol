package com.example.playrate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.playrate.R;
import com.example.playrate.model.Equipo;
import java.util.List;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder> {

    private List<Equipo> equipos;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Equipo equipo);
    }

    public EquipoAdapter(List<Equipo> equipos, OnItemClickListener listener) {
        this.equipos = equipos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EquipoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_equipo, parent, false);
        return new EquipoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipoViewHolder holder, int position) {
        Equipo equipo = equipos.get(position);
        holder.tvNombreEquipo.setText(equipo.getNombre());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(equipo));
    }

    @Override
    public int getItemCount() {
        return equipos.size();
    }

    public static class EquipoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreEquipo;

        public EquipoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreEquipo = itemView.findViewById(R.id.tvNombreEquipo);
        }
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
        notifyDataSetChanged();
    }
}
