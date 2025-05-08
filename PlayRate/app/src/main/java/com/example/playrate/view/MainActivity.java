package com.example.playrate.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.playrate.R;

public class MainActivity extends AppCompatActivity {

    private Button btnAgregarJugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAgregarJugador = findViewById(R.id.btn_agregar_jugador);
        btnAgregarJugador.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddJugadorActivity.class);
            startActivity(intent);
        });
    }
}
