package com.esilva.equiposunidos.Inventario;

import static com.esilva.equiposunidos.util.Constantes.VALUE_INTENT_BOOLEAN;
import static com.esilva.equiposunidos.util.Constantes.VALUE_INTENT_TEXT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.esilva.equiposunidos.Dialog.CustumerDialog;
import com.esilva.equiposunidos.Dialog.CustumerDialogButton;
import com.esilva.equiposunidos.MainActivity;
import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.RegistroInOut.FormRegisterInOutActivity;
import com.esilva.equiposunidos.application.UnidosApplication;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.Historial;
import com.esilva.equiposunidos.db.models.InventarioAceite;
import com.esilva.equiposunidos.db.models.InventarioCaterpila;
import com.esilva.equiposunidos.db.models.InventarioDoorsan;
import com.esilva.equiposunidos.db.models.InventarioKomatsu1;
import com.esilva.equiposunidos.db.models.InventarioKomatsu2;
import com.esilva.equiposunidos.db.models.InventarioMicelanio;
import com.esilva.equiposunidos.util.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormInventarioActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvTitleInven,tvTitleFormInventa,tvFecha,tvTitleCant;
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

    private String[] DesMicelanio;
    private String[] CodMicelanio;

    private String[] stCaterpila;
    private String[] stDoorsan;
    private String[] stKomatsu1;
    private String[] stKomatsu2;
    private String[] stAceites;

    private int[] intCaterpila;
    private int[] intDoorsan;
    private int[] intKomatsu1;
    private int[] intKomatsu2;
    private int[] intAceites;

    private int[] intTotales;

    private int numFragment;
    private boolean isHora;

    private AdminBaseDatos adminBaseDatos;

    private InventarioMicelanio inventarioMicelanio;
    private InventarioAceite    inventarioAceite;
    private InventarioCaterpila inventarioCaterpila;
    private InventarioDoorsan   inventarioDoorsan;
    private InventarioKomatsu1  inventarioKomatsu1;
    private InventarioKomatsu2  inventarioKomatsu2;
    private Historial historial;

    private String textTitle;
    private boolean isEntrada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_form_inventario);

        Intent intent = getIntent();
        isEntrada = intent.getBooleanExtra(VALUE_INTENT_BOOLEAN,false);
        textTitle = intent.getStringExtra(VALUE_INTENT_TEXT);

        adminBaseDatos = new AdminBaseDatos(this);
        inventarioMicelanio = adminBaseDatos.micelanio_get();
        inventarioAceite = adminBaseDatos.aceite_get();
        inventarioCaterpila = adminBaseDatos.cat_get();
        inventarioDoorsan = adminBaseDatos.door_get();
        inventarioKomatsu1 = adminBaseDatos.komat1_get();
        inventarioKomatsu2 = adminBaseDatos.komat2_get();
        historial = new Historial();

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

        DesAceites = getResources().getStringArray(R.array.des_aceites);
        CodAceites = getResources().getStringArray(R.array.cod_aceites);

        DesMicelanio = getResources().getStringArray(R.array.micela_desc);
        CodMicelanio = getResources().getStringArray(R.array.micela_cod);

        stCaterpila = new String[10];
        stDoorsan = new String[10];
        stKomatsu1 = new String[10];
        stKomatsu2 = new String[10];
        stAceites = new String[10];

        intCaterpila = new int[10];
        intDoorsan   = new int[10];
        intKomatsu1  = new int[10];
        intKomatsu2  = new int[10];
        intAceites   = new int[10];

        intTotales = new int[5];
    }

    private void setView() {
        tvTitleCant = findViewById(R.id.tvTitleCant);
        tvFecha = findViewById(R.id.fecha_horaInven);
        btContiInven = findViewById(R.id.btInvenConti);
        btContiInven.setOnClickListener(this);
        btRegresarInven = findViewById(R.id.btInvenRegreso);
        btRegresarInven.setOnClickListener(this);

        tvTitleInven = findViewById(R.id.tvTitleInvetario);
        tvTitleFormInventa = findViewById(R.id.tvTitleFormInventa);
        tvTitleFormInventa.setText(textTitle);
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
        btContiInven.setText("CONTINUAR");
        btContiInven.setBackground(getDrawable(R.drawable.button_selector));
        tvTitleCant.setText("Cantidad");
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
        btContiInven.setText("CONTINUAR");
        btContiInven.setBackground(getDrawable(R.drawable.button_selector));
        tvTitleCant.setText("Cantidad");
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
        btContiInven.setText("CONTINUAR");
        btContiInven.setBackground(getDrawable(R.drawable.button_selector));
        tvTitleCant.setText("Cantidad");

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
        btContiInven.setText("CONTINUAR");
        btContiInven.setBackground(getDrawable(R.drawable.button_selector));
        tvTitleCant.setText("Cantidad");

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
        tvDescrp8.setText(DesMicelanio[0]);
        tvDescrp9.setText(DesMicelanio[1]);

        tvCod1.setText(CodAceites[0]);
        tvCod2.setText(CodAceites[1]);
        tvCod3.setText(CodAceites[2]);
        tvCod4.setText(CodAceites[3]);
        tvCod5.setText(CodAceites[4]);
        tvCod6.setText(CodAceites[5]);
        tvCod7.setText(CodAceites[6]);
        tvCod8.setText(CodMicelanio[0]);
        tvCod9.setText(CodMicelanio[1]);

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
        btContiInven.setText("INGRESAR");
        btContiInven.setBackground(getDrawable(R.drawable.button_selector_verde));
        tvTitleCant.setText("Galones");

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
        int id = view.getId();

        if(id == R.id.btInvenConti) {
            if (numFragment == 0) {
                viewCaterpila();
                numFragment++;
            } else if (numFragment == 1) {
                getDtaCaterpila();
                viewDoorsan();
                numFragment++;
            } else if (numFragment == 2) {
                getDtaDoorsan();
                viewKomatsu1();
                numFragment++;
            } else if (numFragment == 3) {
                getDtaKomatsu1();
                viewKomatsu2();
                numFragment++;
            } else if (numFragment == 4) {
                getDtaKomatsu2();
                viewAceites();
                numFragment++;
            } else if (numFragment == 5) {
                getDtaAceites();
                showDialogInven();
            }
        }
        else if(id == R.id.btInvenRegreso) {
            onBackPressed();
        }
    }

    private void showDialogInven() {
        if(validateData()) {
            CustumerDialogButton custumerDialogButton = new CustumerDialogButton(this,
                    "Productos", "Numero de productos a ingresar\nen el inventario\n"+String.valueOf(intTotales[0]+intTotales[1]+intTotales[2]+intTotales[3]+intTotales[4]));
            custumerDialogButton.setOnClickListener(new CustumerDialogButton.LisenerDailog() {
                @Override
                public void OnClickSI() {
                    CustumerDialog custumerDialog;
                    if(IngresarDatos()){
                        if(isEntrada)
                            custumerDialog = new CustumerDialog(FormInventarioActivity.this,"SUCCESS!","Articulos ingresados correctaemnte",false,false);
                        else
                            custumerDialog = new CustumerDialog(FormInventarioActivity.this,"SUCCESS!","Articulos retirados correctaemnte",false,false);
                        custumerDialog.show();
                    }else{
                        if(isEntrada)
                            custumerDialog = new CustumerDialog(FormInventarioActivity.this,"FAIL!","Error ingresando articulos",true,false);
                        else
                            custumerDialog = new CustumerDialog(FormInventarioActivity.this,"FAIL!","Error retirando articulos",true,false);
                        custumerDialog.show();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            custumerDialog.dismiss();
                            startActivity(new Intent(FormInventarioActivity.this, MainActivity.class));
                            finish();
                        }
                    },4000);
                }

                @Override
                public void OnClickNO() {
                    custumerDialogButton.dismiss();
                }
            });
            custumerDialogButton.show();
        }else {
            Toast.makeText(this,"Sin productos para ingresar",Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateData() {
        historial.init();
        intTotales[0] = 0;
        intTotales[1] = 0;
        intTotales[2] = 0;
        intTotales[3] = 0;
        intTotales[4] = 0;
        for(int i=0;i<10;i++) {
            intCaterpila[i] = stCaterpila[i].isEmpty() ? 0 : Integer.valueOf(stCaterpila[i]);
            intTotales[0] += intCaterpila[i];
            if(intCaterpila[i]>0){
                historial.addItem(CodCaterpila[i],intCaterpila[i]);
            }
        }
        for(int i=0;i<10;i++) {
            intDoorsan[i] = stDoorsan[i].isEmpty() ? 0 : Integer.valueOf(stDoorsan[i]);
            intTotales[1] += intDoorsan[i];
            if(intDoorsan[i]>0){
                historial.addItem(CodDoorsan[i],intDoorsan[i]);
            }
        }
        for(int i=0;i<9;i++) {
            intKomatsu1[i] = stKomatsu1[i].isEmpty() ? 0 : Integer.valueOf(stKomatsu1[i]);
            intTotales[2] += intKomatsu1[i];
            if(intKomatsu1[i]>0){
                historial.addItem(CodKomatsu1[i],intKomatsu1[i]);
            }
        }
        for(int i=0;i<10;i++) {
            intKomatsu2[i] = stKomatsu2[i].isEmpty() ? 0 : Integer.valueOf(stKomatsu2[i]);
            intTotales[3] += intKomatsu2[i];
            if(intKomatsu2[i]>0){
                historial.addItem(CodKomatsu2[i],intKomatsu2[i]);
            }
        }
        for(int i=0;i<9;i++) {
            intAceites[i] = stAceites[i].isEmpty() ? 0 : Integer.valueOf(stAceites[i]);
            intTotales[4] += intAceites[i];
            if(intAceites[i]>0){
                if(i<7)
                    historial.addItem(CodAceites[i],intAceites[i]);
                else
                    historial.addItem(CodMicelanio[i-7],intAceites[i]);
            }
        }
        if(intTotales[0] > 0){
            return true;
        }
        if(intTotales[1] > 0){
            return true;
        }
        if(intTotales[2] > 0){
            return true;
        }
        if(intTotales[3] > 0){
            return true;
        }
        if(intTotales[4] > 0){
            return true;
        }

        return false;
    }

    private boolean IngresarDatos() {
        actualizarCaterpila();
        actualizarDoorsan();
        actualizarKomatsu1();
        actualizarKomatsu2();
        actualizarAceite();
        actualizarMicelanio();

        if(!adminBaseDatos.cat_update(inventarioCaterpila)){
            Toast.makeText(this,"Error actualizando datos Caterpila",Toast.LENGTH_LONG).show();
            return false;
        }
        if(!adminBaseDatos.door_update(inventarioDoorsan)){
            Toast.makeText(this,"Error actualizando datos Doorsan",Toast.LENGTH_LONG).show();
            return false;
        }
        if(!adminBaseDatos.komat1_update(inventarioKomatsu1)){
            Toast.makeText(this,"Error actualizando datos Komatsu 1",Toast.LENGTH_LONG).show();
            return false;
        }
        if(!adminBaseDatos.komat2_update(inventarioKomatsu2)){
            Toast.makeText(this,"Error actualizando datos komatsu 2",Toast.LENGTH_LONG).show();
            return false;
        }
        if(!adminBaseDatos.aceite_update(inventarioAceite)){
            Toast.makeText(this,"Error actualizando datos aceites",Toast.LENGTH_LONG).show();
            return false;
        }
        if(!adminBaseDatos.mice_update(inventarioMicelanio)){
            Toast.makeText(this,"Error actualizando datos micelaio",Toast.LENGTH_LONG).show();
            return false;
        }

        historial.setFecha(Util.getFechaHora());
        historial.setUser(UnidosApplication.getUser().getNombre());
        if(isEntrada)
            historial.setAdd(true);
        else
            historial.setAdd(false);
        adminBaseDatos.history_insert(historial);
        adminBaseDatos.closeBaseDtos();
        return true;
    }

    private void actualizarCaterpila() {
        int valor;
        if(isEntrada)
            valor = inventarioCaterpila.getfAceiteMotor()+intCaterpila[0];
        else
            valor = inventarioCaterpila.getfAceiteMotor()-intCaterpila[0];
        inventarioCaterpila.setfAceiteMotor(valor);

        if(isEntrada)
            valor = inventarioCaterpila.getfCombustibleBF13()+intCaterpila[1];
        else
            valor = inventarioCaterpila.getfCombustibleBF13()-intCaterpila[1];
        inventarioCaterpila.setfCombustibleBF13(valor);

        if(isEntrada)
            valor = inventarioCaterpila.getfCombustibleBF77()+intCaterpila[2];
        else
            valor = inventarioCaterpila.getfCombustibleBF77()-intCaterpila[2];
        inventarioCaterpila.setfCombustibleBF77(valor);

        if(isEntrada)
            valor = inventarioCaterpila.getfServo()+intCaterpila[3];
        else
            valor = inventarioCaterpila.getfServo()-intCaterpila[3];
        inventarioCaterpila.setfServo(valor);

        if(isEntrada)
            valor = inventarioCaterpila.getfCabina()+intCaterpila[4];
        else
            valor = inventarioCaterpila.getfCabina()-intCaterpila[4];
        inventarioCaterpila.setfCabina(valor);

        if(isEntrada)
            valor = inventarioCaterpila.getfHidrailicoBT93()+intCaterpila[5];
        else
            valor = inventarioCaterpila.getfHidrailicoBT93()-intCaterpila[5];
        inventarioCaterpila.setfHidrailicoBT93(valor);

        if(isEntrada)
            valor = inventarioCaterpila.getfHidrailicoBT83()+intCaterpila[6];
        else
            valor = inventarioCaterpila.getfHidrailicoBT83()-intCaterpila[6];
        inventarioCaterpila.setfHidrailicoBT83(valor);

        if(isEntrada)
            valor = inventarioCaterpila.getfAireInterno()+intCaterpila[7];
        else
            valor = inventarioCaterpila.getfAireInterno()-intCaterpila[7];
        inventarioCaterpila.setfAireInterno(valor);

        if(isEntrada)
            valor = inventarioCaterpila.getfAireExterno()+intCaterpila[8];
        else
            valor = inventarioCaterpila.getfAireExterno()-intCaterpila[8];
        inventarioCaterpila.setfAireExterno(valor);

        if(isEntrada)
            valor = inventarioCaterpila.getfPrefijo()+intCaterpila[9];
        else
            valor = inventarioCaterpila.getfPrefijo()-intCaterpila[9];
        inventarioCaterpila.setfPrefijo(valor);
    }
    private void actualizarDoorsan() {
        int valor;
        valor = inventarioDoorsan.getfAceiteMotor()+intDoorsan[0];
        inventarioDoorsan.setfAceiteMotor(valor);

        valor = inventarioDoorsan.getfCombustible4005()+intDoorsan[1];
        inventarioDoorsan.setfCombustible4005(valor);

        valor = inventarioDoorsan.getfCombustible4004()+intDoorsan[2];
        inventarioDoorsan.setfCombustible4004(valor);

        valor = inventarioDoorsan.getfServoTrasmision()+intDoorsan[3];
        inventarioDoorsan.setfServoTrasmision(valor);

        valor = inventarioDoorsan.getfPiloto()+intDoorsan[4];
        inventarioDoorsan.setfPiloto(valor);

        valor = inventarioDoorsan.getfHidrailico()+intDoorsan[5];
        inventarioDoorsan.setfHidrailico(valor);

        valor = inventarioDoorsan.getfAireCabina()+intDoorsan[6];
        inventarioDoorsan.setfAireCabina(valor);

        valor = inventarioDoorsan.getfAireInterno()+intDoorsan[7];
        inventarioDoorsan.setfAireInterno(valor);

        valor = inventarioDoorsan.getfAireExterno()+intDoorsan[8];
        inventarioDoorsan.setfAireExterno(valor);

        valor = inventarioDoorsan.getfPrefijo()+intDoorsan[9];
        inventarioDoorsan.setfPrefijo(valor);
    }
    private void actualizarKomatsu1() {
        int valor;
        valor = inventarioKomatsu1.getfAceiteMotor()+intKomatsu1[0];
        inventarioKomatsu1.setfAceiteMotor(valor);

        valor = inventarioKomatsu1.getfTrampaCombustible()+intKomatsu1[1];
        inventarioKomatsu1.setfTrampaCombustible(valor);

        valor = inventarioKomatsu1.getfCombustible()+intKomatsu1[2];
        inventarioKomatsu1.setfCombustible(valor);

        valor = inventarioKomatsu1.getfCabina()+intKomatsu1[3];
        inventarioKomatsu1.setfCabina(valor);

        valor = inventarioKomatsu1.getfHidraulico()+intKomatsu1[4];
        inventarioKomatsu1.setfHidraulico(valor);

        valor = inventarioKomatsu1.getfRespiradero()+intKomatsu1[5];
        inventarioKomatsu1.setfRespiradero(valor);

        valor = inventarioKomatsu1.getfPiloto()+intKomatsu1[6];
        inventarioKomatsu1.setfPiloto(valor);

        valor = inventarioKomatsu1.getfAireInterno()+intKomatsu1[7];
        inventarioKomatsu1.setfAireInterno(valor);

        valor = inventarioKomatsu1.getfAireExterno()+intKomatsu1[8];
        inventarioKomatsu1.setfAireExterno(valor);
    }
    private void actualizarKomatsu2() {
        int valor;
        valor = inventarioKomatsu2.getfAceiteMotor()+intKomatsu2[0];
        inventarioKomatsu2.setfAceiteMotor(valor);

        valor = inventarioKomatsu2.getfTrampaCombustible()+intKomatsu2[1];
        inventarioKomatsu2.setfTrampaCombustible(valor);

        valor = inventarioKomatsu2.getfCombustible()+intKomatsu2[2];
        inventarioKomatsu2.setfCombustible(valor);

        valor = inventarioKomatsu2.getfCabina()+intKomatsu2[3];
        inventarioKomatsu2.setfCabina(valor);

        valor = inventarioKomatsu2.getfHidraulico()+intKomatsu2[4];
        inventarioKomatsu2.setfHidraulico(valor);

        valor = inventarioKomatsu2.getfRespiradero()+intKomatsu2[5];
        inventarioKomatsu2.setfRespiradero(valor);

        valor = inventarioKomatsu2.getfPiloto()+intKomatsu2[6];
        inventarioKomatsu2.setfPiloto(valor);

        valor = inventarioKomatsu2.getfAireInterno()+intKomatsu2[7];
        inventarioKomatsu2.setfAireInterno(valor);

        valor = inventarioKomatsu2.getfAireExterno()+intKomatsu2[8];
        inventarioKomatsu2.setfAireExterno(valor);

        valor = inventarioKomatsu2.getfRefregerante()+intKomatsu2[9];
        inventarioKomatsu2.setfRefregerante(valor);
    }
    private void actualizarAceite() {
        int valor;
        if(isEntrada)
            valor = inventarioAceite.getA15W40()+intAceites[0];
        else
            valor = inventarioAceite.getA15W40()-intAceites[0];
        inventarioAceite.setA15W40(valor);

        if(isEntrada)
            valor = inventarioAceite.getIso68()+intAceites[1];
        else
            valor = inventarioAceite.getIso68()-intAceites[1];
        inventarioAceite.setIso68(valor);

        if(isEntrada)
            valor = inventarioAceite.getTo30()+intAceites[2];
        else
            valor = inventarioAceite.getTo30()-intAceites[2];
        inventarioAceite.setTo30(valor);

        if(isEntrada)
            valor = inventarioAceite.getA80W90()+intAceites[3];
        else
            valor = inventarioAceite.getA80W90()-intAceites[3];
        inventarioAceite.setA80W90(valor);

        if(isEntrada)
            valor = inventarioAceite.getS527()+intAceites[4];
        else
            valor = inventarioAceite.getS527()-intAceites[4];
        inventarioAceite.setS527(valor);

        if(isEntrada)
            valor = inventarioAceite.getG_Litio()+intAceites[5];
        else
            valor = inventarioAceite.getG_Litio()-intAceites[5];
        inventarioAceite.setG_Litio(valor);

        if(isEntrada)
            valor = inventarioAceite.getMotor()+intAceites[6];
        else
            valor = inventarioAceite.getMotor()-intAceites[6];
        inventarioAceite.setMotor(valor);
    }
    private void actualizarMicelanio() {
        int valor;
        if(isEntrada)
            valor = inventarioMicelanio.getAC_R134()+intAceites[7];
        else
            valor = inventarioMicelanio.getAC_R134()-intAceites[7];
        inventarioMicelanio.setAC_R134(valor);

        if(isEntrada)
            valor = inventarioMicelanio.getX70()+intAceites[8];
        else
            valor = inventarioMicelanio.getX70()-intAceites[8];
        inventarioMicelanio.setX70(valor);
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

    @Override
    protected void onDestroy() {
        adminBaseDatos.closeBaseDtos();
        super.onDestroy();
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