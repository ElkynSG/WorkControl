package com.esilva.equiposunidos.Mantenimiento;

import static com.esilva.equiposunidos.util.Constantes.EQUIPO_TIPO_CARGADOR;
import static com.esilva.equiposunidos.util.Constantes.FILE_IMAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.application.UnidosApplication;
import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.Manteni;
import com.esilva.equiposunidos.util.ManteAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.PropertyReference0Impl;

public class ActiPreventivaActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listaGeneral;
    private List<Manteni> listItemsMante;
    private ManteAdapter generalAdapter;

    private Button btListaEnviar,btActiRegresar;
    private ImageView imaEquipo;
    private Equipos equipoSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_acti_preventiva);
        equipoSelect = UnidosApplication.getEquipo();
        setView();
    }

    private void setView() {
        imaEquipo = findViewById(R.id.imImageEquipo);
        btListaEnviar = findViewById(R.id.btContiActi);
        btListaEnviar.setOnClickListener(this);
        btActiRegresar = findViewById(R.id.btActiRegresa);
        btActiRegresar.setOnClickListener(this);

        generalAdapter = new ManteAdapter(this);
        if(equipoSelect.getTipo() == EQUIPO_TIPO_CARGADOR) {
            generalAdapter.addArray("GENERAL", getResources().getStringArray(R.array.ca_general));
            generalAdapter.addArray("SISTEMA DE COMBUSTIBLE", getResources().getStringArray(R.array.ca_sistemaConbustible));
            generalAdapter.addArray("SISTEMA DE ADMISION DE AIRE", getResources().getStringArray(R.array.ca_sistemaAdmision));
            generalAdapter.addArray("MOTOR", getResources().getStringArray(R.array.ca_motor));
            generalAdapter.addArray("SISTEMA DE ENFRIAMIENTO", getResources().getStringArray(R.array.ca_sistemaEnfriamiento));
            generalAdapter.addArray("SISTEMA HIDRAULICO", getResources().getStringArray(R.array.ca_sistemaHidraulico));
            generalAdapter.addArray("SISTEMA DE ARTICULACIÓN", getResources().getStringArray(R.array.ca_sistemaArticulacion));
            generalAdapter.addArray("TRANSMISIÓN", getResources().getStringArray(R.array.ca_trasmision));
            generalAdapter.addArray("FRENOS", getResources().getStringArray(R.array.ca_frenos));
            generalAdapter.addArray("SISTEMA DE DIRECCIÓN", getResources().getStringArray(R.array.ca_sistemaDireccion));
            generalAdapter.addArray("SISTEMA ELECTRICO", getResources().getStringArray(R.array.ca_sistemaElectrico));
        }else{
            generalAdapter.addArray("GENERAL", getResources().getStringArray(R.array.ex_general));
            generalAdapter.addArray("SISTEMA DE COMBUSTIBLE", getResources().getStringArray(R.array.ex_sistemaConbustible));
            generalAdapter.addArray("SISTEMA DE ADMICION DE AIRE", getResources().getStringArray(R.array.ex_sistemaAdmision));
            generalAdapter.addArray("MOTOR", getResources().getStringArray(R.array.ex_motor));
            generalAdapter.addArray(" SISTEMA DE ENFRIAMIENTO", getResources().getStringArray(R.array.ex_sistemaEnfriamiento));
            generalAdapter.addArray("SISTEMA HIDRAULICO", getResources().getStringArray(R.array.ex_sistemaHidraulico));
            generalAdapter.addArray("SISTEMA MOTOR DE GIRO", getResources().getStringArray(R.array.ex_sistemaGiro));
            generalAdapter.addArray("SISTEMA DE MANDOS FINALES", getResources().getStringArray(R.array.ex_sistemaMandos));
            generalAdapter.addArray("SISTEMA ELECTRICO", getResources().getStringArray(R.array.ex_sistemaElectrico));
        }
        listaGeneral = findViewById(R.id.listGeneral);
        listaGeneral.setAdapter(generalAdapter);


        Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/"+equipoSelect.getFoto());
        File pathImage = new File(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE, equipoSelect.getFoto());
        if(pathImage.exists() == false)
            uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/fotico.png");

        imaEquipo.setBackground(getResources().getDrawable(R.drawable.shape_image_azul));
        imaEquipo.setImageURI(uri);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btContiActi:
                listItemsMante = generalAdapter.getmList();
                if(verifyCheck()) {
                    UnidosApplication.setListManteni(listItemsMante);
                    startActivity(new Intent(this, InsumosActivity.class));
                }else
                    Toast.makeText(this,"Complete la revision, Por favor",Toast.LENGTH_LONG).show();
                break;
            case R.id.btActiRegresa:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean verifyCheck(){
        boolean bRet = true;
        for(Manteni ma:listItemsMante) {
            if(!ma.isbTitle()) {
                if (!ma.isbNA() && !ma.isbSI()) {
                    return false;
                }
            }
        }
        return bRet;
    }
}