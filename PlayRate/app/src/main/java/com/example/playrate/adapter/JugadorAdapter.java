package com.example.playrate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.playrate.R;
import com.example.playrate.model.Jugador;
import com.example.playrate.view.JugadorDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class JugadorAdapter extends RecyclerView.Adapter<JugadorAdapter.JugadorViewHolder> {

    private Context context;
    private List<Jugador> jugadores;
    private OnItemLongClickListener longClickListener;

    public JugadorAdapter(Context context, List<Jugador> jugadores, OnItemLongClickListener longClickListener) {
        this.context = context;
        this.jugadores = jugadores;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jugador, parent, false);
        return new JugadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JugadorViewHolder holder, int position) {
        Jugador jugador = jugadores.get(position);
        holder.tvNombreJugador.setText(jugador.getNombre());

        // Manejar el clic en el elemento del RecyclerView
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, JugadorDetailActivity.class);
            intent.putExtra("jugador", jugador);  // Asegúrate de que el objeto jugador sea serializable
            context.startActivity(intent);
        });

        // Manejar el clic largo en el elemento
        holder.itemView.setOnLongClickListener(v -> {
            longClickListener.onItemLongClick(jugador);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return jugadores.size();
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores.clear();
        this.jugadores.addAll(jugadores);
        notifyDataSetChanged();
    }

    public static class JugadorViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreJugador;

        public JugadorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreJugador = itemView.findViewById(R.id.tvNombreJugador);
        }
    }
}
