package com.example.planitravelv2.Entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Actividad implements Serializable {

    private String momentoDia;
    private String descripcion;
    private String notas;
    private String ubicacion;
    private String dia;

    public Actividad(String dia,String momentoDia, String descripcion, String notas, String ubicacion) {
        this.momentoDia = momentoDia;
        this.descripcion = descripcion;
        this.notas = notas;
        this.ubicacion = ubicacion;
        this.dia = dia;
    }

    public Actividad(String dia,String momentoDia, String descripcion, String notas) {
        this.momentoDia = momentoDia;
        this.descripcion = descripcion;
        this.notas = notas;
        this.dia = dia;
    }

    public String getMomentoDia() {
        return momentoDia;
    }

    public void setMomentoDia(String momentoDia) {
        this.momentoDia = momentoDia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public static List<Actividad> generarListaActividadesAleatorias(int cantidad) {
        List<Actividad> actividadList = new ArrayList<>();
        String[] momentosDia = {"Mañana", "Tarde", "Noche"};
        String[] descripciones = {"Actividad 1", "Actividad 2", "Actividad 3"};
        String[] notas = {"Nota 1", "Nota 2", "Nota 3"};
        String[] ubicaciones = {"Ubicación 1", "Ubicación 2", "Ubicación 3"};
        String[] dias = {"Lunes", "Martes", "Miércoles"};

        Random random = new Random();

        for (int i = 0; i < cantidad; i++) {
            String momentoDia = momentosDia[random.nextInt(momentosDia.length)];
            String descripcion = descripciones[random.nextInt(descripciones.length)];
            String nota = notas[random.nextInt(notas.length)];
            String ubicacion = ubicaciones[random.nextInt(ubicaciones.length)];
            String dia = dias[random.nextInt(dias.length)];

            Actividad actividad = new Actividad(dia, momentoDia, descripcion, nota, ubicacion);
            actividadList.add(actividad);
        }

        return actividadList;
    }
}
