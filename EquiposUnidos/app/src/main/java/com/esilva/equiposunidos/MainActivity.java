package com.esilva.equiposunidos;

import static com.esilva.equiposunidos.util.Constantes.ACTION_USB_PERMISSION;
import static com.esilva.equiposunidos.util.Constantes.FILE_IMAGE;
import static com.esilva.equiposunidos.util.Constantes.FILE_REPORT;
import static com.esilva.equiposunidos.util.Constantes.PACKAGE_FILE;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.esilva.equiposunidos.Setting.SettingActivity;
import com.esilva.equiposunidos.application.UnidosApplication;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.User;
import com.esilva.equiposunidos.util.Util;
import com.google.android.material.snackbar.Snackbar;
import com.suprema.util.Logger;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout inicio;

    private final int REQUEST_COD = 200;
    private PowerManager.WakeLock mWakeLock = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setView();



        solicitarPermisos();
        requestWakeLock();
        initUsbListener();
    }

    private void setView() {
        inicio = findViewById(R.id.inicio);
        inicio.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void solicitarPermisos(){

        if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                Intent getpermission = new Intent();
                getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(getpermission);
            }
        }else{
            int PermisoStorageRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int PermisoStorageWrite = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int PermisoLocation = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
            int PermisoLocationCourse = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION);
            if( PermisoStorageRead == PackageManager.PERMISSION_GRANTED && PermisoStorageWrite == PackageManager.PERMISSION_GRANTED &&
                    PermisoLocation == PackageManager.PERMISSION_GRANTED && PermisoLocationCourse == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"permiso staora otorgado",Toast.LENGTH_LONG);
            }else{
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION

                },REQUEST_COD);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_COD:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    File directorio = new File(Environment.getExternalStorageDirectory(), PACKAGE_FILE);
                    if (!directorio.exists()) {
                        directorio.mkdirs();
                    }

                    File directorio2 = new File(Environment.getExternalStorageDirectory(), FILE_REPORT);
                    if (!directorio2.exists()) {
                        directorio2.mkdirs();
                    }

                    File directorio3 = new File(Environment.getExternalStorageDirectory(), FILE_IMAGE);
                    if (!directorio3.exists()) {
                        directorio3.mkdirs();
                    }
                    //permisoDeCamaraConcedido();
                } else {
                    // permisoDeCamaraDenegado();
                }
                break;
            // Aquí más casos dependiendo de los permisos
            // case OTRO_CODIGO_DE_PERMISOS...
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.inicio:
                /*if(verifyUser(view)) {
                    Intent intent = new Intent(this, EnrolaInitActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }*/



                AdminBaseDatos adminBaseDatos = new AdminBaseDatos(this);
                List<User> users = adminBaseDatos.usu_getAll();
                adminBaseDatos.closeBaseDtos();
                UnidosApplication.setUser(users.get(7));

                Intent intent = new Intent(this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                /*startActivity(new Intent(this,SettingActivity.class));*/
                break;
            default:
                break;
        }
    }
    private boolean verifyUser(View view){
        boolean bRet = true;
        AdminBaseDatos adminBaseDatos = new AdminBaseDatos(this);
        if(adminBaseDatos.usu_getAll() == null){
            Util.SnackbarCreate("Usuarios","Usuarios no registrado, por favor agregarlos", "Ir a Config",this, SettingActivity.class,view);
            return false;
        }

        if(adminBaseDatos.usu_getAll_Enrroled() == null) {
            Util.SnackbarCreate("Usuarios","Usuarios no Enrolados, por favor Enrolar", "Ir a Config",this,SettingActivity.class,view);
            return false;
        }


        return bRet;
    }

    private void requestWakeLock() {
        Logger.d("START!");
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, ":BioMini WakeLock");
        mWakeLock.acquire();
    }

    private void initUsbListener() {
        Log.d("DP_DLOG","initUsbListener "+"inicia");
        //scrollBottom("initUsbListener "+"inicia");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);

        this.registerReceiver(mUsbReceiver, new IntentFilter(ACTION_USB_PERMISSION));
        //IntentFilter attachfilter = new IntentFilter(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        //this.registerReceiver(mUsbReceiver, attachfilter);
        //IntentFilter detachfilter = new IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED);
        //this.registerReceiver(mUsbReceiver, detachfilter);
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_USB_PERMISSION:
                    Log.d("DP_DLOG","onReceive "+"ACTION_USB_PERMISSION");
                    //scrollBottom("onReceive "+"ACTION_USB_PERMISSION");
                    boolean hasUsbPermission = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
                    if (hasUsbPermission) {
                        //Log.d("DP_DLOG","onReceive "+mUsbDevice.getDeviceName() + " is acquire the usb permission. activate this device.");
                        //scrollBottom("onReceive "+mUsbDevice.getDeviceName() + " is acquire the usb permission. activate this device.");
                        //mHandler.sendEmptyMessage(ACTIVATE_USB_DEVICE);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"se necesitan permisos",Toast.LENGTH_LONG).show();
                            }
                        });

                        //scrollBottom("onReceive "+"USB permission is not granted!");
                        Log.d("DP_DLOG","onReceive "+"USB permission is not granted!");
                    }
                    break;
                case UsbManager.ACTION_USB_DEVICE_ATTACHED:
                    Log.d("DP_DLOG","onReceive "+"ACTION_USB_DEVICE_ATTACHED!");
                    //scrollBottom("onReceive "+"ACTION_USB_DEVICE_ATTACHED!");
                    //addDeviceToUsbDeviceList();
                    break;
                case UsbManager.ACTION_USB_DEVICE_DETACHED:
                    //scrollBottom("onReceive "+"ACTION_USB_DEVICE_DETACHED!");
                    //setLogInTextView(getResources().getString(R.string.usb_detached));
                    //mViewPager.setCurrentItem(0);
                    //removeDevice();
                    break;
                default:
                    break;
            }
        }
    };

}