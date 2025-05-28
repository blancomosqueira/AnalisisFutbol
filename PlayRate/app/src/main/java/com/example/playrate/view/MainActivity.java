package com.example.playrate.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.playrate.R;
import com.example.playrate.adapter.EquipoAdapter;
import com.example.playrate.adapter.OnEquipoClickListener;
import com.example.playrate.model.Equipo;
import com.example.playrate.viewmodel.EquipoViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewEquipos;
    private EquipoAdapter equipoAdapter;
    private EquipoViewModel equipoViewModel;
    private Button btnAddEquipo;
    private Button btnAddJugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewEquipos = findViewById(R.id.recyclerViewEquipos);
        btnAddEquipo = findViewById(R.id.btnAddEquipo);
        btnAddJugador = findViewById(R.id.btnAddJugador);

        btnAddJugador.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddJugadorActivity.class);
            startActivity(intent);
        });

        recyclerViewEquipos.setLayoutManager(new LinearLayoutManager(this));
        equipoAdapter = new EquipoAdapter(new ArrayList<>(), new OnEquipoClickListener() {
            @Override
            public void onItemClick(Equipo equipo) {
                Intent intent = new Intent(MainActivity.this, EquipoDetailActivity.class);
                intent.putExtra("equipo_id", equipo.getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Equipo equipo) {
                showDeleteEquipoDialog(equipo.getId());
            }
        });
        recyclerViewEquipos.setAdapter(equipoAdapter);
        recyclerViewEquipos.setAdapter(equipoAdapter);

        equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);
        cargarEquipos();

        btnAddEquipo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEquipoActivity.class);
            startActivity(intent);
        });
    }
    private void showDeleteEquipoDialog(int equipoId) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar equipo")
                .setMessage("¿Seguro que deseas eliminar este equipo?")
                .setPositiveButton("Sí", (dialog, which) -> eliminarEquipo(equipoId))
                .setNegativeButton("No", null)
                .show();
    }

    private void eliminarEquipo(int equipoId) {
        equipoViewModel.eliminarEquipo(equipoId).observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Equipo eliminado", Toast.LENGTH_SHORT).show();
                cargarEquipos();
            } else {
                Toast.makeText(this, "Error al eliminar el equipo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarEquipos() {
        equipoViewModel.getEquipos().observe(this, equipos -> {
            if (equipos != null) {
                equipoAdapter.setEquipos(equipos);
            }
        });
    }

}
