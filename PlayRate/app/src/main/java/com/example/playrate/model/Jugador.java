package com.example.playrate.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Jugador implements Serializable {
    private int id;
    private String nombre;
    private float valoracionMedia;
    private Map<String, Integer> evaluaciones;

    public Jugador(int id, String nombre, float valoracionMedia, Map<String, Integer> evaluaciones) {
        this.id = id;
        this.nombre = nombre;
        this.valoracionMedia = valoracionMedia;
        this.evaluaciones = evaluaciones;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public float getValoracionMedia() {
        return valoracionMedia;
    }

    public Map<String, Integer> getEvaluaciones() {
        return evaluaciones;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setValoracionMedia(float valoracionMedia) {
        this.valoracionMedia = valoracionMedia;
    }

    public void setEvaluaciones(Map<String, Integer> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public void addEvaluacion(String principio, int valor) {
        this.evaluaciones.put(principio, valor);
    }
}
