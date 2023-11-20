package com.esilva.equiposunidos.Mantenimiento;

import static com.esilva.equiposunidos.util.Constantes.FILE_IMAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.application.UnidosApplication;
import com.esilva.equiposunidos.db.models.DataManteni;
import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.User;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ManteCaracterisActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btContinuar,btRegresar;
    private TextView tvDate,tvFechaHora,tvTipoMante,tvNumRegistro,tvEquipo,tvNumMotor,tvSerieMaq,tvPropietari,tvTecnico;
    private ImageView imManteni;
    private CheckBox cheBloqueo,cheATS,cheDerrame;
    private EditText edHorometro,edHorometroProx,edConsecutivo;

    private boolean isHora;
    private Equipos equipoSelect;
    private DataManteni dataManteni;
    private User user;
    private String stHorometro,stHorometroProx,stConsecutivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mante_caracteris);
        equipoSelect = UnidosApplication.getEquipo();
        dataManteni = UnidosApplication.getDataManteni();
        user = UnidosApplication.getUser();

        setView();
    }

    private void setView() {
        tvFechaHora = findViewById(R.id.tv1Date);
        tvTipoMante = findViewById(R.id.tv2TypeMante);
        tvNumRegistro = findViewById(R.id.tv3Register);
        tvEquipo = findViewById(R.id.tv4Equipo);
        tvNumMotor = findViewById(R.id.tv5Motor);
        tvSerieMaq = findViewById(R.id.tv6Serie);
        tvPropietari = findViewById(R.id.tv9Propietario);
        tvTecnico = findViewById(R.id.tv11Tecnico);

        cheBloqueo = findViewById(R.id.che12Bloqueo);
        cheATS = findViewById(R.id.che13ATS);
        cheDerrame = findViewById(R.id.che14Derrames);

        edHorometro = findViewById(R.id.ed7Horome);
        edHorometroProx = findViewById(R.id.ed8HoromeMnto);
        edConsecutivo = findViewById(R.id.ed10Consecutivo);

        btContinuar = findViewById(R.id.btManteContiCarac);
        btContinuar.setOnClickListener(this);
        btRegresar = findViewById(R.id.btManteRegresa);
        btRegresar.setOnClickListener(this);
        tvDate = findViewById(R.id.fecha_horaCaract);

        imManteni = findViewById(R.id.imImageMnate);

        dataManteni.setFechaHora(getDate());

        tvFechaHora.setText(dataManteni.getFechaHora());
        tvTipoMante.setText(dataManteni.getTipoManteni());
        tvNumRegistro.setText(equipoSelect.getNumeroRegistro());
        tvEquipo.setText(equipoSelect.getEquipo());
        tvNumMotor.setText(equipoSelect.getNumeroMotor());
        tvSerieMaq.setText(equipoSelect.getNumeroSerie());
        tvPropietari.setText(equipoSelect.getPropietario());
        tvTecnico.setText(user.getNombre());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btManteContiCarac:
                if(verifyData()) {
                    dataManteni.setATS(true);
                    dataManteni.setKitDerrames(true);
                    dataManteni.setBloqueoEtiquetado(true);
                    dataManteni.setConsecutivo(stConsecutivo);
                    dataManteni.setHorometro(stHorometro);
                    dataManteni.setHorometroProx(stHorometroProx);
                    UnidosApplication.setDataManteni(dataManteni);
                    if(dataManteni.getTipoManteni().contains("Correctivo"))
                        startActivity(new Intent(this,ActiCorrectivoActivity.class));
                    else
                        startActivity(new Intent(this,ActiPreventivaActivity.class));
                }
                break;
            case R.id.btManteRegresa:
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

    private boolean verifyData(){
        stHorometro = edHorometro.getText().toString().trim();
        stHorometroProx = edHorometroProx.getText().toString().trim();
        stConsecutivo = edConsecutivo.getText().toString().trim();
        if(stConsecutivo.isEmpty() || stHorometroProx.isEmpty() || stHorometro.isEmpty()) {
            Toast.makeText(ManteCaracterisActivity.this,"Por favor complete la informacion",Toast.LENGTH_LONG).show();
            return false;
        }

        if(!cheBloqueo.isChecked() || !cheATS.isChecked() || !cheDerrame.isChecked()) {
            Toast.makeText(ManteCaracterisActivity.this,"Por favor complete la informacion de seguridad",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/"+equipoSelect.getFoto());
        File pathImage = new File(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE, equipoSelect.getFoto());
        if(pathImage.exists() == false)
            uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/fotico.png");

        imManteni.setBackground(getResources().getDrawable(R.drawable.shape_image_azul));
        imManteni.setImageURI(uri);
        isHora = true;
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
                    showDate(getDate());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private String getDate(){
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
                tvDate.setText(date);
            }
        });
    }
}