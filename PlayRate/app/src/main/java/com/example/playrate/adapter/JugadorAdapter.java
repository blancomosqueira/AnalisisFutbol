package com.example.playrate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.playrate.R;
import com.example.playrate.model.Jugador;
import java.util.List;

public class JugadorAdapter extends RecyclerView.Adapter<JugadorAdapter.ViewHolder> {

    private final Context context;
    private List<Jugador> jugadores;
    private final OnItemLongClickListener listener;

    public JugadorAdapter(Context context, List<Jugador> jugadores, OnItemLongClickListener listener) {
        this.context = context;
        this.jugadores = jugadores;
        this.listener = listener;
    }

    public void setJugadores(List<Jugador> nuevosJugadores) {
        this.jugadores = nuevosJugadores;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JugadorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jugador, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JugadorAdapter.ViewHolder holder, int position) {
        Jugador jugador = jugadores.get(position);
        holder.tvNombreJugador.setText(jugador.getNombre());
        holder.tvValoracion.setText("Valoración media: " + jugador.getValoracionMedia());

        holder.itemView.setOnLongClickListener(v -> {
            listener.onItemLongClick(jugador);
            return true;
        });

        holder.btnEliminarJugador.setOnClickListener(v -> {
            listener.onDeleteClick(jugador);
        });
        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(jugador);  // 👈 manejar click simple
        });
    }

    @Override
    public int getItemCount() {
        return jugadores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreJugador, tvValoracion;
        ImageButton btnEliminarJugador;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreJugador = itemView.findViewById(R.id.tvNombreJugador);
            tvValoracion = itemView.findViewById(R.id.tvValoracion);
            btnEliminarJugador = itemView.findViewById(R.id.btnEliminarJugador);
        }
    }
}
