package com.esilva.equiposunidos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout inicio;

    int REQUEST_COD = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        solicitarPermisos();
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
            int PermisoStoragEManager = ContextCompat.checkSelfPermission(this,Manifest.permission.MANAGE_EXTERNAL_STORAGE);
            int PermisoSetting = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_SETTINGS);

            if(PermisoSetting == PackageManager.PERMISSION_GRANTED && PermisoStorageRead == PackageManager.PERMISSION_GRANTED && PermisoStoragEManager == PackageManager.PERMISSION_GRANTED && PermisoStorageWrite == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"permiso staora otorgado",Toast.LENGTH_LONG);
            }else{
                requestPermissions(new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE ,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_SETTINGS

                },REQUEST_COD);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.inicio:
                startActivity(new Intent(this,Test.class));
                break;
            default:
                break;
        }

    }
}