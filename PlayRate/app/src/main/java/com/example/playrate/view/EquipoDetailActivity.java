package com.example.playrate.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.playrate.R;
import com.example.playrate.adapter.JugadorAdapter;
import com.example.playrate.viewmodel.JugadorViewModel;

public class EquipoDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerViewJugadores;
    private JugadorAdapter jugadorAdapter;
    private JugadorViewModel jugadorViewModel;
    private int equipoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_detail);

        equipoId = getIntent().getIntExtra("equipo_id", -1);

        recyclerViewJugadores = findViewById(R.id.recyclerViewJugadores);
        recyclerViewJugadores.setLayoutManager(new LinearLayoutManager(this));

        jugadorAdapter = new JugadorAdapter();
        recyclerViewJugadores.setAdapter(jugadorAdapter);

        jugadorViewModel = new ViewModelProvider(this).get(JugadorViewModel.class);
        cargarJugadores();
    }

    private void cargarJugadores() {
        jugadorViewModel.getJugadoresPorEquipo(equipoId).observe(this, jugadores -> {
            if (jugadores != null && !jugadores.isEmpty()) {
                jugadorAdapter.setJugadores(jugadores);
            }
        });
    }
}
