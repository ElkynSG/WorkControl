package com.esilva.equiposunidos.application;

import static com.esilva.equiposunidos.util.Constantes.CHANNEL_NOTIFICATION;
import static com.esilva.equiposunidos.util.Constantes.re_hora;
import static com.esilva.equiposunidos.util.Constantes.re_minu;
import static com.esilva.equiposunidos.util.Constantes.re_seg;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.esilva.equiposunidos.ServiceBroascastReceiver;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.DataInspeccion;
import com.esilva.equiposunidos.db.models.DataManteni;
import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.Manteni;
import com.esilva.equiposunidos.db.models.User;

import java.util.Calendar;
import java.util.List;

public class UnidosApplication extends Application {
    private static Context appContext;
    private static User user;
    private static Equipos equipo;
    private static DataManteni dataManteni;
    private static List<Manteni> listManteni;
    private static List<DataInspeccion> listInspeccion;
    private static int sdkPermision;
    private static int sdkNoti;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("DP_DLOG","onCreate "+"Application");
        appContext = getApplicationContext();
        AdminBaseDatos adminBaseDatos = new AdminBaseDatos(appContext);
        adminBaseDatos.initInventario();
        sdkNoti = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S? PendingIntent.FLAG_IMMUTABLE:0;
        sdkPermision = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S?0:1;

        configService();
        createService();

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

    private void configService(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "hotel";
            String description = "reportes";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_NOTIFICATION, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

    private void createService(){
        AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(ALARM_SERVICE);

        Intent intentToRepeat = new Intent(appContext, ServiceBroascastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(appContext, 0, intentToRepeat, sdkNoti);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, re_hora);
        calendar.set(Calendar.MINUTE, re_minu);
        calendar.set(Calendar.SECOND, re_seg);

        Log.v("time","hora reporte"+String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY
                , pendingIntent);
    }
}
