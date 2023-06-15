package com.example.planitravelv2.SQlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "viajes.db";
    private static final int DATABASE_VERSION = 2;

    // Nombre de la tabla dia_viaje
    public static final String TABLE_DIA_VIAJE = "dia_viaje";
    // Nombre de la tabla actividad_dia
    public static final String TABLE_ACTIVIDAD_DIA = "actividad_dia";

    // Columnas de la tabla dia_viaje
    public static final String COLUMN_DIA_ID = "id";
    public static final String COLUMN_DIA_DIA = "dia";
    public static final String COLUMN_DIA_DESCRIPCION = "descripcion";

    // Columnas de la tabla actividad_dia
    public static final String COLUMN_ACTIVIDAD_ID = "id";
    public static final String COLUMN_ACTIVIDAD_DIA = "dia";
    public static final String COLUMN_ACTIVIDAD_MOMENTO_DIA = "momento_dia";
    public static final String COLUMN_ACTIVIDAD_DESCRIPCION = "descripcion";
    public static final String COLUMN_ACTIVIDAD_NOTAS = "notas";
    public static final String COLUMN_ACTIVIDAD_UBICACION = "ubicacion";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla "dia_viaje"
        String createDiaViajeTableQuery = "CREATE TABLE " + TABLE_DIA_VIAJE + " (" +
                COLUMN_DIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_DIA_DIA + " TEXT," +
                COLUMN_DIA_DESCRIPCION + " TEXT" +
                ")";
        db.execSQL(createDiaViajeTableQuery);

        // Crear la tabla "actividad_dia"
        String createActividadDiaTableQuery = "CREATE TABLE " + TABLE_ACTIVIDAD_DIA + " (" +
                COLUMN_ACTIVIDAD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_ACTIVIDAD_DIA + " TEXT," +
                COLUMN_ACTIVIDAD_MOMENTO_DIA + " TEXT," +
                COLUMN_ACTIVIDAD_DESCRIPCION + " TEXT," +
                COLUMN_ACTIVIDAD_NOTAS + " TEXT," +
                COLUMN_ACTIVIDAD_UBICACION + " TEXT" +
                ")";
        db.execSQL(createActividadDiaTableQuery);
    }
    public void borrarTablas() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIA_VIAJE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVIDAD_DIA);
        onCreate(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualizar la base de datos si se cambia la versión
        // Aquí puedes implementar la lógica para migrar los datos existentes a la nueva versión
        // Puedes utilizar ALTER TABLE, CREATE TABLE, etc.
        // Recuerda respaldar los datos importantes antes de realizar cambios en la estructura de la base de datos
    }
}
