package com.esilva.equiposunidos.InspeccionPreoperacional;

import static com.esilva.equiposunidos.util.Constantes.EQUIPO_TIPO_CARGADOR;
import static com.esilva.equiposunidos.util.Constantes.EQUIPO_TIPO_EXCAVADORA;
import static com.esilva.equiposunidos.util.Constantes.EQUIPO_TIPO_OTRO;
import static com.esilva.equiposunidos.util.Constantes.EQUIPO_TIPO_VOLQUETA;
import static com.esilva.equiposunidos.util.Constantes.FILE_IMAGE;
import static com.esilva.equiposunidos.util.Constantes.IMAGE_FIRMA_SUPER;
import static com.esilva.equiposunidos.util.Constantes.IMAGE_FIRMA_TEC;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esilva.equiposunidos.Dialog.CustumerDialog;
import com.esilva.equiposunidos.Dialog.DialogFirma;
import com.esilva.equiposunidos.Dialog.ProgressDialog;
import com.esilva.equiposunidos.MainActivity;
import com.esilva.equiposunidos.Mantenimiento.InsumosActivity;
import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.Report.ReportInspeccion;
import com.esilva.equiposunidos.application.UnidosApplication;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.User;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InpeccDataActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView,btTecnico,btSupervisor;
    private TextView tvTitle,tvOper,tvFecha,edCedula;
    private EditText edObservaciones,edHorome;
    private Button btGuardar,btRegresar,btHome;
    private LinearLayout liCedula;
    private Spinner spLugar,spSuper;
    private TextView tvOperador,tvCedulaSuper,tvHorometro;

    private Equipos equipos;
    private String stOperador,stCedula,stCedulaSuper,stSupervisor,stHorometro,stLugar,stObservacion;

    private ProgressDialog progressDialog;
    private CustumerDialog custumerDialog;
    private boolean isHora;
    private  List<String> lugares;
    private  List<String> tecnicos;
    private  List<User> tecnicosUser;
    private boolean isFirmaTec = false;
    private boolean isFirmaSuper = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inpecc_data);

        equipos = UnidosApplication.getEquipo();
        progressDialog = new ProgressDialog(this,"Generando Reporte");
        AdminBaseDatos adminBaseDatos = new AdminBaseDatos(this);
        lugares = adminBaseDatos.luga_getAll();
        tecnicosUser = adminBaseDatos.user_getSupervisores();
        tecnicos = new ArrayList<String>();
        for (User user : tecnicosUser) {
            tecnicos.add(user.getNombre());
        }
        adminBaseDatos.closeBaseDtos();

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
        tvHorometro = findViewById(R.id.tvHorometro);

        if(equipos.getTipo() == EQUIPO_TIPO_CARGADOR)
            tvTitle.setText("INSPECCION PREOPERACIONAL\nCARGADOR");
        else if(equipos.getTipo() == EQUIPO_TIPO_EXCAVADORA)
            tvTitle.setText("INSPECCION PREOPERACIONAL\nEXCAVADORA");
        else if(equipos.getTipo() == EQUIPO_TIPO_VOLQUETA)
            tvTitle.setText("INSPECCION PREOPERACIONAL\nVOLQUETA");
        else {
            tvTitle.setText("INSPECCION PREOPERACIONAL\nVEHICULO");
            tvOper.setText("Nombre del conductor:");
            tvHorometro.setText("Kilometraje:");
        }

        tvOper = findViewById(R.id.tvDataInOper);
        tvOperador = findViewById(R.id.spDataInOper);

        stOperador = UnidosApplication.getUser().getNombre();
        stCedula = String.valueOf(UnidosApplication.getUser().getCedula());
        tvOperador.setText(stOperador);

        edCedula = findViewById(R.id.edDataInCedula);
        edCedula.setText(stCedula);

        spSuper = findViewById(R.id.spDataInSuper);
        tvCedulaSuper = findViewById(R.id.tvCedulaSuper);
        stCedulaSuper = String.valueOf(tecnicosUser.get(0).getCedula());
        tvCedulaSuper.setText(stCedulaSuper);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tecnicos);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSuper.setAdapter(adapter1);
        spSuper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stSupervisor = tecnicos.get(i);
                stCedulaSuper = String.valueOf(tecnicosUser.get(i).getCedula());
                tvCedulaSuper.setText(stCedulaSuper);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        edObservaciones = findViewById(R.id.edDataInObserva);
        edHorome = findViewById(R.id.edDataInHorom);
        spLugar = findViewById(R.id.spDataInLugar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lugares);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLugar.setAdapter(adapter);
        spLugar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stLugar = lugares.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btGuardar = findViewById(R.id.btContInGuardar);
        btGuardar.setOnClickListener(this);

        btRegresar = findViewById(R.id.btContInRegresa);
        btRegresar.setOnClickListener(this);

        btHome = findViewById(R.id.btContInHome);
        btHome.setOnClickListener(this);

        btTecnico = findViewById(R.id.btTecnico);
        btTecnico.setOnClickListener(this);

        btSupervisor = findViewById(R.id.btSupervisor);
        btSupervisor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btContInGuardar) {
            if (validarData()) {
                btGuardar.setEnabled(false);
                btRegresar.setEnabled(false);
                btHome.setEnabled(false);
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ReportInspeccion reportInspeccion = new ReportInspeccion(InpeccDataActivity.this);
                        reportInspeccion.setCedula(stCedula);
                        reportInspeccion.setCedulaSuper(stCedulaSuper);
                        reportInspeccion.setHorometro(stHorometro);
                        reportInspeccion.setLugar(stLugar);
                        reportInspeccion.setObservacion(stObservacion);
                        reportInspeccion.setSupervisor(stSupervisor);
                        reportInspeccion.setOperador(stOperador);
                        reportInspeccion.setFirmaTecnico(isFirmaTec);
                        reportInspeccion.setFirmaSuper(isFirmaSuper);
                        boolean b = reportInspeccion.buildReport();
                        showResult(b);
                    }
                }).start();
            } else {
                Toast.makeText(this, "Por favor diligencie todos los datos", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.btContInRegresa) {
            btGuardar.setEnabled(false);
            btRegresar.setEnabled(false);
            btHome.setEnabled(false);
            onBackPressed();
        }
        else if (id == R.id.btContInHome) {
            btGuardar.setEnabled(false);
            btRegresar.setEnabled(false);
            btHome.setEnabled(false);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else if (id == R.id.btTecnico) {
            DialogFirma dialogFirma = new DialogFirma(InpeccDataActivity.this);
            dialogFirma.setNameFile(IMAGE_FIRMA_TEC);
            dialogFirma.setListenerDialog(new DialogFirma.ListenerDialog() {
                @Override
                public void saveImageOK() {
                    Uri uri = Uri.parse(getFilesDir()+"/"+IMAGE_FIRMA_TEC);
                    btTecnico.setImageURI(uri);
                    btTecnico.setEnabled(false);
                    isFirmaTec = true;
                }
            });
            dialogFirma.show();
        }
        else if (id == R.id.btSupervisor) {
            DialogFirma dialogFirma = new DialogFirma(InpeccDataActivity.this);
            dialogFirma.setNameFile(IMAGE_FIRMA_SUPER);
            dialogFirma.setListenerDialog(new DialogFirma.ListenerDialog() {
                @Override
                public void saveImageOK() {
                    Uri uri = Uri.parse(getFilesDir()+"/"+IMAGE_FIRMA_SUPER);
                    btSupervisor.setImageURI(uri);
                    btSupervisor.setEnabled(false);
                    isFirmaSuper = true;
                }
            });
            dialogFirma.show();
        }
    }

    private boolean validarData() {
        stObservacion = edObservaciones.getText().toString();
        if(!isFirmaTec) {
            return false;
        }
        stHorometro = edHorome.getText().toString();
        return !stHorometro.isEmpty();
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