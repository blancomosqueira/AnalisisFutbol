package com.example.playrate.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.playrate.R;
import com.example.playrate.model.Jugador;
import com.example.playrate.utils.VolleySingleton;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EditarValoracionActivity extends AppCompatActivity {

    private LinearLayout containerPrincipios;
    private Button btnGuardarCambios;
    private Jugador jugador;
    private Map<String, RadioGroup> radioGroupMap = new HashMap<>();
    int jugadorId;

    private final String[] principiosTacticos = {
            "Desmarque", "Apoyo", "Espacios de Juego", "Vigilancia Ofensiva", "Desdoblamiento Ofensivo",
            "Temporización Ofensiva", "Pared", "Cambios de Orientación", "Conservación del Balón (Posesión)",
            "Ayudas Ofensivas", "Velocidad Ofensiva", "Amplitud Ofensiva", "Progresión Ofensiva",
            "Profundidad Ofensiva", "Equilibrio Ofensivo", "Ritmos de Juego Ofensivos", "Movilidad Ofensiva",
            "Control del Juego Ofensivo", "Marcaje", "Vigilancia Defensiva", "Entradas al Poseedor",
            "Anticipaciones Defensivas", "Interceptaciones Defensivas", "Temporización Defensiva",
            "Repliegues Defensivos", "Coberturas Defensivas", "Permutas Defensivas", "Profundidad Defensiva",
            "Ventajas Numéricas en Defensa", "Velocidad Defensiva", "Presión Defensiva", "Equilibrio Defensivo",
            "Ritmos de Juego Defensivos", "Control del Juego Defensivo"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_valoracion);

        containerPrincipios = findViewById(R.id.containerPrincipios);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);

        jugadorId = getIntent().getIntExtra("jugador_id", -1);

        if (jugadorId == -1) {
            Toast.makeText(this, "ID de jugador no encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        obtenerEvaluacionesDelJugador(jugadorId);

        btnGuardarCambios.setOnClickListener(v -> guardarCambios());
    }
    private void obtenerEvaluacionesDelJugador(int jugadorId) {
        String url = "http://10.0.2.2:8000/api/jugadores/" + jugadorId + "/evaluaciones/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Map<String, Integer> evaluaciones = new HashMap<>();
                    Iterator<String> keys = response.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        try {
                            int valor = response.getInt(key);
                            evaluaciones.put(key, valor);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    cargarPrincipiosConEvaluaciones(evaluaciones);
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error al obtener evaluaciones", Toast.LENGTH_SHORT).show();
                    finish();
                });

        VolleySingleton.getInstance(this).getRequestQueue().add(request);
    }

    private void cargarPrincipiosConEvaluaciones(Map<String, Integer> evaluaciones) {
        for (String principio : principiosTacticos) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_principio_tactico, containerPrincipios, false);
            TextView tvPrincipio = view.findViewById(R.id.tvPrincipio);
            RadioGroup radioGroup = view.findViewById(R.id.radioGroupPrincipio);
            tvPrincipio.setText(principio);

            if (evaluaciones.containsKey(principio)) {
                int valor = evaluaciones.get(principio);
                RadioButton rb = (RadioButton) radioGroup.getChildAt(valor - 1);
                if (rb != null) rb.setChecked(true);
            }

            radioGroupMap.put(principio, radioGroup);
            containerPrincipios.addView(view);
        }
    }


    private void guardarCambios() {
        Map<String, Integer> nuevasValoraciones = new HashMap<>();

        for (String principio : principiosTacticos) {
            RadioGroup group = radioGroupMap.get(principio);
            int checkedId = group.getCheckedRadioButtonId();
            if (checkedId == -1) {
                Toast.makeText(this, "Faltan evaluaciones en: " + principio, Toast.LENGTH_SHORT).show();
                return;
            }

            View radioButton = group.findViewById(checkedId);
            int index = group.indexOfChild(radioButton);
            nuevasValoraciones.put(principio, index + 1);
        }

        JSONObject jsonBody = new JSONObject(nuevasValoraciones);

        String url = "http://10.0.2.2:8000/api/jugadores/" + jugadorId + "/valoracion/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {
                    Toast.makeText(this, "Valoraciones actualizadas", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error al actualizar valoraciones", Toast.LENGTH_SHORT).show();
                });

        VolleySingleton.getInstance(this).getRequestQueue().add(request);
    }
}
