package com.example.playrate.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.playrate.R;
import com.example.playrate.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EvaluacionJugadorActivity extends AppCompatActivity {

    private Button btnGuardarEvaluacion;
    private Map<String, Integer> evaluaciones;
    private LinearLayout containerPrincipios;

    private String[] principiosTacticos = {
            // Principios ofensivos
            "Desmarque",
            "Apoyo",
            "Espacios de Juego",
            "Vigilancia Ofensiva",
            "Desdoblamiento Ofensivo",
            "Temporización Ofensiva",
            "Pared",
            "Cambios de Orientación",
            "Conservación del Balón (Posesión)",
            "Ayudas Ofensivas",
            "Velocidad Ofensiva",
            "Amplitud Ofensiva",
            "Progresión Ofensiva",
            "Profundidad Ofensiva",
            "Equilibrio Ofensivo",
            "Ritmos de Juego Ofensivos",
            "Movilidad Ofensiva",
            "Control del Juego Ofensivo",

            // Principios defensivos
            "Marcaje",
            "Vigilancia Defensiva",
            "Entradas al Poseedor",
            "Anticipaciones Defensivas",
            "Interceptaciones Defensivas",
            "Temporización Defensiva",
            "Repliegues Defensivos",
            "Coberturas Defensivas",
            "Permutas Defensivas",
            "Profundidad Defensiva",
            "Ventajas Numéricas en Defensa",
            "Velocidad Defensiva",
            "Presión Defensiva",
            "Equilibrio Defensivo",
            "Ritmos de Juego Defensivos",
            "Control del Juego Defensivo"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_jugadores);

        btnGuardarEvaluacion = findViewById(R.id.btnGuardarEvaluacion);
        containerPrincipios = findViewById(R.id.containerPrincipios);
        evaluaciones = new HashMap<>();

        // Generar los principios tácticos dinámicamente
        for (String principio : principiosTacticos) {
            addPrincipioToContainer(principio);
        }

        btnGuardarEvaluacion.setOnClickListener(v -> guardarEvaluacion());
    }

    private void addPrincipioToContainer(String principio) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_principio_tactico, containerPrincipios, false);
        TextView tvPrincipio = view.findViewById(R.id.tvPrincipio);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroupPrincipio);

        tvPrincipio.setText(principio);

        // Asignar IDs dinámicamente a los RadioButtons
        int[] radioButtonIds = new int[]{1001, 1002, 1003, 1004, 1005};

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            if (child instanceof RadioButton) {
                child.setId(radioButtonIds[i]);
            }
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int valor = getValoracion(checkedId);
            evaluaciones.put(principio, valor);
        });

        containerPrincipios.addView(view);
    }

    private int getValoracion(int checkedId) {
        switch (checkedId) {
            case 1001: return 1;
            case 1002: return 2;
            case 1003: return 3;
            case 1004: return 4;
            case 1005: return 5;
            default: return -1;
        }
    }



    private void guardarEvaluacion() {
        if (evaluaciones.size() < principiosTacticos.length) {
            Toast.makeText(this, "Complete todas las evaluaciones", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener el ID del jugador pasado por Intent
        int jugadorId = getIntent().getIntExtra("jugador_id", -1);
        Log.d("EVALUACION", "Jugador ID recibido: " + jugadorId);
        if (jugadorId == -1) {
            Toast.makeText(this, "Error: jugador no especificado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String url = "http://10.0.2.2:8000/api/guardar_evaluaciones/";

        // Crear el objeto JSON con evaluaciones
        JSONObject data = new JSONObject();
        JSONObject evaluacionesJson = new JSONObject();

        try {
            for (Map.Entry<String, Integer> entry : evaluaciones.entrySet()) {
                evaluacionesJson.put(entry.getKey(), entry.getValue());
            }

            data.put("jugador", jugadorId);
            data.put("evaluaciones", evaluacionesJson);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al preparar los datos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Enviar la solicitud
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, data,
                response -> {
                    Toast.makeText(this, "Evaluación guardada correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    error.printStackTrace();
                    Log.e("API_ERROR", "Volley error: " + error.toString());
                    Toast.makeText(this, "Error al guardar evaluación", Toast.LENGTH_SHORT).show();
                });

        VolleySingleton.getInstance(this).getRequestQueue().add(request);
    }

}
