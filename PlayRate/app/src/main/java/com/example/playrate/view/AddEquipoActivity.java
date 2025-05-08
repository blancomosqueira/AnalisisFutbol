package com.example.playrate.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.playrate.R;
import com.example.playrate.utils.VolleySingleton;
import java.util.HashMap;
import java.util.Map;

public class AddEquipoActivity extends AppCompatActivity {

    private EditText etNombreEquipo;
    private Button btnGuardarEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipo);

        etNombreEquipo = findViewById(R.id.etNombreEquipo);
        btnGuardarEquipo = findViewById(R.id.btnGuardarEquipo);

        btnGuardarEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarEquipo();
            }
        });
    }

    private void guardarEquipo() {
        String nombre = etNombreEquipo.getText().toString().trim();

        if (nombre.isEmpty()) {
            Toast.makeText(this, "El nombre del equipo no puede estar vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.0.2.2:8000/api/equipos/";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(this, "Equipo guardado exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    Toast.makeText(this, "Error al guardar el equipo", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                return params;
            }
        };

        VolleySingleton.getInstance(this).getRequestQueue().add(request);
    }
}
