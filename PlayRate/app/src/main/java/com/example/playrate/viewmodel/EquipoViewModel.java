package com.example.playrate.viewmodel;

import android.app.Application;
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
        cargarEquipos(application);
    }

    private void cargarEquipos(Application application) {
        System.out.println("EquipoViewModel: Cargando equipos desde el repositorio...");
        repository.obtenerEquipos(application.getApplicationContext(), equipos);
    }

    public MutableLiveData<List<Equipo>> getEquipos() {
        cargarEquipos(getApplication());
        return equipos;
    }
    public LiveData<Boolean> eliminarEquipo(int equipoId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        repository.eliminarEquipo(getApplication().getApplicationContext(), equipoId, result);
        return result;
    }


}
