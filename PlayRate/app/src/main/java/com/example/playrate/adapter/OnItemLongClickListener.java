package com.example.playrate.adapter;

import com.example.playrate.model.Jugador;

public interface OnItemLongClickListener {
    void onDeleteClick(Jugador jugador);
    void onItemLongClick(Jugador jugador);
    void onItemClick(Jugador jugador);
}

