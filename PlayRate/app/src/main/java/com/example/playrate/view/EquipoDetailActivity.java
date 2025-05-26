package com.example.playrate.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.example.playrate.R;
import com.example.playrate.adapter.JugadorAdapter;
import com.example.playrate.adapter.OnItemLongClickListener;
import com.example.playrate.model.Jugador;
import com.example.playrate.viewmodel.JugadorViewModel;
import java.util.ArrayList;
import java.util.List;

public class EquipoDetailActivity extends AppCompatActivity implements OnItemLongClickListener {

    private RecyclerView recyclerViewJugadores;
    private JugadorAdapter jugadorAdapter;
    private JugadorViewModel jugadorViewModel;
    private int equipoId;
    private Button btnAddJugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_detail);

        equipoId = getIntent().getIntExtra("equipo_id", -1);

        recyclerViewJugadores = findViewById(R.id.recyclerViewJugadores);
        btnAddJugador = findViewById(R.id.btnAddJugador);

        recyclerViewJugadores.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar el adaptador pasando el contexto y el listener
        jugadorAdapter = new JugadorAdapter(this, new ArrayList<>(), this);
        recyclerViewJugadores.setAdapter(jugadorAdapter);

        jugadorViewModel = new ViewModelProvider(this).get(JugadorViewModel.class);
        cargarJugadores();

        btnAddJugador.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddJugadorActivity.class);
            intent.putExtra("equipo_id", equipoId);
            startActivityForResult(intent, 100);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            cargarJugadores();
        }
    }

    private void cargarJugadores() {
        jugadorViewModel.getJugadoresPorEquipo(equipoId).observe(this, jugadores -> {
            if (jugadores != null) {
                jugadorAdapter.setJugadores(jugadores);
            }
        });
    }

    @Override
    public void onItemLongClick(Jugador jugador) {
        showDeleteDialog(jugador.getId());
    }

    private void showDeleteDialog(int jugadorId) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Jugador")
                .setMessage("¿Estás seguro de que deseas eliminar este jugador?")
                .setPositiveButton("Sí", (dialog, which) -> eliminarJugador(jugadorId))
                .setNegativeButton("No", null)
                .show();
    }

    private void eliminarJugador(int jugadorId) {
        jugadorViewModel.eliminarJugador(jugadorId).observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Jugador eliminado", Toast.LENGTH_SHORT).show();
                cargarJugadores();
            } else {
                Toast.makeText(this, "Error al eliminar el jugador", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
