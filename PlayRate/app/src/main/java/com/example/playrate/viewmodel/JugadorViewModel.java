package com.example.playrate.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.playrate.model.Jugador;
import com.example.playrate.repository.JugadorRepository;
import java.util.List;
import java.util.Map;

public class JugadorViewModel extends AndroidViewModel {

    private final JugadorRepository repository;

    public JugadorViewModel(@NonNull Application application) {
        super(application);
        repository = new JugadorRepository();
    }

    public LiveData<List<Jugador>> getJugadoresPorEquipo(int equipoId) {
        MutableLiveData<List<Jugador>> jugadores = new MutableLiveData<>();
        repository.obtenerJugadoresPorEquipo(getApplication().getApplicationContext(), equipoId, jugadores);
        return jugadores;
    }
    public LiveData<Boolean> eliminarJugador(int jugadorId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        repository.eliminarJugador(getApplication().getApplicationContext(), jugadorId, result);
        return result;
    }
    public LiveData<Boolean> agregarJugador(String nombre, int equipoId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        repository.agregarJugador(getApplication().getApplicationContext(), nombre, equipoId, result);
        return result;
    }
    public LiveData<Boolean> actualizarValoracion(int jugadorId, Map<String, Integer> valoraciones) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        repository.actualizarValoracion(getApplication().getApplicationContext(), jugadorId, valoraciones, result);
        return result;
    }


}
