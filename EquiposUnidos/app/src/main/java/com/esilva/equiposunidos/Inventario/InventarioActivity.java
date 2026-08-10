package com.esilva.equiposunidos.Inventario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.esilva.equiposunidos.Dialog.CustumerDialog;
import com.esilva.equiposunidos.Dialog.ProgressDialog;
import com.esilva.equiposunidos.InspeccionPreoperacional.InpeccDataActivity;
import com.esilva.equiposunidos.MainActivity;
import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.Report.ReportInventario;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.InventarioAceite;
import com.esilva.equiposunidos.db.models.InventarioCaterpila;
import com.esilva.equiposunidos.db.models.InventarioDoorsan;
import com.esilva.equiposunidos.db.models.InventarioKomatsu1;
import com.esilva.equiposunidos.db.models.InventarioKomatsu2;
import com.esilva.equiposunidos.db.models.InventarioMicelanio;
import com.esilva.equiposunidos.util.InventarioAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InventarioActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btContiInven,btRegresarInven,btHomeInven;
    private TextView tvTitle1,tvTitle2,tvFecha;
    private TextView tvCant1,tvCant2;
    private ListView listView1,listView2;

  /*  private TextView tv1Descrp1,tv1Descrp2,tv1Descrp3,tv1Descrp4,tv1Descrp5,tv1Descrp6,tv1Descrp7,tv1Descrp8,tv1Descrp9,tv1Descrp10;
    private TextView tv1Cod1,tv1Cod2,tv1Cod3,tv1Cod4,tv1Cod5,tv1Cod6,tv1Cod7,tv1Cod8,tv1Cod9,tv1Cod10;
    private EditText tv1Valor1,tv1Valor2,tv1Valor3,tv1Valor4,tv1Valor5,tv1Valor6,tv1Valor7,tv1Valor8,tv1Valor9,tv1Valor10;

    private TextView tv2Descrp1,tv2Descrp2,tv2Descrp3,tv2Descrp4,tv2Descrp5,tv2Descrp6,tv2Descrp7,tv2Descrp8,tv2Descrp9,tv2Descrp10;
    private TextView tv2Cod1,tv2Cod2,tv1Cod3,tv1Cod4,tv1Cod5,tv1Cod6,tv1Cod7,tv1Cod8,tv1Cod9,tv1Cod10;
    private EditText tv1Valor1,tv1Valor2,tv1Valor3,tv1Valor4,tv1Valor5,tv1Valor6,tv1Valor7,tv1Valor8,tv1Valor9,tv1Valor10;
*/
    private String[] stTitles;

    private String[] DesCartepila;
    private String[] CodCaterpila;
    private String[] CanCaterpila;

    private String[] DesDoorsan;
    private String[] CodDoorsan;
    private String[] CanDoorsan;

    private String[] DesKomatsu1;
    private String[] CodKomatsu1;
    private String[] CanKomatsu1;

    private String[] DesKomatsu2;
    private String[] CodKomatsu2;
    private String[] CanKomatsu2;

    private String[] DesAceites;
    private String[] CodAceites;
    private String[] CanAceites;

    private String[] DesMicelanio;
    private String[] CodMicelanio;
    private String[] CanMicelanio;

    private boolean isHora;

    private InventarioAdapter inventarioAdapter1,inventarioAdapter2,inventarioAdapter3,inventarioAdapter4,inventarioAdapter5,inventarioAdapter6;

    private AdminBaseDatos adminBaseDatos;

    private InventarioMicelanio inventarioMicelanio;
    private InventarioAceite inventarioAceite;
    private InventarioCaterpila inventarioCaterpila;
    private InventarioDoorsan inventarioDoorsan;
    private InventarioKomatsu1 inventarioKomatsu1;
    private InventarioKomatsu2 inventarioKomatsu2;

    private ProgressDialog progressDialog;
    private CustumerDialog custumerDialog;

    private int numFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inventario);



        setView();
        getDatos();

        inventarioAdapter1 = new InventarioAdapter(this);
        inventarioAdapter1.addArray(DesCartepila,CodCaterpila,CanCaterpila);
        inventarioAdapter2 = new InventarioAdapter(this);
        inventarioAdapter2.addArray(DesDoorsan,CodDoorsan,CanDoorsan);

        inventarioAdapter3 = new InventarioAdapter(this);
        inventarioAdapter3.addArray(DesKomatsu1,CodKomatsu1,CanKomatsu1);
        inventarioAdapter4 = new InventarioAdapter(this);
        inventarioAdapter4.addArray(DesKomatsu2,CodKomatsu2,CanKomatsu2);

        inventarioAdapter5 = new InventarioAdapter(this);
        inventarioAdapter5.addArray(DesAceites,CodAceites,CanAceites);
        inventarioAdapter6 = new InventarioAdapter(this);
        inventarioAdapter6.addArray(DesMicelanio,CodMicelanio,CanMicelanio);
        numFragment = 1;
        setViewPage1();

    }

    private void setViewPage1() {

        tvTitle1.setText(stTitles[0]);
        tvTitle2.setText(stTitles[1]);

        listView1.setAdapter(null);
        listView1.setAdapter(inventarioAdapter1);

        listView2.setAdapter(null);
        listView2.setAdapter(inventarioAdapter2);

        tvCant1.setText("Cantidad");
        tvCant2.setText("Cantidad");

        btContiInven.setText("CONTINUAR");
        btContiInven.setBackground(getDrawable(R.drawable.button_selector));
    }

    private void setViewPage2() {
        tvTitle1.setText(stTitles[2]);
        tvTitle2.setText(stTitles[3]);

        listView1.setAdapter(null);
        listView1.setAdapter(inventarioAdapter3);

        listView2.setAdapter(null);
        listView2.setAdapter(inventarioAdapter4);

        tvCant1.setText("Cantidad");
        tvCant2.setText("Cantidad");

        btContiInven.setText("CONTINUAR");
        btContiInven.setBackground(getDrawable(R.drawable.button_selector));
    }

    private void setViewPage3() {
        tvTitle1.setText("ACEITES");
        tvTitle2.setText("MICELANIO");

        listView1.setAdapter(null);
        listView1.setAdapter(inventarioAdapter5);

        listView2.setAdapter(null);
        listView2.setAdapter(inventarioAdapter6);

        tvCant1.setText("Galones");
        tvCant2.setText("Galones");

        btContiInven.setText("REPORTE");
        btContiInven.setBackground(getDrawable(R.drawable.button_selector_verde));
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


        adminBaseDatos = new AdminBaseDatos(this);
        inventarioMicelanio = adminBaseDatos.micelanio_get();
        inventarioAceite = adminBaseDatos.aceite_get();
        inventarioCaterpila = adminBaseDatos.cat_get();
        inventarioDoorsan = adminBaseDatos.door_get();
        inventarioKomatsu1 = adminBaseDatos.komat1_get();
        inventarioKomatsu2 = adminBaseDatos.komat2_get();

        getCaterpilaData();
        getDoorsanData();
        getKomatsu1Data();
        getKomatsu2Data();
        getAceiteData();
        getMicelanioData();

    }

    private void getCaterpilaData(){
        if(inventarioCaterpila == null)
            return;

        CanCaterpila = new String[10];
        CanCaterpila[0]=String.valueOf(inventarioCaterpila.getfAceiteMotor());
        CanCaterpila[1]=String.valueOf(inventarioCaterpila.getfCombustibleBF13());
        CanCaterpila[2]=String.valueOf(inventarioCaterpila.getfCombustibleBF77());
        CanCaterpila[3]=String.valueOf(inventarioCaterpila.getfServo());
        CanCaterpila[4]=String.valueOf(inventarioCaterpila.getfCabina());
        CanCaterpila[5]=String.valueOf(inventarioCaterpila.getfHidrailicoBT93());
        CanCaterpila[6]=String.valueOf(inventarioCaterpila.getfHidrailicoBT83());
        CanCaterpila[7]=String.valueOf(inventarioCaterpila.getfAireInterno());
        CanCaterpila[8]=String.valueOf(inventarioCaterpila.getfAireExterno());
        CanCaterpila[9]=String.valueOf(inventarioCaterpila.getfPrefijo());
    }

    private void getDoorsanData(){
        if(inventarioDoorsan == null)
            return;

        CanDoorsan = new String[10];
        CanDoorsan[0]=String.valueOf(inventarioDoorsan.getfAceiteMotor());
        CanDoorsan[1]=String.valueOf(inventarioDoorsan.getfCombustible4005());
        CanDoorsan[2]=String.valueOf(inventarioDoorsan.getfCombustible4004());
        CanDoorsan[3]=String.valueOf(inventarioDoorsan.getfServoTrasmision());
        CanDoorsan[4]=String.valueOf(inventarioDoorsan.getfPiloto());
        CanDoorsan[5]=String.valueOf(inventarioDoorsan.getfHidrailico());
        CanDoorsan[6]=String.valueOf(inventarioDoorsan.getfAireCabina());
        CanDoorsan[7]=String.valueOf(inventarioDoorsan.getfAireInterno());
        CanDoorsan[8]=String.valueOf(inventarioDoorsan.getfAireExterno());
        CanDoorsan[9]=String.valueOf(inventarioDoorsan.getfPrefijo());
    }

    private void getKomatsu1Data(){
        if(inventarioKomatsu1 == null)
            return;

        CanKomatsu1 = new String[10];
        CanKomatsu1[0]=String.valueOf(inventarioKomatsu1.getfAceiteMotor());
        CanKomatsu1[1]=String.valueOf(inventarioKomatsu1.getfTrampaCombustible());
        CanKomatsu1[2]=String.valueOf(inventarioKomatsu1.getfCombustible());
        CanKomatsu1[3]=String.valueOf(inventarioKomatsu1.getfCabina());
        CanKomatsu1[4]=String.valueOf(inventarioKomatsu1.getfHidraulico());
        CanKomatsu1[5]=String.valueOf(inventarioKomatsu1.getfRespiradero());
        CanKomatsu1[6]=String.valueOf(inventarioKomatsu1.getfPiloto());
        CanKomatsu1[7]=String.valueOf(inventarioKomatsu1.getfAireInterno());
        CanKomatsu1[8]=String.valueOf(inventarioKomatsu1.getfAireExterno());
    }

    private void getKomatsu2Data(){
        if(inventarioKomatsu2 == null)
            return;

        CanKomatsu2 = new String[10];
        CanKomatsu2[0]=String.valueOf(inventarioKomatsu2.getfAceiteMotor());
        CanKomatsu2[1]=String.valueOf(inventarioKomatsu2.getfTrampaCombustible());
        CanKomatsu2[2]=String.valueOf(inventarioKomatsu2.getfCombustible());
        CanKomatsu2[3]=String.valueOf(inventarioKomatsu2.getfCabina());
        CanKomatsu2[4]=String.valueOf(inventarioKomatsu2.getfHidraulico());
        CanKomatsu2[5]=String.valueOf(inventarioKomatsu2.getfRespiradero());
        CanKomatsu2[6]=String.valueOf(inventarioKomatsu2.getfPiloto());
        CanKomatsu2[7]=String.valueOf(inventarioKomatsu2.getfAireInterno());
        CanKomatsu2[8]=String.valueOf(inventarioKomatsu2.getfAireExterno());
        CanKomatsu2[9]=String.valueOf(inventarioKomatsu2.getfRefregerante());
    }

    private void getAceiteData() {
        if (inventarioAceite == null)
            return;

        CanAceites = new String[7];
        CanAceites[0] = String.valueOf(inventarioAceite.getA15W40());
        CanAceites[1] = String.valueOf(inventarioAceite.getIso68());
        CanAceites[2] = String.valueOf(inventarioAceite.getTo30());
        CanAceites[3] = String.valueOf(inventarioAceite.getA80W90());
        CanAceites[4] = String.valueOf(inventarioAceite.getS527());
        CanAceites[5] = String.valueOf(inventarioAceite.getG_Litio());
        CanAceites[6] = String.valueOf(inventarioAceite.getMotor());
    }

    private void getMicelanioData() {
        if (inventarioMicelanio == null)
            return;

        CanMicelanio = new String[2];
        CanMicelanio[0] = String.valueOf(inventarioMicelanio.getAC_R134());
        CanMicelanio[1] = String.valueOf(inventarioMicelanio.getX70());
    }

    private void setView() {
        tvFecha = findViewById(R.id.fecha_horaInven);
        tvCant1 = findViewById(R.id.tv1Cantidad);
        tvCant2 = findViewById(R.id.tv2Cantidad);
        btContiInven = findViewById(R.id.btInvenConti);
        btContiInven.setOnClickListener(this);
        btRegresarInven = findViewById(R.id.btInvenRegresar);
        btRegresarInven.setOnClickListener(this);
        btHomeInven = findViewById(R.id.btInvenHome);
        btHomeInven.setOnClickListener(this);

        tvTitle1 = findViewById(R.id.tvTitu1_Inven);
        tvTitle2 = findViewById(R.id.tvTitu2_Inven);

        listView1 = findViewById(R.id.listInve1);
        listView2 = findViewById(R.id.listInve2);

        //liUltimo = findViewById(R.id.liUltimo);

        //viewDescrip();
        //viewCodigo();
        //viewCantidad();

       // numFragment=1;

    }



    @Override
    public void onClick(View view) {
        int id = view.getId();

        if( id == R.id.btInvenConti) {
            if (numFragment == 0) {
                setViewPage1();
                numFragment++;
            } else if (numFragment == 1) {
                setViewPage2();
                numFragment++;
            } else if (numFragment == 2) {
                setViewPage3();
                numFragment++;
            } else if (numFragment == 3) {
                generarReporte();
            }
        }
        else if( id == R.id.btInvenRegresar) {
            onBackPressed();
        }
        else if( id ==  R.id.btInvenHome) {
            startActivity(new Intent(this, MainActivity.class));
        }

    }

    @Override
    public void onBackPressed() {
        numFragment--;
        if(numFragment==0)
            super.onBackPressed();

        if(numFragment==1) {
            setViewPage1();
        }else if(numFragment==2) {
            setViewPage2();
        }
        Log.d("DP_DLOG","onBackPressed "+"num "+numFragment );
    }

    private void generarReporte() {
        progressDialog = new ProgressDialog(InventarioActivity.this);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReportInventario reportInventario = new ReportInventario(InventarioActivity.this);
                boolean b = reportInventario.buildReport();
                showResult(b);
            }
        }).start();
    }

    private void showResult(boolean result){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                if(result)
                    custumerDialog = new CustumerDialog(InventarioActivity.this,"SUCCESS!", "Reporte actualizado",false,false);
                else
                    custumerDialog = new CustumerDialog(InventarioActivity.this,"FAIL!", "Error Guardando el reporte",true,false);
                custumerDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        custumerDialog.dismiss();
                        startActivity(new Intent(InventarioActivity.this, MainActivity.class));
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