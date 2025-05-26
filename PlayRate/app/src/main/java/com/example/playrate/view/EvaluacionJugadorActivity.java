package com.example.playrate.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.playrate.R;

import java.util.HashMap;
import java.util.Map;

public class EvaluacionJugadorActivity extends AppCompatActivity {

    private Button btnGuardarEvaluacion;
    private Map<String, Integer> evaluaciones;
    private LinearLayout containerPrincipios;

    private String[] principiosOfensivos = {
            "Movilidad", "Apoyo", "Desmarque", "Finalización", "Amplitud",
            "Profundidad", "Cambio de Ritmo", "Creatividad", "Cobertura",
            "Atraer al Rival", "Desborde", "Desmarque de Apoyo", "Desmarque de Ruptura",
            "Fijación del Rival", "Rotación", "Ocupación de Espacios", "Posesión",
            "Juego en Equipo"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_jugadores);

        btnGuardarEvaluacion = findViewById(R.id.btnGuardarEvaluacion);
        containerPrincipios = findViewById(R.id.containerPrincipios);
        evaluaciones = new HashMap<>();

        // Generar los principios tácticos dinámicamente
        for (String principio : principiosOfensivos) {
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
        if (evaluaciones.size() < principiosOfensivos.length) {
            Toast.makeText(this, "Complete todas las evaluaciones", Toast.LENGTH_SHORT).show();
            return;
        }

        // Guardar en la base de datos (a implementar)
        Toast.makeText(this, "Evaluación guardada", Toast.LENGTH_SHORT).show();
        finish();
    }
}
