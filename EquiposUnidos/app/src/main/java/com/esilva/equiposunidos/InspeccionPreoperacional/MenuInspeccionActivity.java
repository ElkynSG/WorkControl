package com.esilva.equiposunidos.InspeccionPreoperacional;

import static com.esilva.equiposunidos.util.Constantes.EQUIPO_TIPO_CARGADOR;
import static com.esilva.equiposunidos.util.Constantes.EQUIPO_TIPO_EXCAVADORA;
import static com.esilva.equiposunidos.util.Constantes.FILE_IMAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.application.UnidosApplication;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.Equipos;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MenuInspeccionActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imaMaq2,imaMaq3,imaMaq4,imaMaq5,imaMaq6,imaMaq1,imaMaq7;
    private Button btContinuar,btRegresar;
    private TextView fecha_horaInspec;
    private AdminBaseDatos adminBaseDatos;
    private List<Equipos> equipos;
    private int idEquipo;
    private boolean isHora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_inspeccion);
        adminBaseDatos = new AdminBaseDatos(this);
        equipos = adminBaseDatos.equi_getAll();
        idEquipo = -1;
        setView();

    }

    private void setView() {
        fecha_horaInspec = findViewById(R.id.fecha_horaInspec);

        imaMaq1 = findViewById(R.id.imaMaq1);
        imaMaq1.setOnClickListener(this);
        imaMaq2 = findViewById(R.id.imaMaq2);
        imaMaq2.setOnClickListener(this);
        imaMaq3 = findViewById(R.id.imaMaq3);
        imaMaq3.setOnClickListener(this);
        imaMaq4 = findViewById(R.id.imaMaq4);
        imaMaq4.setOnClickListener(this);
        imaMaq5 = findViewById(R.id.imaMaq5);
        imaMaq5.setOnClickListener(this);
        imaMaq6 = findViewById(R.id.imaMaq6);
        imaMaq6.setOnClickListener(this);
        imaMaq7 = findViewById(R.id.imaMaq7);
        imaMaq7.setOnClickListener(this);

        for (int i=0;i<7;i++){
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/"+equipos.get(i).getFoto());
            File pathImage = new File(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE, equipos.get(i).getFoto());
            if(pathImage.exists() == false)
                uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/fotico.png");

            switch (i){
                case 0:
                    imaMaq1.setImageURI(uri);
                    break;
                case 1:
                    imaMaq2.setImageURI(uri);
                    break;
                case 2:
                    imaMaq3.setImageURI(uri);
                    break;
                case 3:
                    imaMaq4.setImageURI(uri);
                    break;
                case 4:
                    imaMaq5.setImageURI(uri);
                    break;
                case 5:
                    imaMaq6.setImageURI(uri);
                    break;
                case 6:
                    imaMaq7.setImageURI(uri);
                    break;
                default:
                    break;
            }
        }

        btContinuar = findViewById(R.id.btContiInspec);
        btContinuar.setOnClickListener(this);

        btRegresar = findViewById(R.id.btInspecRegreso);
        btRegresar.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imaMaq1:
                imaMaq1.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                imaMaq2.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq3.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq4.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq5.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq6.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq7.setBackgroundColor(getResources().getColor(R.color.transparant));
                idEquipo = 0;
                break;
            case R.id.imaMaq2:
                imaMaq1.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq2.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                imaMaq3.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq4.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq5.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq6.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq7.setBackgroundColor(getResources().getColor(R.color.transparant));
                idEquipo = 1;
                break;
            case R.id.imaMaq3:
                imaMaq1.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq2.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq3.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                imaMaq4.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq5.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq6.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq7.setBackgroundColor(getResources().getColor(R.color.transparant));
                idEquipo = 2;
                break;
            case R.id.imaMaq4:
                imaMaq1.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq2.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq3.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq4.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                imaMaq5.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq6.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq7.setBackgroundColor(getResources().getColor(R.color.transparant));
                idEquipo = 3;
                break;
            case R.id.imaMaq5:
                imaMaq1.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq2.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq3.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq4.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq5.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                imaMaq6.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq7.setBackgroundColor(getResources().getColor(R.color.transparant));
                idEquipo = 4;
                break;
            case R.id.imaMaq6:
                imaMaq1.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq2.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq3.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq4.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq5.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq6.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                imaMaq7.setBackgroundColor(getResources().getColor(R.color.transparant));
                idEquipo = 5;
                break;
            case R.id.imaMaq7:
                imaMaq1.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq2.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq3.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq4.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq5.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq6.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq7.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                idEquipo = 6;
                break;
            case R.id.btContiInspec:
                if(idEquipo>=0) {
                    btContinuar.setEnabled(false);
                    btRegresar.setEnabled(false);
                    UnidosApplication.setEquipo(equipos.get(idEquipo));
                    if(equipos.get(idEquipo).getTipo() == EQUIPO_TIPO_CARGADOR)
                        startActivity(new Intent(MenuInspeccionActivity.this,CargadorInspActivity.class));
                    else if(equipos.get(idEquipo).getTipo() == EQUIPO_TIPO_EXCAVADORA)
                        startActivity(new Intent(MenuInspeccionActivity.this,ExcavadoraInspActivity.class));
                    else
                        startActivity(new Intent(MenuInspeccionActivity.this,VehiculoInspActivity.class));

                }else
                    Toast.makeText(this,"Por favor, Seleccione maquina",Toast.LENGTH_LONG).show();
                break;
            case R.id.btInspecRegreso:
                btContinuar.setEnabled(false);
                btRegresar.setEnabled(false);
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        isHora = false;
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        adminBaseDatos.closeBaseDtos();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isHora = true;
        btContinuar.setEnabled(true);
        btRegresar.setEnabled(true);
        ShowHours();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isHora = false;
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
                fecha_horaInspec.setText(date);
            }
        });
    }

}