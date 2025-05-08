package com.example.playrate.view;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddJugadorActivity extends AppCompatActivity {

    private EditText etNombreJugador;
    private Spinner spinnerEquipos;
    private Button btnGuardarJugador;
    private List<Equipo> listaEquipos = new ArrayList<>();
    private EquipoViewModel equipoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("AddJugadorActivity: onCreate iniciado");
        setContentView(R.layout.activity_add_jugador);

        etNombreJugador = findViewById(R.id.et_nombre_jugador);
        spinnerEquipos = findViewById(R.id.spinnerEquipos);
        btnGuardarJugador = findViewById(R.id.btn_guardar_jugador);

        // Inicializar el ViewModel correctamente
        equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);
        System.out.println("AddJugadorActivity: ViewModel inicializado");

        cargarEquipos();

        btnGuardarJugador.setOnClickListener(v -> {
            System.out.println("AddJugadorActivity: Botón Guardar presionado");
            int equipoIndex = spinnerEquipos.getSelectedItemPosition();
            if (equipoIndex >= 0 && !listaEquipos.isEmpty()) {
                int equipoId = listaEquipos.get(equipoIndex).getId();
                System.out.println("AddJugadorActivity: Equipo seleccionado: " + equipoId);
            }
            guardarJugador();
        });
    }

    private void cargarEquipos() {
        System.out.println("AddJugadorActivity: Iniciando carga de equipos...");
        equipoViewModel.getEquipos().observe(this, equipos -> {
            if (equipos == null || equipos.isEmpty()) {
                Toast.makeText(this, "No se encontraron equipos", Toast.LENGTH_SHORT).show();
                System.out.println("AddJugadorActivity: Lista de equipos vacía o nula");
                return;
            }

            listaEquipos.clear();
            listaEquipos.addAll(equipos);

            List<String> nombresEquipos = new ArrayList<>();
            for (Equipo equipo : equipos) {
                nombresEquipos.add(equipo.getNombre());
                System.out.println("AddJugadorActivity: Equipo añadido al spinner: " + equipo.getNombre());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresEquipos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerEquipos.setAdapter(adapter);

            System.out.println("AddJugadorActivity: Equipos cargados en el spinner: " + nombresEquipos.size());
        });
    }




    private void guardarJugador() {
        String nombre = etNombreJugador.getText().toString().trim();
        int equipoIndex = spinnerEquipos.getSelectedItemPosition();

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese el nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        if (equipoIndex < 0 || listaEquipos.isEmpty()) {
            Toast.makeText(this, "Seleccione un equipo", Toast.LENGTH_SHORT).show();
            return;
        }

        int equipoId = listaEquipos.get(equipoIndex).getId();
        String url = "http://10.0.2.2:8000/api/jugadores/";

        // Imprimir los datos antes de enviar la solicitud
        Toast.makeText(this, "Enviando: Nombre=" + nombre + ", EquipoID=" + equipoId, Toast.LENGTH_LONG).show();
        System.out.println("Enviando: Nombre=" + nombre + ", EquipoID=" + equipoId);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(this, "Jugador guardado con éxito", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    String errorMessage = "Error: ";
                    if (error.networkResponse != null) {
                        errorMessage += "Código " + error.networkResponse.statusCode;
                        if (error.networkResponse.data != null) {
                            errorMessage += " - " + new String(error.networkResponse.data);
                        }
                    } else {
                        errorMessage += "No se pudo conectar al servidor";
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("valoracion_media", "0.0");
                params.put("equipo", String.valueOf(equipoId));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        VolleySingleton.getInstance(this).getRequestQueue().add(request);
    }


}