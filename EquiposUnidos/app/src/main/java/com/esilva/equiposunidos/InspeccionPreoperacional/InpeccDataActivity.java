package com.esilva.equiposunidos.InspeccionPreoperacional;

import static com.esilva.equiposunidos.util.Constantes.EQUIPO_TIPO_CARGADOR;
import static com.esilva.equiposunidos.util.Constantes.EQUIPO_TIPO_EXCAVADORA;
import static com.esilva.equiposunidos.util.Constantes.EQUIPO_TIPO_OTRO;
import static com.esilva.equiposunidos.util.Constantes.FILE_IMAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esilva.equiposunidos.Dialog.CustumerDialog;
import com.esilva.equiposunidos.Dialog.ProgressDialog;
import com.esilva.equiposunidos.MainActivity;
import com.esilva.equiposunidos.Mantenimiento.InsumosActivity;
import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.Report.ReportInspeccion;
import com.esilva.equiposunidos.application.UnidosApplication;
import com.esilva.equiposunidos.db.models.Equipos;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InpeccDataActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private TextView tvTitle,tvOper,tvFecha;
    private EditText edOper,edCedula,edSuper,edObservaciones,edHorome,edLugar;
    private Button btGuardar,btRegresar,btHome;
    private LinearLayout liCedula;

    private Equipos equipos;
    private String stOperador,stCedula,stSupervisor,stHorometro,stLugar,stObservacion;

    private ProgressDialog progressDialog;
    private CustumerDialog custumerDialog;
    private boolean isHora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inpecc_data);

        equipos = UnidosApplication.getEquipo();
        progressDialog = new ProgressDialog(this,"Generando Reporte");

        setView();
    }

    private void setView() {
        tvFecha  = findViewById(R.id.fechct);
        imageView = findViewById(R.id.imImaInspData);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/"+equipos.getFoto());
        File pathImage = new File(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE, equipos.getFoto());
        if(pathImage.exists() == false)
            uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/fotico.png");
        imageView.setImageURI(uri);

        liCedula = findViewById(R.id.liCedulaInp);

        tvTitle = findViewById(R.id.tvTitleInspec);
        tvOper = findViewById(R.id.tvDataInOper);

        if(equipos.getTipo() == EQUIPO_TIPO_CARGADOR)
            tvTitle.setText("INSPECCION PREOPERACION\nCARGADOR");
        else if(equipos.getTipo() == EQUIPO_TIPO_EXCAVADORA)
            tvTitle.setText("INSPECCION PREOPERACION\nEXCAVADORA");
        else {
            tvTitle.setText("INSPECCION PREOPERACION\nVEHICULO");
            tvOper.setText("Nombre del conductor:");
            liCedula.setVisibility(View.VISIBLE);
        }

        tvOper = findViewById(R.id.tvDataInOper);

        edOper = findViewById(R.id.edDataInOper);
        edCedula = findViewById(R.id.edDataInCedula);
        edSuper = findViewById(R.id.edDataInSuper);
        edObservaciones = findViewById(R.id.edDataInObserva);
        edHorome = findViewById(R.id.edDataInHorom);
        edLugar = findViewById(R.id.edDataInLugar);


        btGuardar = findViewById(R.id.btContInGuardar);
        btGuardar.setOnClickListener(this);

        btRegresar = findViewById(R.id.btContInRegresa);
        btRegresar.setOnClickListener(this);

        btHome = findViewById(R.id.btContInHome);
        btHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btContInGuardar:
                if(validarData()){
                    btGuardar.setEnabled(false);
                    btRegresar.setEnabled(false);
                    btHome.setEnabled(false);
                    progressDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ReportInspeccion reportInspeccion = new ReportInspeccion(InpeccDataActivity.this);
                            reportInspeccion.setCedula(stCedula);
                            reportInspeccion.setHorometro(stHorometro);
                            reportInspeccion.setLugar(stLugar);
                            reportInspeccion.setObservacion(stObservacion);
                            reportInspeccion.setSupervisor(stSupervisor);
                            reportInspeccion.setOperador(stOperador);
                            boolean b = reportInspeccion.buildReport();
                            showResult(b);
                        }
                    }).start();
                }else{
                    Toast.makeText(this,"Por favor diligencie todos los datos",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btContInRegresa:
                btGuardar.setEnabled(false);
                btRegresar.setEnabled(false);
                btHome.setEnabled(false);
                onBackPressed();
                break;
            case R.id.btContInHome:
                btGuardar.setEnabled(false);
                btRegresar.setEnabled(false);
                btHome.setEnabled(false);
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    private boolean validarData() {
        stObservacion = edObservaciones.getText().toString();
        stOperador = edOper.getText().toString();
        if (stOperador.isEmpty())
            return false;

        if(equipos.getTipo() == EQUIPO_TIPO_OTRO){
            stCedula = edCedula.getText().toString().trim();
            if (stCedula.isEmpty())
                return false;
        }

        stSupervisor = edSuper.getText().toString();
        if (stSupervisor.isEmpty())
            return false;

        stHorometro = edHorome.getText().toString();
        if (stHorometro.isEmpty())
            return false;

        stLugar = edLugar.getText().toString();
        if (stLugar.isEmpty())
            return false;

        return true;
    }

    private void showResult(boolean result){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                if(result)
                    custumerDialog = new CustumerDialog(InpeccDataActivity.this,"SUCCESS!", "Reporte guardado",false,false);
                else
                    custumerDialog = new CustumerDialog(InpeccDataActivity.this,"FAIL!", "Error Guardando el reporte",true,false);
                custumerDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        custumerDialog.dismiss();
                        startActivity(new Intent(InpeccDataActivity.this, MainActivity.class));
                        finish();
                    }
                },4000);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isHora = true;
        btGuardar.setEnabled(true);
        btRegresar.setEnabled(true);
        btHome.setEnabled(true);
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
                tvFecha.setText(date);
            }
        });
    }
}