package com.esilva.equiposunidos.Mantenimiento;

import static com.esilva.equiposunidos.util.Constantes.FILE_IMAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.esilva.equiposunidos.Dialog.CustumerDialog;
import com.esilva.equiposunidos.Dialog.DialogFirma;
import com.esilva.equiposunidos.Dialog.ProgressDialog;
import com.esilva.equiposunidos.MainActivity;
import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.RegistroInOut.FormRegisterInOutActivity;
import com.esilva.equiposunidos.Report.ReportManteCorrec;
import com.esilva.equiposunidos.Report.ReportMantePreven;
import com.esilva.equiposunidos.application.UnidosApplication;
import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.Insumos;

import java.io.File;

public class InsumosActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner sp1Insumo,sp2Insumo,sp3Insumo,sp4Insumo,sp5Insumo,sp6Insumo,sp7Insumo,sp8Insumo,sp9Insumo,sp10Insumo,sp11Insumo,sp12Insumo;
    private EditText ed1Insumo,ed2Insumo,ed3Insumo,ed4Insumo,ed5Insumo,ed6Insumo,ed7Insumo,ed8Insumo,ed9Insumo,ed10Insumo,ed11Insumo,ed12Insumo;
    private EditText edRepuestos,edOtros,edObservaciones;
    private ImageView btFirma,imaView;
    private Button btEnviar,btRegresa;

    private Insumos insumos;
    private boolean isFirma=false;
    private Equipos equiposs;

    private ProgressDialog progressDialog;
    private CustumerDialog custumerDialog;
    private boolean result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insumos);
        setView();
        equiposs = UnidosApplication.getEquipo();
        progressDialog = new ProgressDialog(this,"Generando Reporte");
        insumos = new Insumos();
    }

    private void setView() {
        imaView = findViewById(R.id.imImageEquipoIns);


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
                dialogFirma.setListenerDialog(new DialogFirma.ListenerDialog() {
                    @Override
                    public void saveImageOK() {
                        Uri uri = Uri.parse(getFilesDir()+"/firmaMante.png");
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
        switch (view.getId()){
            case R.id.btInsumoEnviar:
                getDataInsumos();
                if(!verifyData()){
                    Toast.makeText(this,"Por favor verifique datos",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!isFirma){
                    Toast.makeText(this,"Por favor asegurese de firmar",Toast.LENGTH_LONG).show();
                    return;
                }
                if(UnidosApplication.getDataManteni().getTipoManteni().contains("Correctivo")) {
                    generarReporte();
                }else{
                    generarReportPreven();
                }

                break;
            case R.id.btInsumoRegresa:
                onBackPressed();
                break;
            default:
                break;
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

        return true;
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
                reportMantePreven.setDataManteni(UnidosApplication.getDataManteni());
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