package com.example.playrate.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.playrate.R;
import com.example.playrate.model.Equipo;
import com.example.playrate.utils.VolleySingleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddJugadorActivity extends AppCompatActivity {

    private EditText etNombreJugador;
    private Spinner spinnerEquipos;
    private Button btnGuardarJugador;
    private List<Equipo> listaEquipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jugador);

        etNombreJugador = findViewById(R.id.et_nombre_jugador);
        spinnerEquipos = findViewById(R.id.spinner_equipos);
        btnGuardarJugador = findViewById(R.id.btn_guardar_jugador);

        cargarEquipos();  // Carga los equipos en el spinner

        btnGuardarJugador.setOnClickListener(v -> guardarJugador());
    }

    private void cargarEquipos() {
        // Simulación de equipos (reemplazar con llamada a la API)
        listaEquipos = new ArrayList<>();
        listaEquipos.add(new Equipo(1, "Equipo A"));
        listaEquipos.add(new Equipo(2, "Equipo B"));

        List<String> nombresEquipos = new ArrayList<>();
        for (Equipo equipo : listaEquipos) {
            nombresEquipos.add(equipo.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresEquipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEquipos.setAdapter(adapter);
    }

    private void guardarJugador() {
        String nombre = etNombreJugador.getText().toString().trim();
        int equipoIndex = spinnerEquipos.getSelectedItemPosition();
        if (nombre.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese el nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        int equipoId = listaEquipos.get(equipoIndex).getId();
        String url = "http://10.0.2.2:8000/api/jugadores/";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(this, "Jugador guardado con éxito", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> Toast.makeText(this, "Error al guardar el jugador", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("valoracion_media", "0.0");
                params.put("equipo", String.valueOf(equipoId));
                return params;
            }
        };

        VolleySingleton.getInstance(this).getRequestQueue().add(request);
    }
}
