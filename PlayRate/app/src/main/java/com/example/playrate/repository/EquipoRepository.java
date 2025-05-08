package com.example.playrate.repository;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.playrate.model.Equipo;
import com.example.playrate.utils.VolleySingleton;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class EquipoRepository {

    private static final String URL = "http://10.0.2.2:8000/api/equipos/";

    public void obtenerEquipos(Context context, MutableLiveData<List<Equipo>> liveData) {
        System.out.println("EquipoRepository: Realizando solicitud a la API...");

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> {
                    List<Equipo> equipos = new ArrayList<>();
                    try {
                        System.out.println("EquipoRepository: Respuesta de la API: " + response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            int id = obj.getInt("id");
                            String nombre = obj.getString("nombre");
                            equipos.add(new Equipo(id, nombre));
                            System.out.println("EquipoRepository: Equipo recibido: " + nombre + " (ID: " + id + ")");
                        }
                        liveData.postValue(equipos);
                        System.out.println("EquipoRepository: Equipos cargados correctamente: " + equipos.size());
                    } catch (Exception e) {
                        System.out.println("EquipoRepository: Error al procesar la respuesta: " + e.getMessage());
                        e.printStackTrace();
                    }
                },
                error -> {
                    System.out.println("EquipoRepository: Error en la solicitud: " + error.getMessage());
                    error.printStackTrace();
                }
        );

        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }
}
