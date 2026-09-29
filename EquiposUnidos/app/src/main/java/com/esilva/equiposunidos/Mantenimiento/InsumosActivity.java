package com.esilva.equiposunidos.Mantenimiento;

import static com.esilva.equiposunidos.Report.ReportMantePreven.REPORT_TYPE_VOLQUETA;
import static com.esilva.equiposunidos.util.Constantes.EQUIPO_TIPO_VOLQUETA;
import static com.esilva.equiposunidos.util.Constantes.FILE_IMAGE;
import static com.esilva.equiposunidos.util.Constantes.IMAGE_FIRMA;
import static com.esilva.equiposunidos.util.Constantes.IMAGE_MANTE_1;
import static com.esilva.equiposunidos.util.Constantes.IMAGE_MANTE_2;
import static com.esilva.equiposunidos.util.Constantes.IMAGE_MANTE_3;
import static com.esilva.equiposunidos.util.Constantes.IMAGE_MANTE_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esilva.equiposunidos.Dialog.CustumerDialog;
import com.esilva.equiposunidos.Dialog.DialogFirma;
import com.esilva.equiposunidos.Dialog.ProgressDialog;
import com.esilva.equiposunidos.MainActivity;
import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.Report.ReportManteCorrec;
import com.esilva.equiposunidos.Report.ReportMantePreven;
import com.esilva.equiposunidos.application.UnidosApplication;
import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.Insumos;
import com.esilva.equiposunidos.util.CameraDialogFragment;

import java.io.File;


public class InsumosActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner sp1Insumo,sp2Insumo,sp3Insumo,sp4Insumo,sp5Insumo,sp6Insumo,sp7Insumo,sp8Insumo,sp9Insumo,sp10Insumo,sp11Insumo,sp12Insumo;
    private EditText ed1Insumo,ed2Insumo,ed3Insumo,ed4Insumo,ed5Insumo,ed6Insumo,ed7Insumo,ed8Insumo,ed9Insumo,ed10Insumo,ed11Insumo,ed12Insumo;
    private EditText edRepuestos,edOtros,edObservaciones;
    private ImageView btFirma,imaView;
    private Button btEnviar,btRegresa;
    private TextView tvVolqueta;

    private Insumos insumos;
    private boolean isFirma=false;
    private Equipos equiposs;
    private ImageView ima1,ima2,ima3,ima4;

    private ProgressDialog progressDialog;
    private CustumerDialog custumerDialog;
    private boolean result;
    private String[] comments;
    private boolean isFoto1,isFoto2,isFoto3,isFoto4;
    CameraDialogFragment dialog = new CameraDialogFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insumos);
        equiposs = UnidosApplication.getEquipo();
        comments = new String[4];
        isFoto1 = false;
        isFoto2 = false;
        isFoto3 = false;
        isFoto4 = false;
        setView();

        progressDialog = new ProgressDialog(this,"Generando Reporte");
        insumos = new Insumos();
    }

    private void setView() {
        imaView = findViewById(R.id.imImageEquipoIns);
        ima1 = findViewById(R.id.ima1);
        ima2 = findViewById(R.id.ima2);
        ima3 = findViewById(R.id.ima3);
        ima4 = findViewById(R.id.ima4);

        ima1.setOnClickListener(this);
        ima2.setOnClickListener(this);
        ima3.setOnClickListener(this);
        ima4.setOnClickListener(this);



        tvVolqueta = findViewById(R.id.tvVolqueta);
        if(equiposs.getTipo() == EQUIPO_TIPO_VOLQUETA){
            tvVolqueta.setText("Filtro de trasnmision");
        }


        sp1Insumo =findViewById(R.id.sp1Insumo);
        sp2Insumo =findViewById(R.id.sp2Insumo);
        sp3Insumo =findViewById(R.id.sp3Insumo);
        sp4Insumo =findViewById(R.id.sp4Insumo);
        sp5Insumo =findViewById(R.id.sp5Insumo);
        sp6Insumo =findViewById(R.id.sp6Insumo);
        sp7Insumo =findViewById(R.id.sp7Insumo);
        sp8Insumo =findViewById(R.id.sp8Insumo);
        sp9Insumo =findViewById(R.id.sp9Insumo);
        sp10Insumo =findViewById(R.id.sp10Insumo);
        sp11Insumo =findViewById(R.id.sp11Insumo);
        sp12Insumo =findViewById(R.id.sp12Insumo);

        ed1Insumo =findViewById(R.id.ed1Insumo);
        ed2Insumo =findViewById(R.id.ed2Insumo);
        ed3Insumo =findViewById(R.id.ed3Insumo);
        ed4Insumo =findViewById(R.id.ed4Insumo);
        ed5Insumo =findViewById(R.id.ed5Insumo);
        ed6Insumo =findViewById(R.id.ed6Insumo);
        ed7Insumo =findViewById(R.id.ed7Insumo);
        ed8Insumo =findViewById(R.id.ed8Insumo);
        ed9Insumo =findViewById(R.id.ed9Insumo);
        ed10Insumo =findViewById(R.id.ed10Insumo);
        ed11Insumo =findViewById(R.id.ed11Insumo);
        ed12Insumo =findViewById(R.id.ed12Insumo);

        edRepuestos =findViewById(R.id.edRepuesto);
        edOtros =findViewById(R.id.edOtros);
        edObservaciones =findViewById(R.id.edObservacion);

        btEnviar =findViewById(R.id.btInsumoEnviar);
        btEnviar.setOnClickListener(this);
        btRegresa =findViewById(R.id.btInsumoRegresa);
        btRegresa.setOnClickListener(this);

        btFirma = findViewById(R.id.btFirma);
        btFirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFirma dialogFirma = new DialogFirma(InsumosActivity.this);
                dialogFirma.setTitle("Firma tecnico responsable");
                dialogFirma.setListenerDialog(new DialogFirma.ListenerDialog() {
                    @Override
                    public void saveImageOK() {
                        Uri uri = Uri.parse(getFilesDir()+"/"+IMAGE_FIRMA);
                        btFirma.setImageURI(uri);
                        btFirma.setEnabled(false);
                        isFirma = true;
                    }
                });
                dialogFirma.show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if( id ==  R.id.btInsumoEnviar) {
            getDataInsumos();
            if (!verifyData()) {
                Toast.makeText(this, "Por favor verifique datos", Toast.LENGTH_LONG).show();
                return;
            }
            if (!isFirma) {
                Toast.makeText(this, "Por favor asegurese de firmar", Toast.LENGTH_LONG).show();
                return;
            }
            if (UnidosApplication.getDataManteni().getTipoManteni().contains("Correctivo")) {
                generarReporte();
            } else {
                generarReportPreven();
            }

        }
        else if( id == R.id.btInsumoRegresa) {
            onBackPressed();
        }else if(id == R.id.ima1){

            dialog.setNameFhoto(IMAGE_MANTE_1);
            dialog.setOnPhotoCapturedListener(new CameraDialogFragment.OnPhotoCapturedListener() {
                @Override
                public void onPhotoCaptured(File photoFile, String comment) {
                    Uri uri = Uri.parse(photoFile.getAbsolutePath());
                    ima1.setImageURI(uri);
                    comments[0] = comment;
                    isFoto1=true;
                }
            });
            dialog.show(getSupportFragmentManager(), "CameraDialogFragment");
        }
        else if(id == R.id.ima2){
            dialog.setNameFhoto(IMAGE_MANTE_2);
            dialog.setOnPhotoCapturedListener(new CameraDialogFragment.OnPhotoCapturedListener() {
                @Override
                public void onPhotoCaptured(File photoFile, String comment) {
                    Uri uri = Uri.parse(photoFile.getAbsolutePath());
                    ima2.setImageURI(uri);
                    comments[1] = comment;
                    isFoto2 = true;
                }
            });
            dialog.show(getSupportFragmentManager(), "CameraDialogFragment");
        }
        else if(id == R.id.ima3){
            dialog.setNameFhoto(IMAGE_MANTE_3);
            dialog.setOnPhotoCapturedListener(new CameraDialogFragment.OnPhotoCapturedListener() {
                @Override
                public void onPhotoCaptured(File photoFile, String comment) {
                    Uri uri = Uri.parse(photoFile.getAbsolutePath());
                    ima3.setImageURI(uri);
                    comments[2] = comment;
                    isFoto3 = true;
                }
            });
            dialog.show(getSupportFragmentManager(), "CameraDialogFragment");
        }
        else if(id == R.id.ima4){
            dialog.setNameFhoto(IMAGE_MANTE_4);
            dialog.setOnPhotoCapturedListener(new CameraDialogFragment.OnPhotoCapturedListener() {
                @Override
                public void onPhotoCaptured(File photoFile, String comment) {
                    Uri uri = Uri.parse(photoFile.getAbsolutePath());
                    ima4.setImageURI(uri);
                    comments[3] = comment;
                    isFoto4 = true;
                }
            });
            dialog.show(getSupportFragmentManager(), "CameraDialogFragment");
        }else{

        }

    }

    private void getDataInsumos(){
        insumos.setAceite_1(sp1Insumo.getSelectedItem().toString());
        insumos.setCant_aceite_1(ed1Insumo.getText().toString());

        insumos.setAceite_2(sp2Insumo.getSelectedItem().toString());
        insumos.setCant_aceite_2(ed2Insumo.getText().toString());

        insumos.setAceite_3(sp3Insumo.getSelectedItem().toString());
        insumos.setCant_aceite_3(ed3Insumo.getText().toString());

        insumos.setFiltroMotor(sp4Insumo.getSelectedItem().toString());
        insumos.setCant_filtroMotor(ed4Insumo.getText().toString());

        insumos.setFiltroCombuPri(sp5Insumo.getSelectedItem().toString());
        insumos.setCant_filtroCombuPri(ed5Insumo.getText().toString());

        insumos.setFiltroCombuSeg(sp6Insumo.getSelectedItem().toString());
        insumos.setCant_filtroCombuSeg(ed6Insumo.getText().toString());

        insumos.setFiltroServoMotor(sp7Insumo.getSelectedItem().toString());
        insumos.setCant_filtroServoMotor(ed7Insumo.getText().toString());

        insumos.setFiltroHidraPri(sp8Insumo.getSelectedItem().toString());
        insumos.setCant_filtroHidraPri(ed8Insumo.getText().toString());

        insumos.setFiltroHidraSeg(sp9Insumo.getSelectedItem().toString());
        insumos.setCant_filtroHidraSeg(ed9Insumo.getText().toString());

        insumos.setFiltroAC(sp10Insumo.getSelectedItem().toString());
        insumos.setCant_filtroAC(ed10Insumo.getText().toString());

        insumos.setPreFiltro(sp11Insumo.getSelectedItem().toString());
        insumos.setCant_preFiltro(ed11Insumo.getText().toString());

        insumos.setDesincrustante(sp12Insumo.getSelectedItem().toString());
        insumos.setCant_desincrustante(ed12Insumo.getText().toString());

        insumos.setRepuestos(edRepuestos.getText().toString());
        insumos.setOtros(edOtros.getText().toString());
        insumos.setObservaciones(edObservaciones.getText().toString());
    }

    private boolean verifyData(){
        if(!insumos.getAceite_1().isEmpty()){
            if(insumos.getCant_aceite_1().isEmpty())
                return false;
        }
        if(!insumos.getAceite_2().isEmpty()){
            if(insumos.getCant_aceite_2().isEmpty())
                return false;
        }
        if(!insumos.getAceite_3().isEmpty()){
            if(insumos.getCant_aceite_3().isEmpty())
                return false;
        }
        if(!insumos.getFiltroMotor().isEmpty()){
            if(insumos.getCant_filtroMotor().isEmpty())
                return false;
        }
        if(!insumos.getFiltroCombuPri().isEmpty()){
            if(insumos.getCant_filtroCombuPri().isEmpty())
                return false;
        }
        if(!insumos.getFiltroCombuSeg().isEmpty()){
            if(insumos.getCant_filtroCombuSeg().isEmpty())
                return false;
        }
        if(!insumos.getFiltroServoMotor().isEmpty()){
            if(insumos.getCant_filtroServoMotor().isEmpty())
                return false;
        }
        if(!insumos.getFiltroHidraPri().isEmpty()){
            if(insumos.getCant_filtroHidraPri().isEmpty())
                return false;
        }
        if(!insumos.getFiltroHidraSeg().isEmpty()){
            if(insumos.getCant_filtroHidraSeg().isEmpty())
                return false;
        }
        if(!insumos.getFiltroAC().isEmpty()){
            if(insumos.getCant_filtroAC().isEmpty())
                return false;
        }
        if(!insumos.getPreFiltro().isEmpty()){
            if(insumos.getCant_preFiltro().isEmpty())
                return false;
        }
        if(!insumos.getDesincrustante().isEmpty()){
            if(insumos.getCant_desincrustante().isEmpty())
                return false;
        }
        return isFoto1 && isFoto2 && isFoto3 && isFoto4;
    }

    private void generarReporte(){
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReportManteCorrec reportManteCorrec = new ReportManteCorrec(InsumosActivity.this);
                reportManteCorrec.setInsumos(insumos);
                reportManteCorrec.setDataManteni(UnidosApplication.getDataManteni());
                reportManteCorrec.setUser(UnidosApplication.getUser());
                reportManteCorrec.setManteniList(UnidosApplication.getListManteni());
                reportManteCorrec.setEquipos(equiposs);
                result = reportManteCorrec.buildReport(equiposs.getTipo());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if(result)
                            messageOK();
                        else
                            showError();
                    }
                });
            }
        }).start();

    }

    private void generarReportPreven(){
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReportMantePreven reportMantePreven = new ReportMantePreven(InsumosActivity.this);
                reportMantePreven.setInsumos(insumos);
                if(equiposs.getTipo()==REPORT_TYPE_VOLQUETA){
                    reportMantePreven.setDataManteniVolq(UnidosApplication.getDataManteni());
                }else{
                    reportMantePreven.setDataManteniOtros(UnidosApplication.getDataManteni());
                }
                reportMantePreven.setComments(comments[0],comments[1],comments[2],comments[3]);
                reportMantePreven.setUser(UnidosApplication.getUser());
                reportMantePreven.setManteniList(UnidosApplication.getListManteni());
                reportMantePreven.setEquipos(equiposs);
                result = reportMantePreven.buildReport(equiposs.getTipo());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if(result)
                            messageOK();
                        else
                            showError();
                    }
                });
            }
        }).start();

    }

    private void messageOK(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                custumerDialog = new CustumerDialog(InsumosActivity.this,"SUCCESS!", "Reporte guardado",false,false);
                custumerDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        custumerDialog.dismiss();
                        startActivity(new Intent(InsumosActivity.this, MainActivity.class));
                        finish();
                    }
                },4000);
            }
        });
    }

    private void showError(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                custumerDialog = new CustumerDialog(InsumosActivity.this,"FAIL!", "Error Guardando el reporte",true,false);
                custumerDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        custumerDialog.dismiss();
                        startActivity(new Intent(InsumosActivity.this, MainActivity.class));
                        finish();
                    }
                },4000);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/"+equiposs.getFoto());
        File pathImage = new File(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE, equiposs.getFoto());
        if(pathImage.exists() == false)
            uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/fotico.png");

        imaView.setBackground(getResources().getDrawable(R.drawable.shape_image_azul));
        imaView.setImageURI(uri);
    }


}