package com.example.planitravelv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.planitravelv2.Adaptadores.ActividadAdapter;
import com.example.planitravelv2.Entidades.Actividad;
import com.example.planitravelv2.Entidades.DiaViaje;

import java.util.ArrayList;

public class PruebaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Actividad> listaActividades;
    private ActividadAdapter actividadAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        // Obtener el Intent y el objeto DiaViaje
        Intent intent = getIntent();
        DiaViaje diaViaje = (DiaViaje) intent.getSerializableExtra("diaViaje");

        listaActividades = (ArrayList<Actividad>) Actividad.generarListaActividadesAleatorias(5);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        actividadAdapter = new ActividadAdapter(listaActividades);

    }
}