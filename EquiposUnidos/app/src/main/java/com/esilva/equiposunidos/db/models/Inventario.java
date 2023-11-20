package com.esilva.equiposunidos.db.models;

public class Inventario {
    private String description;
    private String codigo;
    private String cantidad;
    private int tipo;
    private boolean isTitle;

    public Inventario(boolean isTitleIn){
        if(isTitleIn){
            isTitle = true;
            description = "Descripcion";
            codigo = "Codigo";
            cantidad = "Cantidad";
        }else
            isTitle = false;
    }

    public Inventario(String strDescrip,String strCodigo){
        isTitle = false;
        description = strDescrip;
        codigo = strCodigo;
        cantidad = "";

    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
