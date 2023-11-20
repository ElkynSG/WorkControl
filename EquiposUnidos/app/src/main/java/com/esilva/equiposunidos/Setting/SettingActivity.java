package com.esilva.equiposunidos.Setting;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.esilva.equiposunidos.Dialog.CustumerDialog;
import com.esilva.equiposunidos.Dialog.CustumerDialogButton;
import com.esilva.equiposunidos.Dialog.ProgressDialog;
import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.Equipos;
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
import java.util.List;

import static com.esilva.equiposunidos.util.Constantes.*;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener ,UserFragment.ItemListener,ManteniEquiposFragment.ItemListenerMante{
    private  final String fraUsu = "fragmentUsuario";
    private final String fraItem = "fragmentItems";
    private FragmentTransaction transaction;

    private LinearLayout baseSetting;
    private CardView btChargeUser,btChargeManten,btReport;
    private Fragment fragmentUsuarios, fragMantenimiento, fragReport, fregCurrent;
    private ProgressDialog progressDialog;
    private AdminBaseDatos adminBaseDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);
        setView();
        adminBaseDatos = new AdminBaseDatos(this);
    }

    private void setView() {

        baseSetting = findViewById(R.id.baseSetting);

        btChargeUser = findViewById(R.id.btSettUsuario);
        btChargeUser.setOnClickListener(this);
        btChargeManten = findViewById(R.id.btMantenimiento);
        btChargeManten.setOnClickListener(this);
        btReport = findViewById(R.id.btReport);
        btReport.setOnClickListener(this);

        fragmentUsuarios = new UserFragment();
        fragMantenimiento = new ManteniEquiposFragment();
        fragReport = new ReportFragment(this);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_setting, fragmentUsuarios,fraUsu)
                .commit();
        fregCurrent = fragmentUsuarios;
        progressDialog = new ProgressDialog(this,"Cargando");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btSettUsuario:
                if(fregCurrent != fragmentUsuarios) {
                    baseSetting.setBackgroundColor(getColor(R.color.setting_user));
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_setting,fragmentUsuarios)
                            .commit();
                    transaction.addToBackStack(null);
                    fregCurrent = fragmentUsuarios;
                }
                break;
            case R.id.btMantenimiento:
                if(fregCurrent != fragMantenimiento) {
                    baseSetting.setBackgroundColor(getColor(R.color.setting_manteni));

                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_setting,fragMantenimiento)
                            .commit();
                    transaction.addToBackStack(null);
                    fregCurrent = fragMantenimiento;

                }
                break;
            case R.id.btReport:
                if(fregCurrent != fragReport) {
                    baseSetting.setBackgroundColor(getColor(R.color.setting_report));

                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_setting,fragReport)
                            .commit();
                    transaction.addToBackStack(null);
                    fregCurrent = fragReport;

                }
                break;
            default:
                break;
        }
    }


    /**************************   carga de usuarios   ***********************/

    private void showResult(Boolean isOK,String datos){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CustumerDialog custumerDialog;
                if(isOK)
                    custumerDialog = new CustumerDialog(SettingActivity.this,"SUCCESS!","Datos de "+datos+" cargados exitosamente",false,true);
                else
                    custumerDialog = new CustumerDialog(SettingActivity.this,"FAIL!","Error guardando datos de "+datos,true,true);
                custumerDialog.show();
            }
        });
    }
    private void verifi(){

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

            Sheet sheet = workbook.getSheetAt(SHEET_USUARIOS);  // Accede a la primera hoja

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

        showResult(bRet,"Usuarios");
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

    private Boolean chargeActivities(){
        Boolean bRet = false;
        Row rowNum;
        Cell cell;
        try {
            File path = new File(Environment.getExternalStorageDirectory(), "COLABORADORES.xlsx");

            FileInputStream fis = new FileInputStream(path);

            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(SHEET_ACTIVIDADES);  // Accede a la primera hoja

            rowNum = sheet.getRow(1);
            cell = rowNum.getCell(1);
            String strnum[] = String.valueOf(cell.getNumericCellValue()).replace(".","-").split("-");
            int num = Integer.valueOf(strnum[0]);
            int count=3;
            String Perfil;


            Log.d("DP_DLOG","------------------------------------");
            for (int i=0;i<num;i++){
                String activity;
                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                activity = cell.getStringCellValue();
                Log.d("DP_DLOG","activity "+activity);
                count++;

                Log.d("DP_DLOG","------------------------------------");

                adminBaseDatos.act_insert(activity);
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

        showResult(bRet,"Actividades");
        return bRet;
    }
    @Override
    public void OnClickActividad() {
        if(adminBaseDatos.act_isExistActivities()){
            CustumerDialogButton custumerDialogButton = new CustumerDialogButton(SettingActivity.this,"ACTIVIDADES","Ya existen datos guardados, desear volver a cargar datos?");
            custumerDialogButton.setOnClickListener(new CustumerDialogButton.LisenerDailog() {
                @Override
                public void OnClickSI() {
                    progressDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            adminBaseDatos.act_delete();
                            chargeActivities();
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
                    chargeActivities();
                }
            }).start();
        }
    }

    @Override
    public void OnClickReporte() {

    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        adminBaseDatos.closeBaseDtos();
        super.onDestroy();
    }
    /************************************************************************/

    /**************************   carga de MAQUINARIA   ***********************/
    private Boolean chargeMaquinaria(){
        Boolean bRet = false;
        Row rowNum;
        Cell cell;
        try {
            File path = new File(Environment.getExternalStorageDirectory(), "COLABORADORES.xlsx");

            FileInputStream fis = new FileInputStream(path);

            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(SHEET_EQUIPOS);  // Accede a la primera hoja

            rowNum = sheet.getRow(1);
            cell = rowNum.getCell(1);
            String strnum[] = String.valueOf(cell.getNumericCellValue()).replace(".","-").split("-");
            int num = Integer.valueOf(strnum[0]);
            int count=3;


            Log.d("DP_DLOG","------------------------------------");
            for (int i=0;i<num;i++){
                Equipos equipo = new Equipos();

                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                equipo.setNombre(cell.getStringCellValue());
                Log.d("DP_DLOG","Nombre "+equipo.getNombre());
                count++;

                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                equipo.setNumeroRegistro(cell.getStringCellValue());
                Log.d("DP_DLOG","registro "+equipo.getNumeroRegistro());
                count++;

                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                equipo.setEquipo(cell.getStringCellValue());
                Log.d("DP_DLOG","equipo "+ equipo.getEquipo());
                count++;

                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                equipo.setNumeroMotor(cell.getStringCellValue());
                Log.d("DP_DLOG","numero motor "+equipo.getNumeroMotor());

                count++;
                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                equipo.setNumeroSerie(cell.getStringCellValue());
                Log.d("DP_DLOG","numero serie "+equipo.getNumeroSerie());

                count++;
                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                equipo.setPropietario(cell.getStringCellValue());
                Log.d("DP_DLOG","pripietario "+equipo.getPropietario());

                count++;
                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                equipo.setPlaca(cell.getStringCellValue());
                Log.d("DP_DLOG","placa "+equipo.getPlaca());

                count++;
                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                String strnum2[] = String.valueOf(cell.getNumericCellValue()).replace(".","-").split("-");
                int num2 = Integer.valueOf(strnum2[0]);
                equipo.setTipo(num2);
                Log.d("DP_DLOG","TIPO "+equipo.getTipo());

                count++;
                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                equipo.setFoto(cell.getStringCellValue());
                Log.d("DP_DLOG","Foto "+equipo.getFoto());


                Log.d("DP_DLOG","------------------------------------");
                count+=3;
                adminBaseDatos.equi_insert(equipo);
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

        showResult(bRet,"EQUIPOS");
        return bRet;
    }
    @Override
    public void OnClickMaquinaria() {
        if(adminBaseDatos.equi_isExistEquipos()){
            CustumerDialogButton custumerDialogButton = new CustumerDialogButton(SettingActivity.this,"EQUIPOS","Ya existen datos guardados, desear volver a cargar datos?");
            custumerDialogButton.setOnClickListener(new CustumerDialogButton.LisenerDailog() {
                @Override
                public void OnClickSI() {
                    progressDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            adminBaseDatos.equi_delete();
                            chargeMaquinaria();
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
                    chargeMaquinaria();
                }
            }).start();
        }
    }

    /**************************   carga de MANTENIMIENTO   ***********************/
    private Boolean chargeMantenimiento(){
        Boolean bRet = false;
        Row rowNum;
        Cell cell;
        try {
            File path = new File(Environment.getExternalStorageDirectory(), "COLABORADORES.xlsx");

            FileInputStream fis = new FileInputStream(path);

            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(SHEET_TIPO_MANTENI);  // Accede a la primera hoja

            rowNum = sheet.getRow(1);
            cell = rowNum.getCell(1);
            String strnum[] = String.valueOf(cell.getNumericCellValue()).replace(".","-").split("-");
            int num = Integer.valueOf(strnum[0]);
            int count=3;
            String Perfil;


            Log.d("DP_DLOG","------------------------------------");
            for (int i=0;i<num;i++){
                String activity;
                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                activity = cell.getStringCellValue();
                Log.d("DP_DLOG","activity "+activity);
                count++;

                Log.d("DP_DLOG","------------------------------------");

                adminBaseDatos.mante_insert(activity);
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

        showResult(bRet,"Mantenimientos");
        return bRet;
    }
    @Override
    public void OnClickMantenimiento() {
        if(adminBaseDatos.mante_isExistMante()){
            CustumerDialogButton custumerDialogButton = new CustumerDialogButton(SettingActivity.this,"MANTENIMIENTO","Ya existen datos guardados, desear volver a cargar datos?");
            custumerDialogButton.setOnClickListener(new CustumerDialogButton.LisenerDailog() {
                @Override
                public void OnClickSI() {
                    progressDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            adminBaseDatos.mante_delete();
                            chargeMantenimiento();
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
                    chargeMantenimiento();
                }
            }).start();
        }
    }

    /**************************   carga de LUGARES   ***********************/
    private Boolean chargeLugares(){
        Boolean bRet = false;
        Row rowNum;
        Cell cell;
        try {
            File path = new File(Environment.getExternalStorageDirectory(), "COLABORADORES.xlsx");

            FileInputStream fis = new FileInputStream(path);

            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(SHEET_LUGARES);  // Accede a la primera hoja

            rowNum = sheet.getRow(1);
            cell = rowNum.getCell(1);
            String strnum[] = String.valueOf(cell.getNumericCellValue()).replace(".","-").split("-");
            int num = Integer.valueOf(strnum[0]);
            int count=3;
            String Perfil;


            Log.d("DP_DLOG","------------------------------------");
            for (int i=0;i<num;i++){
                String activity;
                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                activity = cell.getStringCellValue();
                Log.d("DP_DLOG","activity "+activity);
                count++;

                Log.d("DP_DLOG","------------------------------------");

                adminBaseDatos.luga_insert(activity);
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

        showResult(bRet,"Lugares");
        return bRet;
    }
    @Override
    public void OnClickLugares() {
        if(adminBaseDatos.luga_isExistLuga()){
            CustumerDialogButton custumerDialogButton = new CustumerDialogButton(SettingActivity.this,"LUGARES","Ya existen datos guardados, desear volver a cargar datos?");
            custumerDialogButton.setOnClickListener(new CustumerDialogButton.LisenerDailog() {
                @Override
                public void OnClickSI() {
                    progressDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            adminBaseDatos.luga_delete();
                            chargeLugares();
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
                    chargeLugares();
                }
            }).start();
        }
    }

    /**************************   carga de TECNICOS   ***********************/
    private Boolean chargeTecnicos(){
        Boolean bRet = false;
        Row rowNum;
        Cell cell;
        try {
            File path = new File(Environment.getExternalStorageDirectory(), "COLABORADORES.xlsx");

            FileInputStream fis = new FileInputStream(path);

            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(SHEET_TECNICOS);  // Accede a la primera hoja

            rowNum = sheet.getRow(1);
            cell = rowNum.getCell(1);
            String strnum[] = String.valueOf(cell.getNumericCellValue()).replace(".","-").split("-");
            int num = Integer.valueOf(strnum[0]);
            int count=3;
            String Perfil;


            Log.d("DP_DLOG","------------------------------------");
            for (int i=0;i<num;i++){
                String activity;
                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(1);
                activity = cell.getStringCellValue();
                Log.d("DP_DLOG","activity "+activity);
                count++;

                Log.d("DP_DLOG","------------------------------------");

                adminBaseDatos.tec_insert(activity);
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

        showResult(bRet,"Tecnicos");
        return bRet;
    }
    @Override
    public void OnClickTecnicos() {
        if(adminBaseDatos.tec_isExistTec()){
            CustumerDialogButton custumerDialogButton = new CustumerDialogButton(SettingActivity.this,"TECNICOS","Ya existen datos guardados, desear volver a cargar datos?");
            custumerDialogButton.setOnClickListener(new CustumerDialogButton.LisenerDailog() {
                @Override
                public void OnClickSI() {
                    progressDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            adminBaseDatos.tec_delete();
                            chargeTecnicos();
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
                    chargeTecnicos();
                }
            }).start();
        }
    }

    /************************************************************************/


}