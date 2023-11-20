package com.esilva.equiposunidos.db.models;

public class DataInspeccion {
    //  0 blanco
    //  1 verde
    //  2 rojo
    private String Description;
    private boolean posB;
    private boolean posM;
    private String stPare;
    private boolean isCheckRojo;
    private int color;

    public DataInspeccion(){

    }

    public DataInspeccion(String description, boolean isRojo){
        this.Description = description;
        if(isRojo){
            this.stPare = "X";
            this.isCheckRojo = true;
            color = 2;
        }else
            color = 0;

    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isPosB() {
        return posB;
    }

    public void setPosB(boolean posB) {
        this.posB = posB;
    }

    public boolean isPosM() {
        return posM;
    }

    public void setPosM(boolean posM) {
        this.posM = posM;
    }

    public String getStPare() {
        return stPare;
    }

    public void setStPare(String stPare) {
        this.stPare = stPare;
    }

    public boolean isCheckRojo() {
        return isCheckRojo;
    }

    public void setCheckRojo(boolean checkRojo) {
        isCheckRojo = checkRojo;
    }
}
