package com.esilva.equiposunidos.Mantenimiento;

import static com.esilva.equiposunidos.util.Constantes.EQUIPO_TIPO_CARGADOR;
import static com.esilva.equiposunidos.util.Constantes.EQUIPO_TIPO_EXCAVADORA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.esilva.equiposunidos.MainActivity;
import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.application.UnidosApplication;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.DataManteni;
import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TipoManteniActivity extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener {

    private Button btContinuar,btCancelar;
    private TextView tvTecnico, tvDate;
    private Spinner spTypeMante,spEquipo,spLugarMante;
    private AdminBaseDatos adminBaseDatos;

    private String[] mantenimientos;
    private List<String> equipos;
    private List<String> lugares;

    private User userVerify;
    private boolean isHora;
    private List<Equipos> ListEquipo;
    private DataManteni dataManteni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tipo_manteni);
        userVerify = UnidosApplication.getUser();
        dataManteni = new DataManteni();
        adminBaseDatos = new AdminBaseDatos(this);
        setView();
    }

    private void setView() {
        btContinuar = findViewById(R.id.btManteConti);
        btContinuar.setOnClickListener(this);
        btCancelar = findViewById(R.id.btManteCance);
        btCancelar.setOnClickListener(this);
        tvDate = findViewById(R.id.fecha_horaMante);
        tvTecnico = findViewById(R.id.tvTecnicoMante);
        spTypeMante = findViewById(R.id.spTypeMante);
        spEquipo = findViewById(R.id.spEquipo);
        spLugarMante = findViewById(R.id.spLugarMante);


        tvTecnico.setText(userVerify.getNombre());

        //mantenimientos = adminBaseDatos.mante_getAll();
        mantenimientos = getResources().getStringArray(R.array.tipo_mantenimiento);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mantenimientos);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTypeMante.setAdapter(adapter1);
        spTypeMante.setOnItemSelectedListener(this);

        ListEquipo = adminBaseDatos.equi_getAll();
        equipos = new ArrayList<String>();
        for (Equipos stt:ListEquipo) {
            if(stt.getTipo() == EQUIPO_TIPO_CARGADOR || stt.getTipo() == EQUIPO_TIPO_EXCAVADORA)
                equipos.add(stt.getNombre());
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipos);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEquipo.setAdapter(adapter2);
        spEquipo.setOnItemSelectedListener(this);

        lugares = adminBaseDatos.luga_getAll();
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lugares);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLugarMante.setAdapter(adapter3);
        spLugarMante.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btManteConti:
                UnidosApplication.setDataManteni(dataManteni);
                startActivity(new Intent(this,ManteCaracterisActivity.class));
                break;
            case R.id.btManteCance:
                startActivity(new Intent(this, MainActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.spTypeMante:
                dataManteni.setTipoManteni(mantenimientos[i]);
                break;
            case R.id.spEquipo:
                for (Equipos eq:ListEquipo) {
                    if(eq.getNombre().equals(equipos.get(i)))
                        UnidosApplication.setEquipo(eq);
                }
                break;
            case R.id.spLugarMante:
                dataManteni.setLugarMante(lugares.get(i));
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onResume() {
        super.onResume();
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