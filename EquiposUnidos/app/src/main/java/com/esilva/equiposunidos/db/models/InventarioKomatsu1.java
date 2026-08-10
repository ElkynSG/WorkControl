package com.esilva.equiposunidos.db.models;

public class InventarioKomatsu1 {
    private int id;

    private int fAceiteMotor;
    private int fTrampaCombustible;
    private int fCombustible;
    private int fCabina;
    private int fHidraulico;
    private int fRespiradero;
    private int fPiloto;
    private int fAireInterno;
    private int fAireExterno;

    public InventarioKomatsu1(){
        id=1;
        fAceiteMotor=0;
        fTrampaCombustible=0;
        fCombustible=0;
        fCabina=0;
        fHidraulico=0;
        fRespiradero=0;
        fPiloto=0;
        fAireInterno=0;
        fAireExterno=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getfAceiteMotor() {
        return fAceiteMotor;
    }

    public void setfAceiteMotor(int fAceiteMotor) {
        this.fAceiteMotor = fAceiteMotor;
    }

    public int getfTrampaCombustible() {
        return fTrampaCombustible;
    }

    public void setfTrampaCombustible(int fTrampaCombustible) {
        this.fTrampaCombustible = fTrampaCombustible;
    }

    public int getfCombustible() {
        return fCombustible;
    }

    public void setfCombustible(int fCombustible) {
        this.fCombustible = fCombustible;
    }

    public int getfCabina() {
        return fCabina;
    }

    public void setfCabina(int fCabina) {
        this.fCabina = fCabina;
    }

    public int getfHidraulico() {
        return fHidraulico;
    }

    public void setfHidraulico(int fHidraulico) {
        this.fHidraulico = fHidraulico;
    }

    public int getfRespiradero() {
        return fRespiradero;
    }

    public void setfRespiradero(int fRespiradero) {
        this.fRespiradero = fRespiradero;
    }

    public int getfPiloto() {
        return fPiloto;
    }

    public void setfPiloto(int fPiloto) {
        this.fPiloto = fPiloto;
    }

    public int getfAireInterno() {
        return fAireInterno;
    }

    public void setfAireInterno(int fAireInterno) {
        this.fAireInterno = fAireInterno;
    }

    public int getfAireExterno() {
        return fAireExterno;
    }

    public void setfAireExterno(int fAireExterno) {
        this.fAireExterno = fAireExterno;
    }
}
