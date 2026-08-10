package com.esilva.equiposunidos;

import static com.esilva.equiposunidos.util.Constantes.PERFIL_NIVEL_1;
import static com.esilva.equiposunidos.util.Constantes.PERFIL_NIVEL_2;
import static com.esilva.equiposunidos.util.Constantes.PERFIL_NIVEL_3;
import static com.esilva.equiposunidos.util.Constantes.PERFIL_NIVEL_4;
import static com.esilva.equiposunidos.util.Constantes.PERFIL_NIVEL_5;
import static com.esilva.equiposunidos.util.Constantes.PERFIL_NIVEL_6;
import static com.esilva.equiposunidos.util.Constantes.PERFIL_NIVEL_7;
import static com.esilva.equiposunidos.util.Constantes.PERFIL_NIVEL_8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.esilva.equiposunidos.InspeccionPreoperacional.MenuInspeccionActivity;
import com.esilva.equiposunidos.Inventario.MenuInventarioActivity;
import com.esilva.equiposunidos.Mantenimiento.TipoManteniActivity;
import com.esilva.equiposunidos.RegistroInOut.RegisterInOutActivity;
import com.esilva.equiposunidos.Setting.SettingActivity;
import com.esilva.equiposunidos.application.UnidosApplication;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.User;
import com.esilva.equiposunidos.util.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btSetting;
    private CardView btIngreso,btManteni,btInventario,btInspeccion;
    private TextView tvDateHour;

    private boolean isHora;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        setView();
        user = UnidosApplication.getUser();
        setConfPerfil();
    }

    private void setView() {
        btSetting = findViewById(R.id.settings);
        btSetting.setOnClickListener(this);

        btIngreso = findViewById(R.id.btIngreso);
        btIngreso.setOnClickListener(this);

        btManteni = findViewById(R.id.btManteni);
        btManteni.setOnClickListener(this);

        btInventario = findViewById(R.id.btInventario);
        btInventario.setOnClickListener(this);

        btInspeccion = findViewById(R.id.btInspeccion);
        btInspeccion.setOnClickListener(this);

        tvDateHour = findViewById(R.id.fecha_horaMenu);

    }

    private void setConfPerfil(){
        if(user.getPerfil() == PERFIL_NIVEL_1){

        }else if(user.getPerfil() == PERFIL_NIVEL_2){
            btSetting.setVisibility(View.INVISIBLE);
        }else if(user.getPerfil() == PERFIL_NIVEL_3){
            btSetting.setVisibility(View.INVISIBLE);
            btManteni.setVisibility(View.INVISIBLE);
            btInventario.setVisibility(View.INVISIBLE);
        }else if(user.getPerfil() == PERFIL_NIVEL_4){
            btSetting.setVisibility(View.INVISIBLE);
            btInventario.setVisibility(View.INVISIBLE);
        }else if(user.getPerfil() == PERFIL_NIVEL_5){
            btSetting.setVisibility(View.INVISIBLE);
            btManteni.setVisibility(View.INVISIBLE);
            btInventario.setVisibility(View.INVISIBLE);
        }else if(user.getPerfil() == PERFIL_NIVEL_6){

        }
        else if(user.getPerfil() == PERFIL_NIVEL_7){
            btSetting.setVisibility(View.INVISIBLE);
            btManteni.setVisibility(View.INVISIBLE);
        }
        else if(user.getPerfil() == PERFIL_NIVEL_8){
            btSetting.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isHora = true;
        ShowHours();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isHora = false;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if( id ==  R.id.settings) {
            startActivity(new Intent(MenuActivity.this, SettingActivity.class));
        }
        else if( id == R.id.btIngreso) {
            if (checkConfig(view))
                startActivity(new Intent(MenuActivity.this, RegisterInOutActivity.class));
        }
        else if( id == R.id.btManteni) {
            if (checkConfig(view))
                startActivity(new Intent(MenuActivity.this, TipoManteniActivity.class));
        }
        else if( id == R.id.btInventario) {
            if (checkConfig(view))
                startActivity(new Intent(MenuActivity.this, MenuInventarioActivity.class));
        }
        else if( id == R.id.btInspeccion) {
            if (checkConfig(view))
                startActivity(new Intent(MenuActivity.this, MenuInspeccionActivity.class));
        }

    }

    @Override
    public void onBackPressed() {
        isHora = false;
        startActivity(new Intent(this,MainActivity.class));
    }

    private void ShowHours(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (isHora){
                    showDate(getNameFile());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private String getNameFile(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(date);
    }
    private void showDate(String date){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvDateHour.setText(date);
            }
        });
    }

    private boolean checkConfig(View view){
        AdminBaseDatos adminBaseDatos = new AdminBaseDatos(this);
        List<String> lugares;
        List<Equipos> ListEquipo;
        List<String> listTecnicos;
        ListEquipo = adminBaseDatos.equi_getAll();

        if(ListEquipo  == null || ListEquipo.isEmpty()){
            Util.SnackbarCreate("Equipos","Lista de equipos NO cargados", "Ir a Splash",this, MainActivity.class,view);
            return false;
        }

        lugares = adminBaseDatos.luga_getAll();
        if(lugares  == null || lugares.isEmpty()){
            Util.SnackbarCreate("Lugares","Lista de lugares NO cargados", "Ir a Splash",this, MainActivity.class,view);
            return false;
        }

        listTecnicos = adminBaseDatos.tec_getAll();
        if(listTecnicos  == null || listTecnicos.isEmpty()){
            Util.SnackbarCreate("Tecnicos","Lista de tecnicos NO cargados", "Ir a Splash",this, MainActivity.class,view);
            return false;
        }
        return true;
    }


}