package com.esilva.equiposunidos.db.models;

public class Historial {
    private int numItems;
    private boolean isAdd;
    private String data;
    private String fecha;
    private String user;
    private String[] cod;
    private String[] cant;

    public Historial(){
        numItems = 0;
        data="";
        isAdd=false;
    }
    public void init(){
        numItems = 0;
        data="";
        isAdd=false;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void addItem(String cod,int cant){
        data += cod+":"+String.valueOf(cant)+",";
        numItems++;
    }

    public String[] getCod() {
        return cod;
    }

    public String[] getCant() {
        return cant;
    }

    public boolean decodeData(){
        int conta=0;
        cod = new String[100];
        cant = new String[100];
        String[] dest = data.split(",",-1);
        if(dest.length < 1)
            return false;

        for (int i=0;i<dest.length;i++) {
            String[] cff = dest[i].split(":", -1);
            if(cff.length>1){
                cod[i] = cff[0];
                if(isAdd)
                    cant[i] = cff[1];
                else
                    cant[i] = "- "+cff[1];
            }else {
                break;
            }
        }
        return true;
    }
}
