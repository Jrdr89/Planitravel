package com.example.planitravelv2.Vistas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.planitravelv2.Adaptadores.DiaViajeAdapter;
import com.example.planitravelv2.Entidades.DiaViaje;
import com.example.planitravelv2.Entidades.Holder;
import com.example.planitravelv2.R;
import com.example.planitravelv2.SQlite.DatabaseHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // ...

    private RecyclerView recyclerView;
    private DiaViajeAdapter diaViajeAdapter;
    private List<DiaViaje> diaViajeList;
    private DatabaseHelper databaseHelper;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar RecyclerView y adaptador
        recyclerView = findViewById(R.id.recyclerView);
        diaViajeList = new ArrayList<>();
        diaViajeAdapter = new DiaViajeAdapter(diaViajeList,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(diaViajeAdapter);

        //Inicializar componentes del layout de los Date Pickers
        ImageView icon_calendar = findViewById(R.id.icon_calendar);
        LinearLayout datePickerLayout = findViewById(R.id.datePickerLayout);
        ImageView icon_borrar = findViewById(R.id.icon_borrar);

        // Inicializar la base de datos
        databaseHelper = new DatabaseHelper(this);
        /*Holder.setDataBade(databaseHelper);*/

        // Obtener los objetos DiaViaje y mostrarlos en el RecyclerView
        obtenerDiaViajeDesdeSQLite();

        //Evento para mostrar/ocultar el Layout de los DatePickers
        icon_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datePickerLayout.getVisibility() == View.VISIBLE) {
                    datePickerLayout.setVisibility(View.GONE);
                } else {
                    datePickerLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        icon_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoBorrar();
            }
        });

        // Configurar el evento click del botón "Guardar fechas"
        Button saveDatesButton = findViewById(R.id.saveDatesButton);
        saveDatesButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Obtener las fechas seleccionadas en los DatePickers
                DatePicker startDatePicker = findViewById(R.id.startDatePicker);
                DatePicker endDatePicker = findViewById(R.id.endDatePicker);
                LocalDate startDate = obtenerLocalDateDesdeDatePicker(startDatePicker);
                LocalDate endDate = obtenerLocalDateDesdeDatePicker(endDatePicker);

                datePickerLayout.setVisibility(View.GONE);
                // Obtener los objetos DiaViaje entre las fechas seleccionadas
                List<DiaViaje> nuevosDiaViajeList = obtenerDiaViajeEntreFechas(startDate, endDate);

                // Guardar los nuevos objetos DiaViaje en SQLite
                guardarDiaViajeEnSQLite(nuevosDiaViajeList);

                // Actualizar el RecyclerView con los nuevos objetos DiaViaje
                diaViajeList.clear();
                diaViajeList.addAll(nuevosDiaViajeList);
                diaViajeAdapter.notifyDataSetChanged();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private LocalDate obtenerLocalDateDesdeDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;  // Los meses en DatePicker van de 0 a 11
        int year = datePicker.getYear();
        return LocalDate.of(year, month, day);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<DiaViaje> obtenerDiaViajeEntreFechas(LocalDate startDate, LocalDate endDate) {
        List<DiaViaje> diaViajeList = new ArrayList<>();

        // Iterar sobre las fechas desde startDate hasta endDate, ambos incluidos
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            DiaViaje diaViaje = new DiaViaje(date);
            diaViaje.setDescripcion("");
            diaViajeList.add(diaViaje);
        }

        return diaViajeList;
    }



    private void guardarDiaViajeEnSQLite(List<DiaViaje> diaViajeList) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        for (DiaViaje diaViaje : diaViajeList) {
            ContentValues values = new ContentValues();
            values.put("dia", diaViaje.getDia().toString());
            values.put("descripcion", diaViaje.getDescripcion());

            db.insert("dia_viaje", null, values);
        }

        db.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void obtenerDiaViajeDesdeSQLite() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        diaViajeList.clear();

        Cursor cursor = db.query("dia_viaje", null, null, null, null, null, null);

        int diaColumnIndex = cursor.getColumnIndex("dia");
        int descripcionColumnIndex = cursor.getColumnIndex("descripcion");

        if (cursor.moveToFirst()) {
            do {
                if (diaColumnIndex != -1 && descripcionColumnIndex != -1) {
                    String diaString = cursor.getString(diaColumnIndex);
                    LocalDate dia = LocalDate.parse(diaString);
                    String descripcion = cursor.getString(descripcionColumnIndex);

                    DiaViaje diaViaje = new DiaViaje(dia);
                    diaViaje.setDescripcion(descripcion);

                    diaViajeList.add(diaViaje);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        diaViajeAdapter.notifyDataSetChanged();
    }
    private void mostrarDialogoBorrar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar borrado");
        builder.setMessage("¿Está seguro de que desea borrar este planning?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                borrarRegistrosDiaViaje();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No hacer nada, simplemente cerrar el diálogo
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void borrarRegistrosDiaViaje() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete("dia_viaje", null, null);
        db.close();

        diaViajeList.clear();
        databaseHelper.borrarTablas();
        diaViajeAdapter.notifyDataSetChanged();

    }


    // ...
}
