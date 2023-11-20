package com.esilva.equiposunidos.InspeccionPreoperacional;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.application.UnidosApplication;
import com.esilva.equiposunidos.db.models.DataInspeccion;
import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.Manteni;
import com.esilva.equiposunidos.util.InspeccionAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CargadorInspActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private ImageView imImaCarga;
    private TextView fecha_hora;
    private Button btContiInspec, btRegresaInsp;
    boolean[] isRojosBooleans = new boolean[]{false,true ,true ,true ,true ,false,false,false,false,false,false,true ,false,false,
            false,false,true ,false,false,false,true ,true ,false,false,false,false,false,false,
            false,true ,false,true ,false,true ,true ,false,false,false,false,false,true ,false};
    private String[] dataArray;
    private InspeccionAdapter inspeccionAdapter;

    private Equipos equipos;
    private boolean isHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cargador_insp);

        dataArray = getResources().getStringArray(R.array.inspec_cargador);
        inspeccionAdapter = new InspeccionAdapter(this);
        inspeccionAdapter.addArray(isRojosBooleans,dataArray);

        equipos = UnidosApplication.getEquipo();
        setView();
    }

    private void setView() {
        fecha_hora = findViewById(R.id.fecha_carga);

        listView = findViewById(R.id.listInpecCarg);
        listView.setAdapter(inspeccionAdapter);

        imImaCarga = findViewById(R.id.imImaInspCarga);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/"+equipos.getFoto());
        File pathImage = new File(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE, equipos.getFoto());
        if(pathImage.exists() == false)
            uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/fotico.png");
        imImaCarga.setImageURI(uri);

        btContiInspec = findViewById(R.id.btContiCarga);
        btContiInspec.setOnClickListener(this);

        btRegresaInsp = findViewById(R.id.btRegresaCarga);
        btRegresaInsp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btContiCarga:
                if (validaData()) {
                    btContiInspec.setEnabled(false);
                    btRegresaInsp.setEnabled(false);
                    UnidosApplication.setListInspeccion(inspeccionAdapter.getmList());
                    startActivity(new Intent(this, InpeccDataActivity.class));
                }else{
                    Toast.makeText(this,"Por favor diligencie todos los datos",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btRegresaCarga:
                btContiInspec.setEnabled(false);
                btRegresaInsp.setEnabled(false);
                onBackPressed();
                break;
            default:
                break;

        }
    }

    private boolean validaData(){
        boolean bRet = true;
        for(DataInspeccion ma:inspeccionAdapter.getmList()) {
            if (!ma.isPosB() && !ma.isPosM()) {
                return false;
            }

        }
        return bRet;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isHora = true;
        btContiInspec.setEnabled(true);
        btRegresaInsp.setEnabled(true);
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
                fecha_hora.setText(date);
            }
        });
    }

}