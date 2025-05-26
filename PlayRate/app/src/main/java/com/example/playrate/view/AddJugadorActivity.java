package com.example.playrate.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.playrate.R;
import com.example.playrate.model.Equipo;
import com.example.playrate.utils.VolleySingleton;
import com.example.playrate.viewmodel.EquipoViewModel;
import com.example.playrate.viewmodel.JugadorViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddJugadorActivity extends AppCompatActivity {

    private EditText et_nombre_jugador;
    private Spinner spinnerEquipos;
    private Button btn_guardar_jugador;
    private Button btn_evaluar;
    private List<Equipo> listaEquipos = new ArrayList<>();
    private EquipoViewModel equipoViewModel;
    private JugadorViewModel jugadorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jugador);

        spinnerEquipos = findViewById(R.id.spinnerEquipos);
        btn_guardar_jugador = findViewById(R.id.btn_guardar_jugador);
        btn_evaluar = findViewById(R.id.btn_evaluar_jugador);
        et_nombre_jugador = findViewById(R.id.et_nombre_jugador);
        jugadorViewModel = new ViewModelProvider(this).get(JugadorViewModel.class);


        // Recibir el ID del equipo desde el Intent
        int equipoId = getIntent().getIntExtra("equipo_id", -1);

        equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);
        cargarEquipos(equipoId);
        // Pasar el ID para preselección
        btn_evaluar.setOnClickListener(v -> {
            Intent intent = new Intent(this, EvaluacionJugadorActivity.class);
            startActivity(intent);
        });
        btn_guardar_jugador.setOnClickListener(v -> guardarJugador());
    }

    private void cargarEquipos(int equipoId) {
        equipoViewModel.getEquipos().observe(this, equipos -> {
            if (equipos != null && !equipos.isEmpty()) {
                List<String> nombresEquipos = new ArrayList<>();
                int preselectedIndex = 0;

                for (int i = 0; i < equipos.size(); i++) {
                    nombresEquipos.add(equipos.get(i).getNombre());
                    if (equipos.get(i).getId() == equipoId) {
                        preselectedIndex = i;
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresEquipos);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerEquipos.setAdapter(adapter);
                spinnerEquipos.setSelection(preselectedIndex);  // Preseleccionar el equipo

                // Mensaje de equipo seleccionado correctamente
                Toast.makeText(this, "Equipo seleccionado: " + nombresEquipos.get(preselectedIndex), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No hay equipos disponibles", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void guardarJugador() {
        String nombre = et_nombre_jugador.getText().toString().trim();

        if (nombre.isEmpty()) {
            Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedPosition = spinnerEquipos.getSelectedItemPosition();
        if (selectedPosition == -1) {
            Toast.makeText(this, "Seleccione un equipo válido", Toast.LENGTH_SHORT).show();
            return;
        }

        int equipoId = equipoViewModel.getEquipos().getValue().get(selectedPosition).getId();

        jugadorViewModel.agregarJugador(nombre, equipoId).observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Jugador añadido", Toast.LENGTH_SHORT).show();
                // Devolver resultado exitoso
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Error al añadir jugador", Toast.LENGTH_SHORT).show();
            }
        });
    }




}