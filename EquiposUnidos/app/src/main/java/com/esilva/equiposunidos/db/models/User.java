package com.esilva.equiposunidos.db.models;

public class User {
    private int cedula;
    private String nombre;
    private int perfil;
    private String cargo;
    private byte[] huellaPulgar;
    private byte[] huellaIndice;
    private byte[] huellaMedio;

    public User() {
    }
    public User(int cedula) {
        this.cedula = cedula;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public byte[] getHuellaPulgar() {
        return huellaPulgar;
    }

    public void setHuellaPulgar(byte[] huellaPulgar) {
        this.huellaPulgar = huellaPulgar;
    }

    public byte[] getHuellaIndice() {
        return huellaIndice;
    }

    public void setHuellaIndice(byte[] huellaIndice) {
        this.huellaIndice = huellaIndice;
    }

    public byte[] getHuellaMedio() {
        return huellaMedio;
    }

    public void setHuellaMedio(byte[] huellaMedio) {
        this.huellaMedio = huellaMedio;
    }
}
