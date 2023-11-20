package com.esilva.equiposunidos.Inventario;

import static com.esilva.equiposunidos.util.Constantes.VALUE_INTENT_BOOLEAN;
import static com.esilva.equiposunidos.util.Constantes.VALUE_INTENT_TEXT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.RegistroInOut.FormRegisterInOutActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MenuInventarioActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView btIngresoInv,btSalidaInv,btReportInv;
    private Button btRegrasr;
    private TextView fechaIn;
    private Boolean isHora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_inventario);

        setView();
    }

    private void setView() {
        btIngresoInv = findViewById(R.id.btIngresosInv);
        btIngresoInv.setOnClickListener(this);
        btSalidaInv = findViewById(R.id.btSalidasInv);
        btSalidaInv.setOnClickListener(this);
        btReportInv = findViewById(R.id.btInvenReport);
        btReportInv.setOnClickListener(this);
        btRegrasr = findViewById(R.id.btInventariRegreso);
        btRegrasr.setOnClickListener(this);
        fechaIn = findViewById(R.id.fecha_horaInven);

    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btIngresosInv:
                intent = new Intent(this, FormInventarioActivity.class);
                startActivity(intent);
                break;
            case R.id.btSalidasInv:
                //intent = new Intent(this,FormRegisterInOutActivity.class);
                //startActivity(intent);
                break;
            case R.id.btInvenReport:

                break;
            case R.id.btInventariRegreso:
                onBackPressed();
                break;
            default:
                break;
        }
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
                fechaIn.setText(date);
            }
        });
    }


}