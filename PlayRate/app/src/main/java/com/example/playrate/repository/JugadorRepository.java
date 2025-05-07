package com.example.playrate.repository;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.playrate.model.Jugador;
import com.example.playrate.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JugadorRepository {

    private static final String URL = "http://10.0.2.2:8000/api/jugadores/";

    public void obtenerJugadores(Context context, MutableLiveData<List<Jugador>> liveData) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> {
                    List<Jugador> jugadores = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            int id = obj.getInt("id");
                            String nombre = obj.getString("nombre");
                            float media = (float) obj.getDouble("valoracion_media");

                            jugadores.add(new Jugador(id, nombre, media));
                        }
                        liveData.postValue(jugadores);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()
        );

        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }
}
