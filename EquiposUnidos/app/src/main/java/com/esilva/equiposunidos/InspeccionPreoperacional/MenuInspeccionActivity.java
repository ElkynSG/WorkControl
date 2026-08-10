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
import android.widget.RelativeLayout;
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

    private ImageView imaMaq2,imaMaq3,imaMaq4,imaMaq5,imaMaq6,imaMaq1,imaMaq7,imaMaq8,imaMaq9,imaMaq10;
    private RelativeLayout rela1,rela2,rela3,rela4,rela5;
    private Button btContinuar,btRegresar;
    private TextView fecha_horaInspec;
    private TextView tvMaq1,tvMaq2,tvMaq3,tvMaq4,tvMaq5,tvMaq6,tvMaq7,tvMaq8,tvMaq9,tvMaq10;
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
        tvMaq1 = findViewById(R.id.tvMaq1);
        tvMaq2 = findViewById(R.id.tvMaq2);
        tvMaq3 = findViewById(R.id.tvMaq3);
        tvMaq4 = findViewById(R.id.tvMaq4);
        tvMaq5 = findViewById(R.id.tvMaq5);
        tvMaq6 = findViewById(R.id.tvMaq6);
        tvMaq7 = findViewById(R.id.tvMaq7);
        tvMaq8 = findViewById(R.id.tvMaq8);
        tvMaq9 = findViewById(R.id.tvMaq9);
        tvMaq10 = findViewById(R.id.tvMaq10);

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
        imaMaq8 = findViewById(R.id.imaMaq8);
        imaMaq8.setOnClickListener(this);
        imaMaq9 = findViewById(R.id.imaMaq9);
        imaMaq9.setOnClickListener(this);
        imaMaq10 = findViewById(R.id.imaMaq10);
        imaMaq10.setOnClickListener(this);

        rela1 = findViewById(R.id.rel1);
        rela2 = findViewById(R.id.rel2);
        rela3 = findViewById(R.id.rel3);
        rela4 = findViewById(R.id.rel4);
        rela5 = findViewById(R.id.rel5);

        int max = equipos.size();
        if(max>10)
            max = 10;

        for (int i=0;i<max;i++){
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/"+equipos.get(i).getFoto());
            File pathImage = new File(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE, equipos.get(i).getFoto());
            if(pathImage.exists() == false)
                uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/fotico.png");

            switch (i){
                case 0:
                    imaMaq1.setImageURI(uri);
                    tvMaq1.setText(equipos.get(i).getNombre());
                    break;
                case 1:
                    imaMaq2.setImageURI(uri);
                    tvMaq2.setText(equipos.get(i).getNombre());
                    break;
                case 2:
                    imaMaq3.setImageURI(uri);
                    tvMaq3.setText(equipos.get(i).getNombre());
                    break;
                case 3:
                    imaMaq4.setImageURI(uri);
                    tvMaq4.setText(equipos.get(i).getNombre());
                    break;
                case 4:
                    imaMaq5.setImageURI(uri);
                    tvMaq5.setText(equipos.get(i).getNombre());
                    break;
                case 5:
                    imaMaq6.setImageURI(uri);
                    tvMaq6.setText(equipos.get(i).getNombre());
                    rela1.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    imaMaq7.setImageURI(uri);
                    tvMaq7.setText(equipos.get(i).getNombre());
                    rela2.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    imaMaq8.setImageURI(uri);
                    tvMaq8.setText(equipos.get(i).getNombre());
                    rela3.setVisibility(View.VISIBLE);
                    break;
                case 8:
                    imaMaq9.setImageURI(uri);
                    tvMaq9.setText(equipos.get(i).getNombre());
                    rela4.setVisibility(View.VISIBLE);
                    break;
                case 9:
                    imaMaq10.setImageURI(uri);
                    tvMaq10.setText(equipos.get(i).getNombre());
                    rela5.setVisibility(View.VISIBLE);
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
        int id = view.getId();

            if(id == R.id.imaMaq1) {
                imaMaq1.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                imaMaq2.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq3.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq4.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq5.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq6.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq7.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq8.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq9.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq10.setBackgroundColor(getResources().getColor(R.color.transparant));
                idEquipo = 0;
            }
            else if(id == R.id.imaMaq2) {
                imaMaq1.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq2.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                imaMaq3.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq4.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq5.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq6.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq7.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq8.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq9.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq10.setBackgroundColor(getResources().getColor(R.color.transparant));
                idEquipo = 1;
            }
            else if(id == R.id.imaMaq3) {
                imaMaq1.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq2.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq3.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                imaMaq4.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq5.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq6.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq7.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq8.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq9.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq10.setBackgroundColor(getResources().getColor(R.color.transparant));
                idEquipo = 2;
            }
            else if(id == R.id.imaMaq4) {
                imaMaq1.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq2.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq3.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq4.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                imaMaq5.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq6.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq7.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq8.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq9.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq10.setBackgroundColor(getResources().getColor(R.color.transparant));
                idEquipo = 3;
            }
            else if(id ==  R.id.imaMaq5) {
                imaMaq1.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq2.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq3.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq4.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq5.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                imaMaq6.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq7.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq8.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq9.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq10.setBackgroundColor(getResources().getColor(R.color.transparant));
                idEquipo = 4;
            }
            else if(id ==  R.id.imaMaq6) {
                imaMaq1.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq2.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq3.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq4.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq5.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq6.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                imaMaq7.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq8.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq9.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq10.setBackgroundColor(getResources().getColor(R.color.transparant));
                idEquipo = 5;
            }
            else if(id ==  R.id.imaMaq7){
                imaMaq1.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq2.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq3.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq4.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq5.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq6.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq7.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                imaMaq8.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq9.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq10.setBackgroundColor(getResources().getColor(R.color.transparant));
                idEquipo = 6;
            }
            else if(id ==  R.id.imaMaq8){
                imaMaq1.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq2.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq3.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq4.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq5.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq6.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq7.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq8.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                imaMaq9.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq10.setBackgroundColor(getResources().getColor(R.color.transparant));
                idEquipo = 7;
            }
            else if(id ==  R.id.imaMaq9){
                imaMaq1.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq2.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq3.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq4.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq5.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq6.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq7.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq8.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq9.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                imaMaq10.setBackgroundColor(getResources().getColor(R.color.transparant));
                idEquipo = 8;
            }
            else if(id ==  R.id.imaMaq10){
                imaMaq1.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq2.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq3.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq4.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq5.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq6.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq7.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq8.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq9.setBackgroundColor(getResources().getColor(R.color.transparant));
                imaMaq10.setBackground(getResources().getDrawable(R.drawable.shape_maq));
                idEquipo = 9;
            }
            else if(id ==  R.id.btContiInspec){
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
            }
            else if(id ==  R.id.btInspecRegreso) {
                btContinuar.setEnabled(false);
                btRegresar.setEnabled(false);
                onBackPressed();
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