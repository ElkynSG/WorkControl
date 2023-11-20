package com.esilva.equiposunidos.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.esilva.equiposunidos.db.models.DataInspeccion;
import com.esilva.equiposunidos.db.models.DataManteni;
import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.Manteni;
import com.esilva.equiposunidos.db.models.User;

import java.util.List;

public class UnidosApplication extends Application {
    private static Context appContext;
    private static User user;
    private static Equipos equipo;
    private static DataManteni dataManteni;
    private static List<Manteni> listManteni;
    private static List<DataInspeccion> listInspeccion;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("DP_DLOG","onCreate "+"Application");
        appContext = getApplicationContext();

    }

    public static List<DataInspeccion> getListInspeccion() {
        return listInspeccion;
    }

    public static void setListInspeccion(List<DataInspeccion> listInspeccion) {
        UnidosApplication.listInspeccion = listInspeccion;
    }

    public static DataManteni getDataManteni() {
        return dataManteni;
    }

    public static List<Manteni> getListManteni() {
        return listManteni;
    }

    public static void setListManteni(List<Manteni> listManteni) {
        UnidosApplication.listManteni = listManteni;
    }

    public static void setDataManteni(DataManteni dataManteni) {
        UnidosApplication.dataManteni = dataManteni;
    }

    public static Equipos getEquipo() {
        return equipo;
    }

    public static void setEquipo(Equipos equipo) {
        UnidosApplication.equipo = equipo;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UnidosApplication.user = user;
    }
}
