package com.esilva.equiposunidos.db.models;

public class Manteni {
    private String descrition;
    private boolean bSI;
    private boolean bNA;
    private boolean bTitle;
    private String comentario;

    public Manteni() {

    }

    public Manteni(String descrition, boolean bTitle) {
        this.descrition = descrition;
        this.bTitle = bTitle;
    }

    public Manteni(String descrition) {
        this.descrition = descrition;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isbTitle() {
        return bTitle;
    }

    public void setbTitle(boolean bTitle) {
        this.bTitle = bTitle;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public boolean isbSI() {
        return bSI;
    }

    public void setbSI(boolean bSI) {
        this.bSI = bSI;
    }

    public boolean isbNA() {
        return bNA;
    }

    public void setbNA(boolean bNA) {
        this.bNA = bNA;
    }
}
