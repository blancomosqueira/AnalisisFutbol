package com.example.playrate.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.playrate.model.Equipo;
import com.example.playrate.repository.EquipoRepository;

import java.util.List;

public class EquipoViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Equipo>> equipos = new MutableLiveData<>();
    private final EquipoRepository repository;

    public EquipoViewModel(@NonNull Application application) {
        super(application);
        System.out.println("EquipoViewModel: Constructor inicializado");
        repository = new EquipoRepository();
        cargarEquipos(application.getApplicationContext()); // carga inicial
    }

    public void cargarEquipos(Context context) {
        System.out.println("EquipoViewModel: Cargando equipos desde el repositorio...");
        repository.obtenerEquipos(context, equipos);
    }

    public LiveData<List<Equipo>> getEquipos() {
        return equipos;
    }

    public LiveData<Boolean> eliminarEquipo(int equipoId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        repository.eliminarEquipo(getApplication().getApplicationContext(), equipoId, result);
        return result;
    }
}
