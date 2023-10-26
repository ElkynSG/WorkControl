package com.esilva.equiposunidos.Setting;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.esilva.equiposunidos.Dialog.CustumerDialog;
import com.esilva.equiposunidos.Dialog.CustumerDialogButton;
import com.esilva.equiposunidos.Dialog.ProgressDialog;
import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.User;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.esilva.equiposunidos.util.Constantes.*;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener ,UserFragment.ItemListener{
    private  final String fraUsu = "fragmentUsuario";
    private final String fraItem = "fragmentItems";

    private Button btChargeUser;
    private Fragment fragmentUsuarios, fragItems;
    private ProgressDialog progressDialog;
    private AdminBaseDatos adminBaseDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setView();
    }

    private void setView() {
        fragmentUsuarios = new UserFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_setting, fragmentUsuarios,fraUsu)
                .commit();
        progressDialog = new ProgressDialog(this,"Cargando");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btSettUsuario:
                verifi();
                
                break;
            default:
                break;
        }
    }


    /**************************   carga de usuarios   ***********************/

    private void showResult(Boolean isOK){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CustumerDialog custumerDialog;
                if(isOK)
                    custumerDialog = new CustumerDialog(SettingActivity.this,"SUCCESS!","Datos de usuarios cargados exitosamente",false);
                else
                    custumerDialog = new CustumerDialog(SettingActivity.this,"FAIL!","Error guardando datos de susuarios",true);
                custumerDialog.show();
            }
        });
    }
    private void verifi(){
        adminBaseDatos = new AdminBaseDatos(this);
        if(adminBaseDatos.usu_isExistUsers()){
            CustumerDialogButton custumerDialogButton = new CustumerDialogButton(SettingActivity.this,"USUARIOS","Ya existen datos guardados, desear volver a cargar datos?");
            custumerDialogButton.setOnClickListener(new CustumerDialogButton.LisenerDailog() {
                @Override
                public void OnClickSI() {
                    progressDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            adminBaseDatos.usu_deleteUsers();
                            chargeUser();
                        }
                    }).start();
                }

                @Override
                public void OnClickNO() {

                }
            });
            custumerDialogButton.show();
        }else{
            progressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    chargeUser();
                }
            }).start();
        }
    }
    private Boolean chargeUser(){
        Boolean bRet = false;
        Row rowNum;
        Cell cell;
        try {
            File path = new File(Environment.getExternalStorageDirectory(), "COLABORADORES.xlsx");

            FileInputStream fis = new FileInputStream(path);

            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(0);  // Accede a la primera hoja

            rowNum = sheet.getRow(1);
            cell = rowNum.getCell(1);
            String strnum[] = String.valueOf(cell.getNumericCellValue()).replace(".","-").split("-");
            int num = Integer.valueOf(strnum[0]);
            int count=3;
            String Perfil;

            
            Log.d("DP_DLOG","------------------------------------");
            for (int i=0;i<num;i++){
                User user = new User();
                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                user.setNombre(cell.getStringCellValue());
                Log.d("DP_DLOG","Nombre "+user.getNombre());
                count++;

                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                double numericValue = cell.getNumericCellValue();
                int longValue = (int) numericValue;
                user.setCedula(longValue);
                String cedula = String.valueOf(longValue);
                Log.d("DP_DLOG","cedula "+cedula);
                count++;

                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                user.setCargo(cell.getStringCellValue());
                Log.d("DP_DLOG","cargo "+user.getCargo());
                count++;

                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                Perfil = cell.getStringCellValue();
                int perf;
                if(Perfil.equals(PER_SUPER))
                    user.setPerfil(PERFIL_SUPERADMIN);
                else if(Perfil.equals(PER_MANTE))
                    user.setPerfil(PERFIL_MANTENIMIENTO);
                else if(Perfil.equals(PER_OPER1))
                    user.setPerfil(PERFIL_OPERARIO_1);
                else if(Perfil.equals(PER_OPER2))
                    user.setPerfil(PERFIL_OPERARIO_2);
                else if(Perfil.equals(PER_SUPER_NIVEL1))
                    user.setPerfil(PERFIL_SUPER_NIVEL_1);
                else if(Perfil.equals(PER_SUPER_NIVEL2))
                    user.setPerfil(PERFIL_SUPER_NIVEL_2);
                else if(Perfil.equals(PER_AXILIAR))
                    user.setPerfil(PERFIL_AXILIAR);
                else
                    user.setPerfil(PERFIL_NULL);
                Log.d("DP_DLOG","perfil "+user.getPerfil());

                count++;
                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                user.setFoto(cell.getStringCellValue());
                Log.d("DP_DLOG","Foto "+user.getFoto());


                Log.d("DP_DLOG","------------------------------------");
                count+=3;
                adminBaseDatos.usu_insert(user);
            }
        bRet = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    progressDialog.dismiss();
                }
            });
        }

        showResult(bRet);
        return bRet;
    }

    @Override
    public void OnClickCargar() {
        verifi();
    }

    @Override
    public void OnClickEnrolar() {
        startActivity(new Intent(this,EnrolaUserActivity.class));
    }


    /************************************************************************/
}