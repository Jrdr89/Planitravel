package com.example.planitravelv2.Entidades;

import com.example.planitravelv2.SQlite.DatabaseHelper;

public class Holder {

    private static DatabaseHelper dataBade;

    public static DatabaseHelper getDataBade() {
        return dataBade;
    }

    public static void setDataBade(DatabaseHelper dataBade) {
        Holder.dataBade = dataBade;
    }
}
