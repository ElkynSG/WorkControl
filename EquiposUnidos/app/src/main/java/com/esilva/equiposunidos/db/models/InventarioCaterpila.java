package com.esilva.equiposunidos.db.models;

public class InventarioCaterpila {
    private int id;

    private int fAceiteMotor;
    private int fCombustibleBF13;
    private int fCombustibleBF77;
    private int fServo;
    private int fCabina;
    private int fHidrailicoBT93;
    private int fHidrailicoBT83;
    private int fAireInterno;
    private int fAireExterno;
    private int fPrefijo;

    public InventarioCaterpila(){
        id=1;
        fAceiteMotor=0;
        fCombustibleBF13=0;
        fCombustibleBF77=0;
        fServo=0;
        fCabina=0;
        fHidrailicoBT93=0;
        fHidrailicoBT83=0;
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

    public int getfCombustibleBF13() {
        return fCombustibleBF13;
    }

    public void setfCombustibleBF13(int fCombustibleBF13) {
        this.fCombustibleBF13 = fCombustibleBF13;
    }

    public int getfCombustibleBF77() {
        return fCombustibleBF77;
    }

    public void setfCombustibleBF77(int fCombustibleBF77) {
        this.fCombustibleBF77 = fCombustibleBF77;
    }

    public int getfServo() {
        return fServo;
    }

    public void setfServo(int fServo) {
        this.fServo = fServo;
    }

    public int getfCabina() {
        return fCabina;
    }

    public void setfCabina(int fCabina) {
        this.fCabina = fCabina;
    }

    public int getfHidrailicoBT93() {
        return fHidrailicoBT93;
    }

    public void setfHidrailicoBT93(int fHidrailicoBT93) {
        this.fHidrailicoBT93 = fHidrailicoBT93;
    }

    public int getfHidrailicoBT83() {
        return fHidrailicoBT83;
    }

    public void setfHidrailicoBT83(int fHidrailicoBT83) {
        this.fHidrailicoBT83 = fHidrailicoBT83;
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
