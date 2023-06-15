package com.example.planitravelv2.Entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DiaViaje implements Serializable {
    private LocalDate dia;
    private List<Actividad> actividad;

    private String descripcion;

    public DiaViaje(LocalDate dia) {
        this.dia = dia;
        this.actividad=new ArrayList<>();
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Actividad> getActividad() {
        return actividad;
    }

    public void setActividad(List<Actividad> actividad) {
        this.actividad = actividad;
    }


    public void añadirActividad (Actividad act){
        this.actividad.add(act);
    }
}

