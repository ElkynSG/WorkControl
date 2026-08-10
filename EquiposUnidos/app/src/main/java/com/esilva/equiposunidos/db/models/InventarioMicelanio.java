package com.esilva.equiposunidos.db.models;

public class InventarioMicelanio {
    private int id;

    private int AC_R134;
    private int X70;

    public InventarioMicelanio(){
        id=1;
        AC_R134=0;
        X70=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAC_R134() {
        return AC_R134;
    }

    public void setAC_R134(int AC_R134) {
        this.AC_R134 = AC_R134;
    }

    public int getX70() {
        return X70;
    }

    public void setX70(int x70) {
        X70 = x70;
    }
}
