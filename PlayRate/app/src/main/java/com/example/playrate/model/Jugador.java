package com.example.playrate.model;

public class Jugador {
    private int id;
    private String nombre;
    private float valoracionMedia;

    public Jugador(int id, String nombre, float valoracionMedia) {
        this.id = id;
        this.nombre = nombre;
        this.valoracionMedia = valoracionMedia;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public float getValoracionMedia() { return valoracionMedia; }
}
