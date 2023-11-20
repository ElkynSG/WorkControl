package com.esilva.equiposunidos.Mantenimiento;

import static com.esilva.equiposunidos.util.Constantes.EQUIPO_TIPO_CARGADOR;
import static com.esilva.equiposunidos.util.Constantes.FILE_IMAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.tv.TsRequest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.application.UnidosApplication;
import com.esilva.equiposunidos.db.models.DataManteni;
import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.Manteni;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ActiCorrectivoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imaEquipo;
    private TextView tv_1,tv_2,tv_3,tv_4;
    private CheckBox check_1_1,check_2_1,check_3_1,check_4_1,check_5_1,check_6_1,check_7_1,check_8_1,check_9_1,check_10_1,check_11_1,check_12_1;
    private CheckBox check_1_2,check_2_2,check_3_2,check_4_2,check_5_2,check_6_2,check_7_2,check_8_2,check_9_2,check_10_2,check_11_2,check_12_2;
    private EditText edTx_1,edTx_2,edTx_3,edTx_4,edTx_5,edTx_6,edTx_7,edTx_8,edTx_9,edTx_10,edTx_11,edTx_12;
    private Button btContCorre,btCorreRegres;
    private Equipos equipoSelect;
    private  DataManteni dataManteni;
    private List<Manteni> listMante = new ArrayList<Manteni>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_correctivo);
        equipoSelect = UnidosApplication.getEquipo();
        dataManteni = UnidosApplication.getDataManteni();
        setView();
    }

    private void setView() {
        imaEquipo = findViewById(R.id.imImageEquipoCorrec);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/"+equipoSelect.getFoto());
        File pathImage = new File(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE, equipoSelect.getFoto());
        if(pathImage.exists() == false)
            uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+FILE_IMAGE+"/fotico.png");

        imaEquipo.setBackground(getResources().getDrawable(R.drawable.shape_image_azul));
        imaEquipo.setImageURI(uri);

        btContCorre = findViewById(R.id.btContiCorre);
        btContCorre.setOnClickListener(this);
        btCorreRegres = findViewById(R.id.btCorreRegresa);
        btCorreRegres.setOnClickListener(this);

        tv_1 = findViewById(R.id.tvCorrecVolteo1);
        tv_2 = findViewById(R.id.tvCorrecFinales2);
        tv_3 = findViewById(R.id.tvCorrecLLantas3);
        tv_4 = findViewById(R.id.tvCorrecDireccion4);
        if(equipoSelect != null) {
            if (equipoSelect.getTipo() == EQUIPO_TIPO_CARGADOR) {
                tv_1.setText("PLUMA, BRAZO, BALDE");
                tv_2.setText("MOTOR DE GIRO, CENTER, CONTROL DE VALVULAS");
                tv_3.setText("ZAPATAS, CADENAS, BASTIDOR, CARRILES, TENSORA, SPROCKET");
                tv_4.setText("MOTORES DE TRASLACION");
            }
        }
        check_1_1 = findViewById(R.id.cheCorrec1_1);
        check_1_2 = findViewById(R.id.cheCorrec1_2);

        check_2_1 = findViewById(R.id.cheCorrec2_1);
        check_2_2 = findViewById(R.id.cheCorrec2_2);

        check_3_1 = findViewById(R.id.cheCorrec3_1);
        check_3_2 = findViewById(R.id.cheCorrec3_2);

        check_4_1 = findViewById(R.id.cheCorrec4_1);
        check_4_2 = findViewById(R.id.cheCorrec4_2);

        check_5_1 = findViewById(R.id.cheCorrec5_1);
        check_5_2 = findViewById(R.id.cheCorrec5_2);

        check_6_1 = findViewById(R.id.cheCorrec6_1);
        check_6_2 = findViewById(R.id.cheCorrec6_2);

        check_7_1 = findViewById(R.id.cheCorrec7_1);
        check_7_2 = findViewById(R.id.cheCorrec7_2);

        check_8_1 = findViewById(R.id.cheCorrec8_1);
        check_8_2 = findViewById(R.id.cheCorrec8_2);

        check_9_1 = findViewById(R.id.cheCorrec9_1);
        check_9_2 = findViewById(R.id.cheCorrec9_2);

        check_10_1 = findViewById(R.id.cheCorrec10_1);
        check_10_2 = findViewById(R.id.cheCorrec10_2);

        check_11_1 = findViewById(R.id.cheCorrec11_1);
        check_11_2 = findViewById(R.id.cheCorrec11_2);

        check_12_1 = findViewById(R.id.cheCorrec12_1);
        check_12_2 = findViewById(R.id.cheCorrec12_2);

        checkLister();

        edTx_1 = findViewById(R.id.edCorrec1);
        edTx_2 = findViewById(R.id.edCorrec2);
        edTx_3 = findViewById(R.id.edCorrec3);
        edTx_4 = findViewById(R.id.edCorrec4);
        edTx_5 = findViewById(R.id.edCorrec5);
        edTx_6 = findViewById(R.id.edCorrec6);
        edTx_7 = findViewById(R.id.edCorrec7);
        edTx_8 = findViewById(R.id.edCorrec8);
        edTx_9 = findViewById(R.id.edCorrec9);
        edTx_10 = findViewById(R.id.edCorrec10);
        edTx_11 = findViewById(R.id.edCorrec11);
        edTx_12 = findViewById(R.id.edCorrec12);
    }

    private void checkLister(){
        check_1_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    check_1_2.setChecked(false);
                }
            }
        });
        check_1_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    check_1_1.setChecked(false);
                }
            }
        });

        check_2_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    check_2_2.setChecked(false);
                }
            }
        });
        check_2_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    check_2_1.setChecked(false);
                }
            }
        });

        check_3_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    check_3_2.setChecked(false);
                }
            }
        });
        check_3_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    check_3_1.setChecked(false);
                }
            }
        });

        check_4_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    check_4_2.setChecked(false);
                }
            }
        });
        check_4_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    check_4_1.setChecked(false);
                }
            }
        });

        check_5_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    check_5_2.setChecked(false);
                }
            }
        });
        check_5_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    check_5_1.setChecked(false);
                }
            }
        });

        check_6_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    check_6_2.setChecked(false);
                }
            }
        });
        check_6_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    check_6_1.setChecked(false);
                }
            }
        });

        check_7_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    check_7_2.setChecked(false);
                }
            }
        });
        check_7_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    check_7_1.setChecked(false);
                }
            }
        });

        check_8_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    check_8_2.setChecked(false);
                }
            }
        });
        check_8_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    check_8_1.setChecked(false);
                }
            }
        });

        check_9_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    check_9_2.setChecked(false);
                }
            }
        });
        check_9_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    check_9_1.setChecked(false);
                }
            }
        });

        check_10_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    check_10_2.setChecked(false);
                }
            }
        });
        check_10_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    check_10_1.setChecked(false);
                }
            }
        });

        check_11_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    check_11_2.setChecked(false);
                }
            }
        });
        check_11_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    check_11_1.setChecked(false);
                }
            }
        });

        check_12_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    check_12_2.setChecked(false);
                }
            }
        });
        check_12_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    check_12_1.setChecked(false);
                }
            }
        });



    }

    private boolean verifyCheck(){
        if(!check_1_1.isChecked() && !check_1_2.isChecked())
            return false;
        if(!check_2_1.isChecked() && !check_2_2.isChecked())
            return false;
        if(!check_3_1.isChecked() && !check_3_2.isChecked())
            return false;
        if(!check_4_1.isChecked() && !check_4_2.isChecked())
            return false;
        if(!check_5_1.isChecked() && !check_5_2.isChecked())
            return false;
        if(!check_6_1.isChecked() && !check_6_2.isChecked())
            return false;
        if(!check_7_1.isChecked() && !check_7_2.isChecked())
            return false;
        if(!check_8_1.isChecked() && !check_8_2.isChecked())
            return false;
        if(!check_9_1.isChecked() && !check_9_2.isChecked())
            return false;
        if(!check_10_1.isChecked() && !check_10_2.isChecked())
            return false;
        if(!check_11_1.isChecked() && !check_11_2.isChecked())
            return false;
        if(!check_12_1.isChecked() && !check_12_2.isChecked())
            return false;
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btContiCorre:
                if(verifyCheck()) {
                    saveDataManteni();
                    UnidosApplication.setListManteni(listMante);
                    startActivity(new Intent(this, InsumosActivity.class));
                }else
                    Toast.makeText(this,"Complete la revision, Por favor",Toast.LENGTH_LONG).show();
                break;
            case R.id.btCorreRegresa:
                onBackPressed();
                break;
            default:
                break;

        }
    }
    private void addList(boolean isSI, String comment){
        Manteni temp1 = new Manteni();
        if(isSI)
            temp1.setbSI(true);
        else
            temp1.setbNA(true);
        temp1.setComentario(comment);
        listMante.add(temp1);
    }

    private void saveDataManteni(){
        addList(check_1_1.isChecked(),edTx_1.getText().toString());
        addList(check_2_1.isChecked(),edTx_2.getText().toString());
        addList(check_3_1.isChecked(),edTx_3.getText().toString());
        addList(check_4_1.isChecked(),edTx_4.getText().toString());
        addList(check_5_1.isChecked(),edTx_5.getText().toString());
        addList(check_6_1.isChecked(),edTx_6.getText().toString());
        addList(check_7_1.isChecked(),edTx_7.getText().toString());
        addList(check_8_1.isChecked(),edTx_8.getText().toString());
        addList(check_9_1.isChecked(),edTx_9.getText().toString());
        addList(check_10_1.isChecked(),edTx_10.getText().toString());
        addList(check_11_1.isChecked(),edTx_11.getText().toString());
        addList(check_12_1.isChecked(),edTx_12.getText().toString());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}