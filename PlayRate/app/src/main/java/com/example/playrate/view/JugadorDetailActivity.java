package com.example.playrate.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.playrate.R;
import com.example.playrate.model.Jugador;
import com.example.playrate.utils.VolleySingleton;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JugadorDetailActivity extends AppCompatActivity {

    private TextView tvJugadorNombre, tvValoraciones;
    private Button btnEditarValoracion;
    private Jugador jugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador_detail);

        tvJugadorNombre = findViewById(R.id.tvJugadorNombre);
        tvValoraciones = findViewById(R.id.tvValoraciones);
        btnEditarValoracion = findViewById(R.id.btnEditarValoracion);

        // Recibir el objeto jugador desde el intent
        jugador = (Jugador) getIntent().getSerializableExtra("jugador");

        if (jugador != null) {
            mostrarInformacionJugador();
        } else {
            Toast.makeText(this, "Jugador no encontrado", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Configurar el botón de editar valoración
        btnEditarValoracion.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditarValoracionActivity.class);
            intent.putExtra("jugador_id", jugador.getId());
            startActivity(intent);
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        recargarEvaluaciones();
    }
    private void recargarEvaluaciones() {
        int jugadorId = jugador.getId();  // Usa el ID actual

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
                    mostrarInformacionJugador();  // vuelve a actualizar la vista
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error al recargar evaluaciones", Toast.LENGTH_SHORT).show();
                });

        VolleySingleton.getInstance(this).getRequestQueue().add(request);
    }

    private void mostrarInformacionJugador() {
        tvJugadorNombre.setText("Nombre: " + jugador.getNombre());

        StringBuilder valoraciones = new StringBuilder();
        Map<String, Integer> evaluaciones = jugador.getEvaluaciones();

        if (evaluaciones != null && !evaluaciones.isEmpty()) {
            for (Map.Entry<String, Integer> entry : evaluaciones.entrySet()) {
                valoraciones.append(entry.getKey())
                        .append(": ")
                        .append(entry.getValue())
                        .append("\n");
            }
            tvValoraciones.setText(valoraciones.toString());
        } else {
            tvValoraciones.setText("Sin valoraciones registradas");
        }
    }

}
