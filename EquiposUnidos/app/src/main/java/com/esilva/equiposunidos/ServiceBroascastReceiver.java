package com.esilva.equiposunidos;

import static com.esilva.equiposunidos.util.Constantes.CHANNEL_NOTIFICATION;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.esilva.equiposunidos.Report.ReportUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ServiceBroascastReceiver  extends BroadcastReceiver {

    private  String nameFile;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("ServiceBroadcastReceiver", "inicia el servicio reporte");
        if(saveReport(context)) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_NOTIFICATION);
            builder.setSmallIcon(R.drawable.user_add);
            builder.setContentTitle("Reporte");
            builder.setContentText("Reporte usuarios actualizado");
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(201, builder.build());
        }
    }



    private Boolean saveReport(Context context){
        ReportUser user = new ReportUser(context);
        return user.buildReportCurrent();
    }
}
