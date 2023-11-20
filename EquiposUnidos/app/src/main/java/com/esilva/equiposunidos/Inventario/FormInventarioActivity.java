package com.esilva.equiposunidos.Inventario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.application.UnidosApplication;

public class FormInventarioActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvTitleInven;
    private TextView tvDescrp1,tvDescrp2,tvDescrp3,tvDescrp4,tvDescrp5,tvDescrp6,tvDescrp7,tvDescrp8,tvDescrp9,tvDescrp10;
    private TextView tvCod1,tvCod2,tvCod3,tvCod4,tvCod5,tvCod6,tvCod7,tvCod8,tvCod9,tvCod10;
    private EditText edValor1,edValor2,edValor3,edValor4,edValor5,edValor6,edValor7,edValor8,edValor9,edValor10;
    private ImageView imaIventario;
    private LinearLayout liUltimo;
    private Button btContiInven,btRegresarInven;

    private String[] stTitles;

    private String[] DesCartepila;
    private String[] CodCaterpila;

    private String[] DesDoorsan;
    private String[] CodDoorsan;

    private String[] DesKomatsu1;
    private String[] CodKomatsu1;

    private String[] DesKomatsu2;
    private String[] CodKomatsu2;

    private String[] DesAceites;
    private String[] CodAceites;

    private String[] stCaterpila;
    private String[] stDoorsan;
    private String[] stKomatsu1;
    private String[] stKomatsu2;
    private String[] stAceites;

    private int numFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_form_inventario);

        setView();
        getDatos();

        viewCaterpila();
    }

    private void getDatos() {
        stTitles = getResources().getStringArray(R.array.title_inventario);

        DesCartepila = getResources().getStringArray(R.array.des_caterpila);
        CodCaterpila = getResources().getStringArray(R.array.cod_caterpila);

        DesDoorsan = getResources().getStringArray(R.array.des_doorsan);
        CodDoorsan = getResources().getStringArray(R.array.cod_doorsan);

        DesKomatsu1 = getResources().getStringArray(R.array.des_komatsu1);
        CodKomatsu1 = getResources().getStringArray(R.array.cod_komatsu1);

        DesKomatsu2 = getResources().getStringArray(R.array.des_komatsu2);
        CodKomatsu2 = getResources().getStringArray(R.array.cod_komatsu2);

        DesAceites = getResources().getStringArray(R.array.aceites_mecelanios);
        CodAceites = getResources().getStringArray(R.array.cod_aceites);

        stCaterpila = new String[10];
        stDoorsan = new String[10];
        stKomatsu1 = new String[10];
        stKomatsu2 = new String[10];
        stAceites = new String[10];
    }

    private void setView() {
        btContiInven = findViewById(R.id.btInvenConti);
        btContiInven.setOnClickListener(this);
        btRegresarInven = findViewById(R.id.btInvenRegreso);
        btRegresarInven.setOnClickListener(this);

        tvTitleInven = findViewById(R.id.tvTitleInvetario);
        liUltimo = findViewById(R.id.liUltimo);
        imaIventario = findViewById(R.id.imaInventario);

        viewDescrip();
        viewCodigo();
        viewCantidad();

        numFragment=1;

    }

    private void viewCantidad() {
        tvDescrp1 = findViewById(R.id.tvDescrip1);
        tvDescrp2 = findViewById(R.id.tvDescrip2);
        tvDescrp3 = findViewById(R.id.tvDescrip3);
        tvDescrp4 = findViewById(R.id.tvDescrip4);
        tvDescrp5 = findViewById(R.id.tvDescrip5);
        tvDescrp6 = findViewById(R.id.tvDescrip6);
        tvDescrp7 = findViewById(R.id.tvDescrip7);
        tvDescrp8 = findViewById(R.id.tvDescrip8);
        tvDescrp9 = findViewById(R.id.tvDescrip9);
        tvDescrp10 = findViewById(R.id.tvDescrip10);
    }

    private void viewCodigo() {
        tvCod1 = findViewById(R.id.tvCodigo1);
        tvCod2 = findViewById(R.id.tvCodigo2);
        tvCod3 = findViewById(R.id.tvCodigo3);
        tvCod4 = findViewById(R.id.tvCodigo4);
        tvCod5 = findViewById(R.id.tvCodigo5);
        tvCod6 = findViewById(R.id.tvCodigo6);
        tvCod7 = findViewById(R.id.tvCodigo7);
        tvCod8 = findViewById(R.id.tvCodigo8);
        tvCod9 = findViewById(R.id.tvCodigo9);
        tvCod10 = findViewById(R.id.tvCodigo10);
    }

    private void viewDescrip() {
        edValor1 = findViewById(R.id.edInventario1);
        edValor2 = findViewById(R.id.edInventario2);
        edValor3 = findViewById(R.id.edInventario3);
        edValor4 = findViewById(R.id.edInventario4);
        edValor5 = findViewById(R.id.edInventario5);
        edValor6 = findViewById(R.id.edInventario6);
        edValor7 = findViewById(R.id.edInventario7);
        edValor8 = findViewById(R.id.edInventario8);
        edValor9 = findViewById(R.id.edInventario9);
        edValor10 = findViewById(R.id.edInventario10);
    }


    private void viewCaterpila(){
        tvTitleInven.setText(stTitles[0]);

        tvDescrp1.setText(DesCartepila[0]);
        tvDescrp2.setText(DesCartepila[1]);
        tvDescrp3.setText(DesCartepila[2]);
        tvDescrp4.setText(DesCartepila[3]);
        tvDescrp5.setText(DesCartepila[4]);
        tvDescrp6.setText(DesCartepila[5]);
        tvDescrp7.setText(DesCartepila[6]);
        tvDescrp8.setText(DesCartepila[7]);
        tvDescrp9.setText(DesCartepila[8]);
        tvDescrp10.setText(DesCartepila[9]);

        tvCod1.setText(CodCaterpila[0]);
        tvCod2.setText(CodCaterpila[1]);
        tvCod3.setText(CodCaterpila[2]);
        tvCod4.setText(CodCaterpila[3]);
        tvCod5.setText(CodCaterpila[4]);
        tvCod6.setText(CodCaterpila[5]);
        tvCod7.setText(CodCaterpila[6]);
        tvCod8.setText(CodCaterpila[7]);
        tvCod9.setText(CodCaterpila[8]);
        tvCod10.setText(CodCaterpila[9]);

        edValor1.setText(stCaterpila[0]);
        edValor2.setText(stCaterpila[1]);
        edValor3.setText(stCaterpila[2]);
        edValor4.setText(stCaterpila[3]);
        edValor5.setText(stCaterpila[4]);
        edValor6.setText(stCaterpila[5]);
        edValor7.setText(stCaterpila[6]);
        edValor8.setText(stCaterpila[7]);
        edValor9.setText(stCaterpila[8]);
        edValor10.setText(stCaterpila[9]);

        liUltimo.setVisibility(View.VISIBLE);

        imaIventario.setImageResource(R.drawable.filtro_caterpila);
    }
    private void getDtaCaterpila(){
        stCaterpila[0] = edValor1.getText().toString();
        stCaterpila[1] = edValor2.getText().toString();
        stCaterpila[2] = edValor3.getText().toString();
        stCaterpila[3] = edValor4.getText().toString();
        stCaterpila[4] = edValor5.getText().toString();
        stCaterpila[5] = edValor6.getText().toString();
        stCaterpila[6] = edValor7.getText().toString();
        stCaterpila[7] = edValor8.getText().toString();
        stCaterpila[8] = edValor9.getText().toString();
        stCaterpila[9] = edValor10.getText().toString();
    }

    private void viewDoorsan(){
        tvTitleInven.setText(stTitles[1]);

        tvDescrp1.setText(DesDoorsan[0]);
        tvDescrp2.setText(DesDoorsan[1]);
        tvDescrp3.setText(DesDoorsan[2]);
        tvDescrp4.setText(DesDoorsan[3]);
        tvDescrp5.setText(DesDoorsan[4]);
        tvDescrp6.setText(DesDoorsan[5]);
        tvDescrp7.setText(DesDoorsan[6]);
        tvDescrp8.setText(DesDoorsan[7]);
        tvDescrp9.setText(DesDoorsan[8]);
        tvDescrp10.setText(DesDoorsan[9]);

        tvCod1.setText(CodDoorsan[0]);
        tvCod2.setText(CodDoorsan[1]);
        tvCod3.setText(CodDoorsan[2]);
        tvCod4.setText(CodDoorsan[3]);
        tvCod5.setText(CodDoorsan[4]);
        tvCod6.setText(CodDoorsan[5]);
        tvCod7.setText(CodDoorsan[6]);
        tvCod8.setText(CodDoorsan[7]);
        tvCod9.setText(CodDoorsan[8]);
        tvCod10.setText(CodDoorsan[9]);

        edValor1.setText(stDoorsan[0]);
        edValor2.setText(stDoorsan[1]);
        edValor3.setText(stDoorsan[2]);
        edValor4.setText(stDoorsan[3]);
        edValor5.setText(stDoorsan[4]);
        edValor6.setText(stDoorsan[5]);
        edValor7.setText(stDoorsan[6]);
        edValor8.setText(stDoorsan[7]);
        edValor9.setText(stDoorsan[8]);
        edValor10.setText(stDoorsan[9]);

        liUltimo.setVisibility(View.VISIBLE);

        imaIventario.setImageResource(R.drawable.filtro_doosan);
    }
    private void getDtaDoorsan(){
        stDoorsan[0] = edValor1.getText().toString();
        stDoorsan[1] = edValor2.getText().toString();
        stDoorsan[2] = edValor3.getText().toString();
        stDoorsan[3] = edValor4.getText().toString();
        stDoorsan[4] = edValor5.getText().toString();
        stDoorsan[5] = edValor6.getText().toString();
        stDoorsan[6] = edValor7.getText().toString();
        stDoorsan[7] = edValor8.getText().toString();
        stDoorsan[8] = edValor9.getText().toString();
        stDoorsan[9] = edValor10.getText().toString();
    }

    private void viewKomatsu1(){
        tvTitleInven.setText(stTitles[2]);

        tvDescrp1.setText(DesKomatsu1[0]);
        tvDescrp2.setText(DesKomatsu1[1]);
        tvDescrp3.setText(DesKomatsu1[2]);
        tvDescrp4.setText(DesKomatsu1[3]);
        tvDescrp5.setText(DesKomatsu1[4]);
        tvDescrp6.setText(DesKomatsu1[5]);
        tvDescrp7.setText(DesKomatsu1[6]);
        tvDescrp8.setText(DesKomatsu1[7]);
        tvDescrp9.setText(DesKomatsu1[8]);
        //tvDescrp10.setText(DesKomatsu1[9]);

        tvCod1.setText(CodKomatsu1[0]);
        tvCod2.setText(CodKomatsu1[1]);
        tvCod3.setText(CodKomatsu1[2]);
        tvCod4.setText(CodKomatsu1[3]);
        tvCod5.setText(CodKomatsu1[4]);
        tvCod6.setText(CodKomatsu1[5]);
        tvCod7.setText(CodKomatsu1[6]);
        tvCod8.setText(CodKomatsu1[7]);
        tvCod9.setText(CodKomatsu1[8]);
        //tvCod10.setText(CodKomatsu1[9]);

        edValor1.setText(stKomatsu1[0]);
        edValor2.setText(stKomatsu1[1]);
        edValor3.setText(stKomatsu1[2]);
        edValor4.setText(stKomatsu1[3]);
        edValor5.setText(stKomatsu1[4]);
        edValor6.setText(stKomatsu1[5]);
        edValor7.setText(stKomatsu1[6]);
        edValor8.setText(stKomatsu1[7]);
        edValor9.setText(stKomatsu1[8]);
        //edValor10.setText(stKomatsu1[9]);

        liUltimo.setVisibility(View.GONE);

        imaIventario.setImageResource(R.drawable.filtro_komatsu);
    }
    private void getDtaKomatsu1(){
        stKomatsu1[0] = edValor1.getText().toString();
        stKomatsu1[1] = edValor2.getText().toString();
        stKomatsu1[2] = edValor3.getText().toString();
        stKomatsu1[3] = edValor4.getText().toString();
        stKomatsu1[4] = edValor5.getText().toString();
        stKomatsu1[5] = edValor6.getText().toString();
        stKomatsu1[6] = edValor7.getText().toString();
        stKomatsu1[7] = edValor8.getText().toString();
        stKomatsu1[8] = edValor9.getText().toString();
        stKomatsu1[9] = edValor10.getText().toString();
    }

    private void viewKomatsu2(){
        tvTitleInven.setText(stTitles[3]);

        tvDescrp1.setText(DesKomatsu2[0]);
        tvDescrp2.setText(DesKomatsu2[1]);
        tvDescrp3.setText(DesKomatsu2[2]);
        tvDescrp4.setText(DesKomatsu2[3]);
        tvDescrp5.setText(DesKomatsu2[4]);
        tvDescrp6.setText(DesKomatsu2[5]);
        tvDescrp7.setText(DesKomatsu2[6]);
        tvDescrp8.setText(DesKomatsu2[7]);
        tvDescrp9.setText(DesKomatsu2[8]);
        tvDescrp10.setText(DesKomatsu2[9]);

        tvCod1.setText(CodKomatsu2[0]);
        tvCod2.setText(CodKomatsu2[1]);
        tvCod3.setText(CodKomatsu2[2]);
        tvCod4.setText(CodKomatsu2[3]);
        tvCod5.setText(CodKomatsu2[4]);
        tvCod6.setText(CodKomatsu2[5]);
        tvCod7.setText(CodKomatsu2[6]);
        tvCod8.setText(CodKomatsu2[7]);
        tvCod9.setText(CodKomatsu2[8]);
        tvCod10.setText(CodKomatsu2[9]);

        edValor1.setText(stKomatsu2[0]);
        edValor2.setText(stKomatsu2[1]);
        edValor3.setText(stKomatsu2[2]);
        edValor4.setText(stKomatsu2[3]);
        edValor5.setText(stKomatsu2[4]);
        edValor6.setText(stKomatsu2[5]);
        edValor7.setText(stKomatsu2[6]);
        edValor8.setText(stKomatsu2[7]);
        edValor9.setText(stKomatsu2[8]);
        edValor10.setText(stKomatsu2[9]);

        liUltimo.setVisibility(View.VISIBLE);

        imaIventario.setImageResource(R.drawable.filtro_komatsu_pc200);
    }
    private void getDtaKomatsu2(){
        stKomatsu2[0] = edValor1.getText().toString();
        stKomatsu2[1] = edValor2.getText().toString();
        stKomatsu2[2] = edValor3.getText().toString();
        stKomatsu2[3] = edValor4.getText().toString();
        stKomatsu2[4] = edValor5.getText().toString();
        stKomatsu2[5] = edValor6.getText().toString();
        stKomatsu2[6] = edValor7.getText().toString();
        stKomatsu2[7] = edValor8.getText().toString();
        stKomatsu2[8] = edValor9.getText().toString();
        stKomatsu2[9] = edValor10.getText().toString();
    }

    private void viewAceites(){
        tvTitleInven.setText(stTitles[4]);

        tvDescrp1.setText(DesAceites[0]);
        tvDescrp2.setText(DesAceites[1]);
        tvDescrp3.setText(DesAceites[2]);
        tvDescrp4.setText(DesAceites[3]);
        tvDescrp5.setText(DesAceites[4]);
        tvDescrp6.setText(DesAceites[5]);
        tvDescrp7.setText(DesAceites[6]);
        tvDescrp8.setText(DesAceites[7]);
        tvDescrp9.setText(DesAceites[8]);

        tvCod1.setText(CodAceites[0]);
        tvCod2.setText(CodAceites[1]);
        tvCod3.setText(CodAceites[2]);
        tvCod4.setText(CodAceites[3]);
        tvCod5.setText(CodAceites[4]);
        tvCod6.setText(CodAceites[5]);
        tvCod7.setText(CodAceites[6]);
        tvCod8.setText(CodAceites[7]);
        tvCod9.setText(CodAceites[8]);

        edValor1.setText(stAceites[0]);
        edValor2.setText(stAceites[1]);
        edValor3.setText(stAceites[2]);
        edValor4.setText(stAceites[3]);
        edValor5.setText(stAceites[4]);
        edValor6.setText(stAceites[5]);
        edValor7.setText(stAceites[6]);
        edValor8.setText(stAceites[7]);
        edValor9.setText(stAceites[8]);

        liUltimo.setVisibility(View.GONE);

        imaIventario.setImageResource(R.drawable.aceites);
    }
    private void getDtaAceites(){
        stAceites[0] = edValor1.getText().toString();
        stAceites[1] = edValor2.getText().toString();
        stAceites[2] = edValor3.getText().toString();
        stAceites[3] = edValor4.getText().toString();
        stAceites[4] = edValor5.getText().toString();
        stAceites[5] = edValor6.getText().toString();
        stAceites[6] = edValor7.getText().toString();
        stAceites[7] = edValor8.getText().toString();
        stAceites[8] = edValor9.getText().toString();
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btInvenConti:
                if(numFragment==0) {
                    viewCaterpila();
                    numFragment++;
                }else if(numFragment==1) {
                    getDtaCaterpila();
                    viewDoorsan();
                    numFragment++;
                }else if(numFragment==2) {
                    getDtaDoorsan();
                    viewKomatsu1();
                    numFragment++;
                }else if(numFragment==3) {
                    getDtaKomatsu1();
                    viewKomatsu2();
                    numFragment++;
                }else if(numFragment==4) {
                    getDtaKomatsu2();
                    viewAceites();
                    numFragment++;
                }
                break;
            case R.id.btInvenRegreso:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        numFragment--;
        if(numFragment==0)
            super.onBackPressed();

        if(numFragment==1) {
            getDtaDoorsan();
            viewCaterpila();
        }else if(numFragment==2) {
            getDtaKomatsu1();
            viewDoorsan();
        }else if(numFragment==3) {
            getDtaKomatsu2();
            viewKomatsu1();
        }else if(numFragment==4) {
            getDtaAceites();
            viewKomatsu2();
        }
        Log.d("DP_DLOG","onBackPressed "+"num "+numFragment );
    }
}