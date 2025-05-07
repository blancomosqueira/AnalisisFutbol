package com.example.playrate.view;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.playrate.R;
import com.example.playrate.viewmodel.JugadorViewModel;

public class MainActivity extends AppCompatActivity {

    private JugadorViewModel jugadorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jugadorViewModel = new ViewModelProvider(this).get(JugadorViewModel.class);
        jugadorViewModel.getJugadores().observe(this, jugadores -> {
            Toast.makeText(this, "Jugadores cargados: " + jugadores.size(), Toast.LENGTH_SHORT).show();
        });
    }
}
