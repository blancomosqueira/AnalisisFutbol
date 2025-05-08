package com.example.playrate.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.playrate.R;
import com.example.playrate.adapter.EquipoAdapter;
import com.example.playrate.model.Equipo;
import com.example.playrate.viewmodel.EquipoViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewEquipos;
    private EquipoAdapter equipoAdapter;
    private EquipoViewModel equipoViewModel;
    private Button btnAddEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewEquipos = findViewById(R.id.recyclerViewEquipos);
        btnAddEquipo = findViewById(R.id.btnAddEquipo);

        recyclerViewEquipos.setLayoutManager(new LinearLayoutManager(this));
        equipoAdapter = new EquipoAdapter(new ArrayList<>(), equipo -> {
            Intent intent = new Intent(MainActivity.this, EquipoDetailActivity.class);
            intent.putExtra("equipo_id", equipo.getId());
            startActivity(intent);
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

    private void cargarEquipos() {
        equipoViewModel.getEquipos().observe(this, equipos -> {
            if (equipos != null) {
                equipoAdapter.setEquipos(equipos);
            }
        });
    }

}
