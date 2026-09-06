package com.esilva.equiposunidos.Setting;

import android.graphics.drawable.Drawable;

public class AppInfo {

    String nombre;
    Drawable icono;
    String paquete;

    public AppInfo(String nombre, Drawable icono, String paquete) {
        this.nombre = nombre;
        this.icono = icono;
        this.paquete = paquete;
    }
}