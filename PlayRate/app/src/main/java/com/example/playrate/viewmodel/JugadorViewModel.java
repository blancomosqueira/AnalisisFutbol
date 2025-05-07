package com.example.playrate.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.playrate.model.Jugador;
import com.example.playrate.repository.JugadorRepository;

import java.util.List;

public class JugadorViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Jugador>> jugadores = new MutableLiveData<>();
    private final JugadorRepository repository;

    public JugadorViewModel(@NonNull Application application) {
        super(application);
        repository = new JugadorRepository();
        repository.obtenerJugadores(application.getApplicationContext(), jugadores);
    }

    public MutableLiveData<List<Jugador>> getJugadores() {
        return jugadores;
    }
}
