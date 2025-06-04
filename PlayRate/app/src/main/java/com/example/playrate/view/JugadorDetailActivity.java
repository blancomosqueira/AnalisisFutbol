package com.example.playrate.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.playrate.R;
import com.example.playrate.model.Jugador;
import com.example.playrate.utils.VolleySingleton;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JugadorDetailActivity extends AppCompatActivity {

    private TextView tvJugadorNombre;
    private Button btnEditarValoracion;
    private LinearLayout layoutContainer;

    private Jugador jugador;
    private int jugadorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador_detail);

        tvJugadorNombre = findViewById(R.id.tvJugadorNombre);
        layoutContainer = findViewById(R.id.layoutContainer);
        btnEditarValoracion = findViewById(R.id.btnEditarValoracion);

        jugador = (Jugador) getIntent().getSerializableExtra("jugador");

        if (jugador != null) {
            jugadorId = jugador.getId(); // Guardamos el ID para futuras peticiones
            tvJugadorNombre.setText("Nombre: " + jugador.getNombre());
            mostrarInformacionJugador();
        } else {
            Toast.makeText(this, "Jugador no encontrado", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnEditarValoracion.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditarValoracionActivity.class);
            intent.putExtra("jugador_id", jugadorId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        recargarJugador();
        recargarEvaluaciones();
    }


    private void recargarEvaluaciones() {
        String url = "http://10.0.2.2:8000/api/jugadores/" + jugadorId + "/evaluaciones/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Map<String, Integer> evaluaciones = new HashMap<>();
                    Iterator<String> keys = response.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        try {
                            evaluaciones.put(key, response.getInt(key));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    jugador.setEvaluaciones(evaluaciones);
                    mostrarInformacionJugador();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error al recargar evaluaciones", Toast.LENGTH_SHORT).show();
                });

        VolleySingleton.getInstance(this).getRequestQueue().add(request);
    }
    private void recargarJugador() {
        String url = "http://10.0.2.2:8000/api/jugadores/" + jugadorId + "/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String nombre = response.getString("nombre");
                        double media = response.getDouble("valoracion_media");

                        jugador.setNombre(nombre);
                        jugador.setValoracionMedia((float) media);

                        TextView tvNombre = findViewById(R.id.tvJugadorNombre);
                        TextView tvMedia = findViewById(R.id.tvValoracionMedia);
                        if (tvNombre != null) {
                            tvNombre.setText("Nombre: " + nombre);
                        }
                        if (tvMedia != null) {
                            tvMedia.setText("Media: " + String.format("%.2f", media));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error al recargar datos del jugador", Toast.LENGTH_SHORT).show();
                });

        VolleySingleton.getInstance(this).getRequestQueue().add(request);
    }



    private void mostrarInformacionJugador() {

        LinkedHashMap<String, List<String>> principiosPorCategoria = new LinkedHashMap<>();

        principiosPorCategoria.put("MEDIOS TÉCNICO-TÁCTICOS DEL “JUEGO SIN BALÓN”", Arrays.asList(
                "Desmarque", "Apoyo", "Espacios de Juego", "Vigilancia Ofensiva", "Desdoblamiento Ofensivo"
        ));

        principiosPorCategoria.put("MEDIOS TÉCNICO-TÁCTICOS DEL “JUEGO CON BALÓN”", Arrays.asList(
                "Temporización Ofensiva", "Pared", "Cambios de Orientación", "Conservación del Balón (Posesión)"
        ));

        principiosPorCategoria.put("ESTILO DE JUEGO OFENSIVO PROPIO DEL EQUIPO", Arrays.asList(
                "Ayudas Ofensivas", "Velocidad Ofensiva", "Amplitud Ofensiva", "Progresión Ofensiva",
                "Profundidad Ofensiva", "Equilibrio Ofensivo", "Ritmos de Juego Ofensivos",
                "Movilidad Ofensiva", "Control del Juego Ofensivo"
        ));

        principiosPorCategoria.put("NEUTRALIZAR ATACANTES SIN BALÓN Y CONTROLAR ESPACIOS", Arrays.asList(
                "Marcaje", "Vigilancia Defensiva"
        ));

        principiosPorCategoria.put("ARREBATAR Y DIFICULTAR EVOLUCIONES CON BALÓN", Arrays.asList(
                "Entradas al Poseedor", "Anticipaciones Defensivas", "Interceptaciones Defensivas", "Temporización Defensiva"
        ));

        principiosPorCategoria.put("AYUDAS DEFENSIVAS Y REUBICACIÓN", Arrays.asList(
                "Repliegues Defensivos", "Coberturas Defensivas", "Permutas Defensivas"
        ));

        principiosPorCategoria.put("IDENTIDAD DEFENSIVA DEL EQUIPO", Arrays.asList(
                "Profundidad Defensiva", "Ventajas Numéricas en Defensa", "Velocidad Defensiva",
                "Presión Defensiva", "Equilibrio Defensivo", "Ritmos de Juego Defensivos",
                "Control del Juego Defensivo"
        ));

        layoutContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        Map<String, Integer> evaluaciones = jugador.getEvaluaciones();

        if (evaluaciones != null && !evaluaciones.isEmpty()) {
            for (Map.Entry<String, List<String>> categoria : principiosPorCategoria.entrySet()) {
                // Añadir título de categoría
                TextView titulo = new TextView(this);
                titulo.setText(categoria.getKey());
                titulo.setTextSize(18);
                titulo.setTextColor(getResources().getColor(android.R.color.black));
                titulo.setPadding(0, 24, 0, 12);
                layoutContainer.addView(titulo);

                // Añadir cada principio
                for (String principio : categoria.getValue()) {
                    View view = inflater.inflate(R.layout.item_valoracion, layoutContainer, false);

                    TextView tvNombrePrincipio = view.findViewById(R.id.tvNombrePrincipio);
                    ProgressBar barValoracion = view.findViewById(R.id.barValoracion);
                    TextView tvNivelEtiqueta = view.findViewById(R.id.tvNivelEtiqueta);

                    int valor = jugador.getEvaluaciones().getOrDefault(principio, 0);

                    tvNombrePrincipio.setText(principio);
                    barValoracion.setProgress(valor);

                    String etiqueta;
                    int color;

                    switch (valor) {
                        case 1:
                            etiqueta = "Muy bajo";
                            color = getResources().getColor(android.R.color.holo_red_dark);
                            break;
                        case 2:
                            etiqueta = "Bajo";
                            color = getResources().getColor(android.R.color.holo_orange_dark);
                            break;
                        case 3:
                            etiqueta = "Normal";
                            color = getResources().getColor(android.R.color.holo_orange_light);
                            break;
                        case 4:
                            etiqueta = "Bueno";
                            color = getResources().getColor(android.R.color.holo_green_light);
                            break;
                        case 5:
                            etiqueta = "Excelente";
                            color = getResources().getColor(android.R.color.holo_blue_dark);
                            break;
                        default:
                            etiqueta = "Sin datos";
                            color = getResources().getColor(android.R.color.darker_gray);
                    }

                    barValoracion.setProgressTintList(android.content.res.ColorStateList.valueOf(color));
                    tvNivelEtiqueta.setText(etiqueta);
                    tvNivelEtiqueta.setTextColor(color);

                    layoutContainer.addView(view);
                }
            }
        } else {
            TextView sinDatos = new TextView(this);
            sinDatos.setText("Sin valoraciones registradas");
            layoutContainer.addView(sinDatos);
        }
    }
}
