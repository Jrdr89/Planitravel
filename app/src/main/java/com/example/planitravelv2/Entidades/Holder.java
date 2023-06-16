package com.example.planitravelv2.Entidades;

import com.example.planitravelv2.SQlite.DatabaseHelper;

public class Holder {

    private static DiaViaje diaViaje;
    private static Actividad actividad;

    public static DiaViaje getDiaViaje() {
        return diaViaje;
    }

    public static void setDiaViaje(DiaViaje diaViaje) {
        Holder.diaViaje = diaViaje;
    }

    public static Actividad getActividad() {
        return actividad;
    }

    public static void setActividad(Actividad actividad) {
        Holder.actividad = actividad;
    }
}

