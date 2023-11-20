package com.esilva.equiposunidos.RegistroInOut;

import static com.esilva.equiposunidos.util.Constantes.FILE_IMAGE;
import static com.esilva.equiposunidos.util.Constantes.VALUE_INTENT_BOOLEAN;
import static com.esilva.equiposunidos.util.Constantes.VALUE_INTENT_TEXT;
import static com.esilva.equiposunidos.util.Util.getFechaHora;
import static com.esilva.equiposunidos.util.Util.getFechaHoraCom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.esilva.equiposunidos.Dialog.CustumerDialog;
import com.esilva.equiposunidos.EnrolaInitActivity;
import com.esilva.equiposunidos.MainActivity;
import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.application.UnidosApplication;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.Registros;
import com.esilva.equiposunidos.db.models.User;
import com.esilva.equiposunidos.util.Util;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FormRegisterInOutActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvName,tvDocument,tvPositions,tvDateHours,tvTitle;
    private Spinner spMaquina,spAcatividad;
    private EditText edComment;
    private Button btEnviarForm,btRegresarForm;
    private ImageView imImageForm;

    private User userVerify;
    private boolean isHora;

    private List<String> machine,activities;
    private AdminBaseDatos adminBaseDatos;

    private boolean isEntrada;
    private String textTitle;

    private double latitude;
    private double longitude;

    private CustumerDialog custumerDialog;
    private  Registros regisLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_form_register_in_out);
        getCoordenadas();
        Intent intent = getIntent();
        isEntrada = intent.getBooleanExtra(VALUE_INTENT_BOOLEAN,false);
        textTitle = intent.getStringExtra(VALUE_INTENT_TEXT);
        userVerify = UnidosApplication.getUser();
        adminBaseDatos = new AdminBaseDatos(this);
        List<Equipos> equipos = adminBaseDatos.equi_getAll();
        machine = new ArrayList<String>();
        for (Equipos eq:equipos) {
            machine.add(eq.getEquipo());
        }
        activities = adminBaseDatos.act_getAll();
        setView();
    }

    private void setView() {
        tvName = findViewById(R.id.tvNameForm);
        tvDocument = findViewById(R.id.tvDocumentForm);
        tvPositions = findViewById(R.id.tvCargoForm);
        tvDateHours = findViewById(R.id.fecha_horaForm);
        tvTitle = findViewById(R.id.tvTitleForm);
        tvTitle.setText(textTitle);

        spMaquina = findViewById(R.id.spMachineForm);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, machine);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaquina.setAdapter(adapter);
        spMaquina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //setViewDataUser(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spAcatividad = findViewById(R.id.spActivityForm);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, activities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAcatividad.setAdapter(adapter2);
        spAcatividad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //setViewDataUser(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edComment = findViewById(R.id.edCommentForm);

        imImageForm = findViewById(R.id.imImageForm);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/"+userVerify.getFoto());
        File pathImage = new File(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE, userVerify.getFoto());
        if(pathImage.exists() == false)
            uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/fotico.png");

        imImageForm.setBackground(getResources().getDrawable(R.drawable.shape_image_azul));
        imImageForm.setImageURI(uri);

        btEnviarForm = findViewById(R.id.btFormEnviar);
        btEnviarForm.setOnClickListener(this);
        btRegresarForm = findViewById(R.id.btFormCancelar);
        btRegresarForm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btFormEnviar:
                btEnviarForm.setEnabled(false);
                guardarRegistro();
                break;
            case R.id.btFormCancelar:
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
                break;
            default:
                break;
        }
    }

    private void guardarRegistro(){
        regisLast = adminBaseDatos.reg_getAllLastByUser(userVerify.getCedula());
        if(isEntrada){      // es entrada?
            if(regisLast == null) {  // no tiene registros
                registra();  // OK
                return;
            }
            if(Util.getCompareDate(regisLast.getFecha_in(), getFechaHora())){ // ya tiene registro de entrada del dia
                showError(3);
                return;
            }
            if(regisLast.getFecha_out()!=null) { // tiene registro de salida
                registra(); // OK
                return;
            }else{    // NO tiene registro de salida
                long dif = Util.getTimeDifference(regisLast.getFecha_in());
                if(dif >= 12){         // diferencia de horas es mayo a 13
                    updateLastRegisterAuto(regisLast,"Registro de salida automatico");
                    registra();
                }else{              // diferencia de horas es menor a 13
                    showError(0); // DEBE REGISTRAR SALIDA
                }
            }
        }else{  // ES SALIDA
            if(regisLast != null){
                if(regisLast.getFecha_out()!=null) {  // salida ya registrada
                    showError(5);
                    return;
                }

                if(regisLast.getFecha_in()!=null) { // tiene ingreso
                    long dif = Util.getTimeDifference(regisLast.getFecha_in());
                    if(dif >= 12){
                        updateLastRegister(regisLast,"Se registra salida con tiempo de trabajo = "+String.valueOf(dif)); /* TODO se registra NOVEDAD MAYOR DE TIEMPO */
                    }else{
                        updateLastRegister(regisLast,null);
                    }
                }else{                              // NO tiene ingreso
                    showError(1);
                }
            }else{
                showError(2);
            }
        }
    }

    private void updateLastRegisterAuto(Registros reg,String novedad){
        reg.setFecha_out(getFechaHora());
        reg.setComentario_out(novedad);
        reg.setActividad_out(reg.getActividad_in());
        reg.setEquipo_out(reg.getEquipo_in());
        reg.setLongitud_out(String.valueOf(longitude));
        reg.setLatitud_out(String.valueOf(latitude));
        Boolean ret = adminBaseDatos.reg_update(reg);
        if(ret)
            Log.d("DP_DLOG","updateLastRegisterAuto "+"ok");
        else
            showError(4);
    }

    private void updateLastRegister(Registros reg,String novedad){

        reg.setFecha_out(getFechaHora());
        String comment;
        comment = edComment.getText().toString();
        if(novedad != null)
            comment+= " |"+novedad;
        reg.setComentario_out(comment);
        reg.setActividad_out(spAcatividad.getSelectedItem().toString());
        reg.setEquipo_out(spMaquina.getSelectedItem().toString());
        reg.setLongitud_out(String.valueOf(longitude));
        reg.setLatitud_out(String.valueOf(latitude));
        Boolean ret = adminBaseDatos.reg_update(reg);
        if(ret)
            messageOK();
        else
            showError(4);
    }

    private void registra(){
        Registros register = new Registros();
        register.setCedula(userVerify.getCedula());
        String[] fechaHora = getFechaHoraCom();
        register.setDia(fechaHora[1]);
        register.setDia_mes(Integer.valueOf(fechaHora[2]));
        register.setFecha_in(fechaHora[0]);
        register.setEquipo_in(spMaquina.getSelectedItem().toString());
        register.setActividad_in(spAcatividad.getSelectedItem().toString());
        register.setComentario_in(edComment.getText().toString());
        register.setLatitud_in(String.valueOf(latitude));
        register.setLongitud_in(String.valueOf(longitude));

        /*int idDIA;
        if(regisLast == null || regisLast.getDia_mes()>1)
            idDIA = 0;
        else
            idDIA = regisLast.getDia_mes();
        rellenaRegistros(fechaHora[0],idDIA,register.getDia_mes());*/

        long ret = adminBaseDatos.reg_insert(register);
        if(ret > 0)
            messageOK();
        else
            showError(4);

    }

    private void rellenaRegistros(String fecha,int iniDia,int finDia) {
        // yyyy-MM-dd HH:mm:ss
        String fechaSplit = fecha.replace(' ','-');
        String[] temp = fechaSplit.split("-",-1);
        for(int i=iniDia+1;i<finDia;i++){
            Registros register = new Registros();
            register.setDia("--");
            register.setCedula(userVerify.getCedula());

            register.setActividad_in("---");
            register.setActividad_out("---");
            register.setEquipo_in("---");
            register.setEquipo_out("---");
            register.setComentario_in("---");
            register.setComentario_out("---");
            register.setLongitud_in("0.0");
            register.setLongitud_out("0.0");
            register.setLatitud_in("0.0");
            register.setLatitud_out("0.0");
            register.setDia_mes(i);

            String tfe = temp[0]+"-"+temp[1]+"-"+String.format("%02d", i);
            register.setFecha_in(tfe+" 00:00:00");
            register.setFecha_out(tfe+" 00:00:00");

            adminBaseDatos.reg_insertAll(register);
        }
    }

    private void messageOK(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                custumerDialog = new CustumerDialog(FormRegisterInOutActivity.this,"SUCCESS!", "Registro guardado",false,false);
                custumerDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        custumerDialog.dismiss();
                        startActivity(new Intent(FormRegisterInOutActivity.this, MainActivity.class));
                        finish();
                    }
                },4000);
            }
        });
    }

    private void showError(final int error){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg;
                if(error == 0)
                    msg = "Debe registrar salida";
                else if(error == 1)
                    msg = "NO tiene ingreso";
                else if(error == 2)
                    msg = "NO tiene registros el usuarios";
                else if(error == 3)
                    msg = "Ya tiene registro de entrada en este dia";
                else if(error == 4)
                    msg = "ERROR guardando en la base de datos";
                else if(error == 5)
                    msg = "Salida YA registrada";
                else
                    msg = "error de seleccion";
                custumerDialog = new CustumerDialog(FormRegisterInOutActivity.this,"FAIL!", msg,true,false);
                custumerDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        custumerDialog.dismiss();
                        startActivity(new Intent(FormRegisterInOutActivity.this, MainActivity.class));
                        finish();
                    }
                },4000);
            }
        });
    }

    private void getCoordenadas(){
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Solicita permisos si aún no se han otorgado
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(FormRegisterInOutActivity.this, location -> {
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.d("DP_DLOG","getCoordenadas "+"latitud "+latitude+" longitud "+longitude);
                // Usa las coordenadas como desees
            }
        });

        return;
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvName.setText(userVerify.getNombre());
        tvDocument.setText(String.valueOf(userVerify.getCedula()));
        tvPositions.setText(userVerify.getCargo());
        isHora = true;
        ShowHours();

    }

    @Override
    protected void onPause() {
        super.onPause();
        isHora = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void ShowHours(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isHora){
                    showDate(getCurrentDate());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private String getCurrentDate(){
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
                tvDateHours.setText(date);
            }
        });
    }

    @Override
    protected void onDestroy() {
        adminBaseDatos.closeBaseDtos();
        super.onDestroy();
    }
}