package com.esilva.equiposunidos.db.models;

import java.util.PrimitiveIterator;

public class DataManteni {
    //private User user;
    //private Equipos equipos;
    private String fechaHora;
    private String tipoManteni;
    private String horometro;
    private String horometroProx;
    private String consecutivo;
    private String lugarMante;
    private boolean isBloqueoEtiquetado;
    private boolean isATS;
    private boolean isKitDerrames;

    public DataManteni() {
    }

    public String getLugarMante() {
        return lugarMante;
    }

    public void setLugarMante(String lugarMante) {
        this.lugarMante = lugarMante;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTipoManteni() {
        return tipoManteni;
    }

    public void setTipoManteni(String tipoManteni) {
        this.tipoManteni = tipoManteni;
    }

    public String getHorometro() {
        return horometro;
    }

    public void setHorometro(String horometro) {
        this.horometro = horometro;
    }

    public String getHorometroProx() {
        return horometroProx;
    }

    public void setHorometroProx(String horometroProx) {
        this.horometroProx = horometroProx;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public boolean isBloqueoEtiquetado() {
        return isBloqueoEtiquetado;
    }

    public void setBloqueoEtiquetado(boolean bloqueoEtiquetado) {
        isBloqueoEtiquetado = bloqueoEtiquetado;
    }

    public boolean isATS() {
        return isATS;
    }

    public void setATS(boolean ATS) {
        isATS = ATS;
    }

    public boolean isKitDerrames() {
        return isKitDerrames;
    }

    public void setKitDerrames(boolean kitDerrames) {
        isKitDerrames = kitDerrames;
    }
}
