package com.esilva.equiposunidos.db.models;

public class Registros {
    private int id;
    private int cedula;
    private String dia;
    private int dia_mes;
    private String fecha_in;
    private String comentario_in;
    private String fecha_out;
    private String comentario_out;
    private String equipo_in;
    private String equipo_out;
    private String actividad_in;
    private String actividad_out;
    private String latitud_in;
    private String longitud_in;
    private String latitud_out;
    private String longitud_out;

    public Registros() {
    }

    public int getDia_mes() {
        return dia_mes;
    }

    public void setDia_mes(int dia_mes) {
        this.dia_mes = dia_mes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getFecha_in() {
        return fecha_in;
    }

    public void setFecha_in(String fecha_in) {
        this.fecha_in = fecha_in;
    }

    public String getComentario_in() {
        return comentario_in;
    }

    public void setComentario_in(String comentario_in) {
        this.comentario_in = comentario_in;
    }

    public String getFecha_out() {
        return fecha_out;
    }

    public void setFecha_out(String fecha_out) {
        this.fecha_out = fecha_out;
    }

    public String getComentario_out() {
        return comentario_out;
    }

    public void setComentario_out(String comentario_out) {
        this.comentario_out = comentario_out;
    }

    public String getEquipo_in() {
        return equipo_in;
    }

    public void setEquipo_in(String equipo_in) {
        this.equipo_in = equipo_in;
    }

    public String getEquipo_out() {
        return equipo_out;
    }

    public void setEquipo_out(String equipo_out) {
        this.equipo_out = equipo_out;
    }

    public String getActividad_in() {
        return actividad_in;
    }

    public void setActividad_in(String actividad_in) {
        this.actividad_in = actividad_in;
    }

    public String getActividad_out() {
        return actividad_out;
    }

    public void setActividad_out(String actividad_out) {
        this.actividad_out = actividad_out;
    }

    public String getLatitud_in() {
        return latitud_in;
    }

    public void setLatitud_in(String latitud_in) {
        this.latitud_in = latitud_in;
    }

    public String getLongitud_in() {
        return longitud_in;
    }

    public void setLongitud_in(String longitud_in) {
        this.longitud_in = longitud_in;
    }

    public String getLatitud_out() {
        return latitud_out;
    }

    public void setLatitud_out(String latitud_out) {
        this.latitud_out = latitud_out;
    }

    public String getLongitud_out() {
        return longitud_out;
    }

    public void setLongitud_out(String longitud_out) {
        this.longitud_out = longitud_out;
    }
}
