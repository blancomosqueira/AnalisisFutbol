package com.example.playrate.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.playrate.R;
import com.example.playrate.model.Jugador;
import com.example.playrate.viewmodel.JugadorViewModel;
import java.util.HashMap;
import java.util.Map;

public class EditarValoracionActivity extends AppCompatActivity {

    private LinearLayout containerPrincipios;
    private Button btnGuardarCambios;
    private Jugador jugador;
    private JugadorViewModel jugadorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_valoracion);

        containerPrincipios = findViewById(R.id.containerPrincipios);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);

        jugador = (Jugador) getIntent().getSerializableExtra("jugador");
        if (jugador != null) {
            cargarPrincipios();
        }

        btnGuardarCambios.setOnClickListener(v -> guardarCambios());
    }

    private void cargarPrincipios() {
        if (jugador == null || jugador.getEvaluaciones() == null) {
            Toast.makeText(this, "No hay valoraciones disponibles", Toast.LENGTH_SHORT).show();
            return;
        }

        for (Map.Entry<String, Integer> entry : jugador.getEvaluaciones().entrySet()) {
            View view = getLayoutInflater().inflate(R.layout.item_principio_tactico, containerPrincipios, false);
            TextView tvPrincipio = view.findViewById(R.id.tvPrincipio);
            RadioGroup radioGroup = view.findViewById(R.id.radioGroupPrincipio);

            tvPrincipio.setText(entry.getKey());
            int valor = entry.getValue();

            // Seleccionar el radio button correspondiente
            switch (valor) {
                case 1:
                    ((RadioButton) radioGroup.findViewById(R.id.rb1)).setChecked(true);
                    break;
                case 2:
                    ((RadioButton) radioGroup.findViewById(R.id.rb2)).setChecked(true);
                    break;
                case 3:
                    ((RadioButton) radioGroup.findViewById(R.id.rb3)).setChecked(true);
                    break;
                case 4:
                    ((RadioButton) radioGroup.findViewById(R.id.rb4)).setChecked(true);
                    break;
                case 5:
                    ((RadioButton) radioGroup.findViewById(R.id.rb5)).setChecked(true);
                    break;
                default:
                    break;
            }

            containerPrincipios.addView(view);
        }
    }

    private void guardarCambios() {
        // Crear un mapa de valoraciones
        Map<String, Integer> valoraciones = new HashMap<>();
        for (int i = 0; i < containerPrincipios.getChildCount(); i++) {
            View view = containerPrincipios.getChildAt(i);
            TextView tvPrincipio = view.findViewById(R.id.tvPrincipio);
            RadioGroup radioGroup = view.findViewById(R.id.radioGroupPrincipio);

            int checkedId = radioGroup.getCheckedRadioButtonId();
            int valor = getValoracion(checkedId);
            if (valor != -1) {
                valoraciones.put(tvPrincipio.getText().toString(), valor);
            }
        }

        if (valoraciones.isEmpty()) {
            Toast.makeText(this, "No hay valoraciones seleccionadas", Toast.LENGTH_SHORT).show();
            return;
        }

        jugadorViewModel.actualizarValoracion(jugador.getId(), valoraciones).observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Valoraciones actualizadas", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar valoraciones", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private int getValoracion(int checkedId) {
        if (checkedId == R.id.rb1) {
            return 1;
        } else if (checkedId == R.id.rb2) {
            return 2;
        } else if (checkedId == R.id.rb3) {
            return 3;
        } else if (checkedId == R.id.rb4) {
            return 4;
        } else if (checkedId == R.id.rb5) {
            return 5;
        } else {
            return -1;  // Valor no seleccionado o ID no reconocido
        }
    }

}
