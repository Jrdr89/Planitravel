package com.example.planitravelv2.Vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.planitravelv2.Adaptadores.ActividadAdapter;
import com.example.planitravelv2.Adaptadores.SpinnerAdapter;
import com.example.planitravelv2.Entidades.Actividad;
import com.example.planitravelv2.Entidades.DiaViaje;
import com.example.planitravelv2.R;
import com.example.planitravelv2.SQlite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ActividadAdapter actividadAdapter;
    private List<String> spinnerOptions;

    private ArrayList<Actividad> listaActividades;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        // Obtener el Intent y el objeto DiaViaje
        Intent intent = getIntent();
        DiaViaje diaViaje = (DiaViaje) intent.getSerializableExtra("diaViaje");

        //Obtener iconos del toolbar
        ImageView icon_añadir= findViewById(R.id.icon_añadir);
        ImageView icon_atras = findViewById(R.id.icon_atras);

        // Obtener la lista de Actividades del DiaViaje

        listaActividades = (ArrayList<Actividad>) diaViaje.getActividad();
       /* listaActividades = (ArrayList<Actividad>) Actividad.generarListaActividadesAleatorias(5);*/

        // Configurar el RecyclerView y el ActividadAdapter
        recyclerView = findViewById(R.id.recyclerViewActividad);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        actividadAdapter = new ActividadAdapter(listaActividades);
        recyclerView.setAdapter(actividadAdapter);

        //Obtener la referencia del layout
        LinearLayout layoutNuevaActividad = findViewById(R.id.layout_nueva_actividad);

        Spinner spinnerMomentoDia = findViewById(R.id.spinner_momento_dia);
        // Generar array para el spinner
        spinnerOptions = generarSpinnerOptions();

        // Crear adapter para el spinner
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, spinnerOptions);
        spinnerMomentoDia.setAdapter(spinnerAdapter);

        //Componentes del Layout para crear nueva actividad
        EditText editTextDescripcion = findViewById(R.id.editText_descripcion);
        EditText editTextNotas = findViewById(R.id.editText_notas);
        Button buttonGuardarActividad = findViewById(R.id.button_guardar_actividad);


        //Actualizar datos
        obtenerActividadesPorDia(diaViaje);

        //Método para mostrar Layaut que permite crear nueva actividad
        icon_añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (layoutNuevaActividad.getVisibility() == View.VISIBLE) {
                    // Si el layout de nueva actividad es visible, ocultarlo y mostrar el RecyclerView
                    layoutNuevaActividad.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    // Si el layout de nueva actividad está oculto, mostrarlo y ocultar el RecyclerView
                    layoutNuevaActividad.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });

        icon_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        buttonGuardarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String momentoDia = spinnerMomentoDia.getSelectedItem().toString();
                String descripcion = editTextDescripcion.getText().toString();
                String notas = editTextNotas.getText().toString();

                // Obtén el día de DiaViaje (reemplaza "ObtenerDiaViajeActual()" con el método que corresponda)
                String dia = String.valueOf(diaViaje.getDia());

                // Crea el objeto Actividad y avisar al adaptador
                Actividad actividad = new Actividad(dia, momentoDia, descripcion, notas);
                listaActividades.add(actividad);
                actividadAdapter.setActividadList(listaActividades);
                actividadAdapter.notifyDataSetChanged();
                // Guarda la actividad en SQLite
                guardarActividadEnSQLite(actividad);

                recyclerView.setVisibility(View.VISIBLE);
                layoutNuevaActividad.setVisibility(View.GONE);
            }
        });

    }  private List<String> generarSpinnerOptions() {
        List<String> options = new ArrayList<>();
        options.add("Mañana");
        options.add("Tarde");
        options.add("Noche");
        return options;
    }
    private void obtenerActividadesPorDia(DiaViaje diaViaje) {
        /*List<Actividad> actividades = new ArrayList<>();*/
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] columnas = {
                DatabaseHelper.COLUMN_ACTIVIDAD_ID,
                DatabaseHelper.COLUMN_ACTIVIDAD_DIA,
                DatabaseHelper.COLUMN_ACTIVIDAD_MOMENTO_DIA,
                DatabaseHelper.COLUMN_ACTIVIDAD_DESCRIPCION,
                DatabaseHelper.COLUMN_ACTIVIDAD_NOTAS,
                DatabaseHelper.COLUMN_ACTIVIDAD_UBICACION
        };

        String seleccion = DatabaseHelper.COLUMN_ACTIVIDAD_DIA + " = ?";
        String[] args = { diaViaje.getDia().toString() };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_ACTIVIDAD_DIA,
                columnas,
                seleccion,
                args,
                null,
                null,
                null
        );

        int actividadIdColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ACTIVIDAD_ID);
        int diaColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DIA_DIA);
        int momentoDiaColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ACTIVIDAD_MOMENTO_DIA);
        int descripcionColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ACTIVIDAD_DESCRIPCION);
        int notasColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ACTIVIDAD_NOTAS);
        int ubicacionColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ACTIVIDAD_UBICACION);

        if (cursor.moveToFirst()) {
            do {
                int actividadId = cursor.getInt(actividadIdColumnIndex);
                String Dia = cursor.getString(diaColumnIndex);
                String momentoDia = cursor.getString(momentoDiaColumnIndex);
                String descripcion = cursor.getString(descripcionColumnIndex);
                String notas = cursor.getString(notasColumnIndex);
                String ubicacion = cursor.getString(ubicacionColumnIndex);

                Actividad actividad = new Actividad(Dia, momentoDia, descripcion, notas, ubicacion);
                listaActividades.add(actividad);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        actividadAdapter.setActividadList(listaActividades);
        actividadAdapter.notifyDataSetChanged();
       /* return listaActividades;*/
    }
    private void guardarActividadEnSQLite( Actividad actividad) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ACTIVIDAD_DIA, actividad.getDia());
        values.put(DatabaseHelper.COLUMN_ACTIVIDAD_MOMENTO_DIA, actividad.getMomentoDia());
        values.put(DatabaseHelper.COLUMN_ACTIVIDAD_DESCRIPCION, actividad.getDescripcion());
        values.put(DatabaseHelper.COLUMN_ACTIVIDAD_NOTAS, actividad.getNotas());

        long resultado = db.insert(DatabaseHelper.TABLE_ACTIVIDAD_DIA, null, values);

        if (resultado == -1) {
            // Error al guardar la actividad en SQLite
            Toast.makeText(this, "Error al guardar la actividad en SQLite", Toast.LENGTH_SHORT).show();
        } else {
            // Éxito al guardar la actividad en SQLite
            Toast.makeText(this, "Actividad guardada en SQLite", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }




}
