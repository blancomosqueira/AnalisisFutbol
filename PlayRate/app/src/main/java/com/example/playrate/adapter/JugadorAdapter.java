package com.example.playrate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.playrate.R;
import com.example.playrate.model.Jugador;
import java.util.ArrayList;
import java.util.List;

public class JugadorAdapter extends RecyclerView.Adapter<JugadorAdapter.JugadorViewHolder> {

    private List<Jugador> jugadores;

    public JugadorAdapter() {
        this.jugadores = new ArrayList<>();
    }

    @NonNull
    @Override
    public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jugador, parent, false);
        return new JugadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JugadorViewHolder holder, int position) {
        Jugador jugador = jugadores.get(position);
        holder.tvNombreJugador.setText(jugador.getNombre());
        holder.tvValoracion.setText("Valoración: " + jugador.getValoracionMedia());
    }

    @Override
    public int getItemCount() {
        return jugadores.size();
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        notifyDataSetChanged();
    }

    static class JugadorViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreJugador, tvValoracion;

        public JugadorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreJugador = itemView.findViewById(R.id.tvNombreJugador);
            tvValoracion = itemView.findViewById(R.id.tvValoracion);
        }
    }
}
