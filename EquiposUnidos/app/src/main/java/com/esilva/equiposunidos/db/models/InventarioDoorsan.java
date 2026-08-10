package com.esilva.equiposunidos.db.models;

public class InventarioDoorsan {
    private int id;

    private int fAceiteMotor;
    private int fCombustible4005;
    private int fCombustible4004;
    private int fServoTrasmision;
    private int fPiloto;
    private int fHidrailico;
    private int fAireCabina;
    private int fAireInterno;
    private int fAireExterno;
    private int fPrefijo;

    public InventarioDoorsan(){
        id=1;
        fAceiteMotor=0;
        fCombustible4005=0;
        fCombustible4004=0;
        fServoTrasmision=0;
        fPiloto=0;
        fHidrailico=0;
        fAireCabina=0;
        fAireInterno=0;
        fAireExterno=0;
        fPrefijo=0;
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

    public int getfCombustible4005() {
        return fCombustible4005;
    }

    public void setfCombustible4005(int fCombustible4005) {
        this.fCombustible4005 = fCombustible4005;
    }

    public int getfCombustible4004() {
        return fCombustible4004;
    }

    public void setfCombustible4004(int fCombustible4004) {
        this.fCombustible4004 = fCombustible4004;
    }

    public int getfServoTrasmision() {
        return fServoTrasmision;
    }

    public void setfServoTrasmision(int fServoTrasmision) {
        this.fServoTrasmision = fServoTrasmision;
    }

    public int getfPiloto() {
        return fPiloto;
    }

    public void setfPiloto(int fPiloto) {
        this.fPiloto = fPiloto;
    }

    public int getfHidrailico() {
        return fHidrailico;
    }

    public void setfHidrailico(int fHidrailico) {
        this.fHidrailico = fHidrailico;
    }

    public int getfAireCabina() {
        return fAireCabina;
    }

    public void setfAireCabina(int fAireCabina) {
        this.fAireCabina = fAireCabina;
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

    public int getfPrefijo() {
        return fPrefijo;
    }

    public void setfPrefijo(int fPrefijo) {
        this.fPrefijo = fPrefijo;
    }
}
