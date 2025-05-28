package com.example.playrate.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.playrate.model.Jugador;
import com.example.playrate.utils.VolleySingleton;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JugadorRepository {

    private static final String BASE_URL = "http://10.0.2.2:8000/api/jugadores/";

    public void obtenerJugadoresPorEquipo(Context context, int equipoId, MutableLiveData<List<Jugador>> liveData) {
        String url = BASE_URL + "?equipo=" + equipoId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Jugador> jugadores = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            int id = obj.getInt("id");
                            String nombre = obj.getString("nombre");
                            float valoracionMedia = (float) obj.getDouble("valoracion_media");

                            // Obtener evaluaciones si existen
                            Map<String, Integer> evaluaciones = new HashMap<>();
                            if (obj.has("evaluaciones") && !obj.isNull("evaluaciones")) {
                                JSONObject evaluacionesJson = obj.getJSONObject("evaluaciones");
                                Iterator<String> keys = evaluacionesJson.keys();
                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    evaluaciones.put(key, evaluacionesJson.getInt(key));
                                }
                            }

                            // Crear jugador con evaluaciones
                            jugadores.add(new Jugador(id, nombre, valoracionMedia, evaluaciones));
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

    public void eliminarJugador(Context context, int jugadorId, MutableLiveData<Boolean> result) {
        String url = "http://10.0.2.2:8000/api/jugadores/" + jugadorId + "/";
        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                response -> result.postValue(true),
                error -> result.postValue(false));

        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }
    public void agregarJugador(Context context, String nombre, int equipoId, MutableLiveData<Boolean> result) {
        String url = "http://10.0.2.2:8000/api/jugadores/";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> result.postValue(true),
                error -> result.postValue(false)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("equipo", String.valueOf(equipoId));
                return params;
            }
        };

        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }
    public void actualizarValoracion(Context context, int jugadorId, Map<String, Integer> valoraciones, MutableLiveData<Boolean> result) {
        String url = "http://10.0.2.2:8000/api/jugadores/" + jugadorId + "/valoracion/";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> result.postValue(true),
                error -> {
                    result.postValue(false);
                    Log.e("JugadorRepository", "Error al guardar valoraciones: " + error.getMessage());
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                for (Map.Entry<String, Integer> entry : valoraciones.entrySet()) {
                    params.put(entry.getKey(), String.valueOf(entry.getValue()));
                }
                return params;
            }
        };

        VolleySingleton.getInstance(context).getRequestQueue().add(request);
    }


}
