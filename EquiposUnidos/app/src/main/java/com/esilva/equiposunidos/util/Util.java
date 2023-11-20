package com.esilva.equiposunidos.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.esilva.equiposunidos.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Util {

    public static String getVersionName(Context ctx){
        String version = null;
        try {
            PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    @SuppressLint("ResourceAsColor")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void showToast(int image, String mensaje, Activity context){
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_custumer,
                (ViewGroup) context.findViewById(R.id.layout_toast));

        ImageView image1 = (ImageView) layout.findViewById(R.id.im_toast);
        image1.setImageResource(image);
        TextView text = (TextView) layout.findViewById(R.id.tv_toast);
        text.setBackgroundColor(context.getResources().getColor(R.color.white));
        text.setText(mensaje);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void showToast(int image, int idRes,Activity context){
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_custumer,
                (ViewGroup) context.findViewById(R.id.layout_toast));

        ImageView image1 = (ImageView) layout.findViewById(R.id.im_toast);
        image1.setImageResource(image);
        TextView text = (TextView) layout.findViewById(R.id.tv_toast);
        text.setBackgroundColor(context.getResources().getColor(R.color.white));
        text.setText(idRes);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    static public void SnackbarCreate(String title, String message,String msgButton,Activity context,Class<?> destiny, View v){
        // create an instance of the snackbar
        final Snackbar snackbar = Snackbar.make(v, "", Snackbar.LENGTH_LONG);
        View customSnackView = context.getLayoutInflater().inflate(R.layout.custom_snackbar_view, null);
        snackbar.getView().setBackgroundColor(context.getColor(R.color.transparant));
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        TextView tvTitle = customSnackView.findViewById(R.id.textView1);
        tvTitle.setText(title);
        TextView tvMessage = customSnackView.findViewById(R.id.textView2);
        tvMessage.setText(message);
        Button bGotoWebsite = customSnackView.findViewById(R.id.gotoWebsiteButton);
        bGotoWebsite.setText(msgButton);
        bGotoWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,destiny));
                //Toast.makeText(context, "Redirecting to Website", Toast.LENGTH_SHORT).show();
                snackbar.dismiss();
            }
        });
        snackbarLayout.addView(customSnackView, 0);

        snackbar.show();
    }

    static public Bitmap replaceBitmapColor(Bitmap srcBitmap, int color) {
        if (srcBitmap == null) return srcBitmap;
        Bitmap result = srcBitmap.copy(Bitmap.Config.ARGB_8888, true);
        int nWidth = result.getWidth();
        int nHeight = result.getHeight();
        for (int y = 0; y < nHeight; ++y)
            for (int x = 0; x < nWidth; ++x) {
                int nPixelColor = result.getPixel(x, y);

                int r = Color.red(nPixelColor);
                int g = Color.green(nPixelColor);
                int b = Color.blue(nPixelColor);
                int a = Color.alpha(nPixelColor);

                if ((0 <= r && r < 180)
                        && (0 <= g && g < 180)
                        && (0 <= b && b < 180)) {

                    int newColor = Color.argb(a, Color.red(color) + (int) (Math.random() * 10), Color.green(color), Color.blue(color));
                    result.setPixel(x, y, newColor);

                } else {
                    result.setPixel(x, y, Color.TRANSPARENT);
                }
            }
        return result;
    }

    static public Bitmap getAlphaBitmap(Bitmap mBitmap, int mColor) {
        if (mBitmap == null) return null;
        Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        int mBitmapWidth = mAlphaBitmap.getWidth();
        int mBitmapHeight = mAlphaBitmap.getHeight();

        for (int i = 0; i < mBitmapHeight; i++) {
            for (int j = 0; j < mBitmapWidth; j++) {
                int color = mBitmap.getPixel(j, i);
                if (color != mColor) {
                    mAlphaBitmap.setPixel(j, i, color);
                }
            }
        }

        return mAlphaBitmap;
    }

    static public String getFechaHoraOld(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    static public String getFechaHora(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    static public String[] getFechaOldCom(){
        String[] days = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
        String[] months = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        String[] dateOut = new String[4];
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateOut[0]=sdf.format(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dateOut[1]=days[dayOfWeek - 1];
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dateOut[2]=String.valueOf(dayOfMonth);
        int monthOfYear = calendar.get(Calendar.MONTH);
        dateOut[3]=months[monthOfYear];
        return dateOut;
    }

    static public String[] getFechaHoraCom(){
        String[] days = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
        String[] months = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        String[] dateOut = new String[4];
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateOut[0]=sdf.format(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dateOut[1]=days[dayOfWeek - 1];
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dateOut[2]=String.valueOf(dayOfMonth);
        int monthOfYear = calendar.get(Calendar.MONTH);
        dateOut[3]=months[monthOfYear];
        return dateOut;
    }



    static public long getTimeDifference(String inDate){
        long diferenciaHoras = 0;
        //String test = "2023-10-28 02:03:06";
        Calendar calendar = Calendar.getInstance();
        Date date_Current = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.d("DP_DLOG","getTimeDifference "+"in "+inDate );
        //Log.d("DP_DLOG","getTimeDifference "+"test "+test );
        Log.d("DP_DLOG","getTimeDifference "+"act "+sdf.format(date_Current));


        SimpleDateFormat lastTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date_In = null;

        try {
            date_In = lastTime.parse(inDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date_In != null) {
            long diferenciaMillis = date_Current.getTime() - date_In.getTime();
            diferenciaHoras = TimeUnit.MILLISECONDS.toHours(diferenciaMillis);
            TimeUnit.MILLISECONDS.toHours(diferenciaMillis);
        }

        return diferenciaHoras;
    }

    static public boolean getCompareDate(String DateIn_1,String DateIn_2){
       String[] date1 = DateIn_1.split(" ",-1);
       String[] date2 = DateIn_2.split(" ",-1);
       if(date1.length > 1 && date2.length > 1){
           if(date1[0].equals(date2[0]))
               return true;
           else
               return false;
       }else
           return false;
    }


}