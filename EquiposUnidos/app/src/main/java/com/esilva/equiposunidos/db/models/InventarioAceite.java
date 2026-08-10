package com.esilva.equiposunidos.db.models;

public class InventarioAceite {
    private int id;

    private int a15W40;
    private int iso68;
    private int to30;
    private int a80W90;
    private int S527;
    private int G_Litio;
    private int Motor;

    public InventarioAceite(){
        id=1;
        a15W40=0;
        iso68=0;
        to30=0;
        a80W90=0;
        S527=0;
        G_Litio=0;
        Motor=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getA15W40() {
        return a15W40;
    }

    public void setA15W40(int a15W40) {
        this.a15W40 = a15W40;
    }

    public int getIso68() {
        return iso68;
    }

    public void setIso68(int iso68) {
        this.iso68 = iso68;
    }

    public int getTo30() {
        return to30;
    }

    public void setTo30(int to30) {
        this.to30 = to30;
    }

    public int getA80W90() {
        return a80W90;
    }

    public void setA80W90(int a80W90) {
        this.a80W90 = a80W90;
    }

    public int getS527() {
        return S527;
    }

    public void setS527(int s527) {
        S527 = s527;
    }

    public int getG_Litio() {
        return G_Litio;
    }

    public void setG_Litio(int g_Litio) {
        G_Litio = g_Litio;
    }

    public int getMotor() {
        return Motor;
    }

    public void setMotor(int motor) {
        Motor = motor;
    }
}
