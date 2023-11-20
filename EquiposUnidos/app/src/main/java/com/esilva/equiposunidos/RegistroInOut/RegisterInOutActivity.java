package com.esilva.equiposunidos.RegistroInOut;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterInOutActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView btIngreso,btSalida;
    private Button btRegreso;
    private TextView tvDateHour;

    private Boolean isHora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_in_out);
        setView();

    }

    private void setView() {
        btIngreso = findViewById(R.id.btRegistroIn);
        btIngreso.setOnClickListener(this);
        btSalida = findViewById(R.id.btRegistroOut);
        btSalida.setOnClickListener(this);
        btRegreso = findViewById(R.id.btRegistroRegreso);
        btRegreso.setOnClickListener(this);
        tvDateHour = findViewById(R.id.fecha_horain);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btRegistroIn:
                intent = new Intent(this,FormRegisterInOutActivity.class);
                intent.putExtra(VALUE_INTENT_TEXT,"REGISTRO DE ENTRADA");
                intent.putExtra(VALUE_INTENT_BOOLEAN,true);
                startActivity(intent);
                break;
            case R.id.btRegistroOut:
                intent = new Intent(this,FormRegisterInOutActivity.class);
                intent.putExtra(VALUE_INTENT_TEXT,"REGISTRO DE SALIDA");
                intent.putExtra(VALUE_INTENT_BOOLEAN,false);
                startActivity(intent);
                break;
            case R.id.btRegistroRegreso:
                isHora =false;
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
                tvDateHour.setText(date);
            }
        });
    }
}