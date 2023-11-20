package com.esilva.equiposunidos.Report;

import static com.esilva.equiposunidos.util.Constantes.FILE_REPORT;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.Registros;
import com.esilva.equiposunidos.db.models.User;
import com.esilva.equiposunidos.util.Util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ReportUser {
    public static final int ID_DIA = 2;
    public static final int ID_FECHA = 3;
    public static final int ID_HORA_IN = 5;
    public static final int ID_HORA_OUT = 6;
    public static final int ID_EQUIPO_IN = 7;
    public static final int ID_EQUIPO_OUT = 7;
    public static final int ID_ACTIVIDAD_IN = 8;
    public static final int ID_ACTIVIDAD_OUT = 8;
    public static final int ID_COMENTARIO_IN = 9;
    public static final int ID_COMENTARIO_OUT = 9;
    public static final int ID_COORDENADAS_IN = 10;
    public static final int ID_COORDENADAS_OUT = 10;

    
    private Context context;
    private int cedula;
    private File template;
    private User user;
    private String fechaMes;
    private String rutaFile;
    private int numDia;
    private AdminBaseDatos adminBaseDatos;

    public static final String TEMPLATE_USER = "template_user.xlsx";
    public ReportUser(Context context) {
        this.context = context;
    }

    public boolean generalReportUser(int cedula){
        this.cedula = cedula;
        if(!isExitFileTemplate())
            copyTemplate();

        return createRetportById();
    }

    public boolean buildReportCurrent(){
        adminBaseDatos = new AdminBaseDatos(context);
        if(!isExitFileTemplate())
            copyTemplate();

        createFile();
        fechaMes = Util.getFechaHora().substring(0,7);
        boolean res = createRetportAll();
        if(numDia < 3)
           buildReportOld();
        adminBaseDatos.closeBaseDtos();
        return res;
    }

    public boolean buildReportOld(){
        if(!isExitFileTemplate())
            copyTemplate();

        createFileOld();

        fechaMes = Util.getFechaHoraOld().substring(0,7);

        return createRetportAll();
    }

    private void createFile() {
        String[] fechaHora = Util.getFechaHoraCom();
        rutaFile = FILE_REPORT+"/"+fechaHora[3];
        numDia = Integer.valueOf(fechaHora[2]);
        File directorio2 = new File(Environment.getExternalStorageDirectory(), rutaFile);
        if (!directorio2.exists()) {
            directorio2.mkdirs();
        }
    }

    private void createFileOld() {
        String[] fechaHora = Util.getFechaOldCom();
        rutaFile = FILE_REPORT+"/"+fechaHora[3];
        File directorio2 = new File(Environment.getExternalStorageDirectory(), rutaFile);
        if (!directorio2.exists()) {
            directorio2.mkdirs();
        }
    }

    private boolean isExitFileTemplate(){
        //template = new File(Environment.getExternalStorageDirectory(), "EquiposUnidos/Reportes/template_user.xlsx");
        template = new File(context.getFilesDir(), TEMPLATE_USER);
        if(template.exists())
            return true;

        return false;
    }

    private void copyTemplate(){

        // 1. Leer el archivo de res/raw
        InputStream inputStream = context.getResources().openRawResource(R.raw.template_user);

        // 2. Define el path en el almacenamiento interno donde quieres guardar la plantilla
        File outputFile = new File(context.getFilesDir(), TEMPLATE_USER);

        // 3. Escribe el archivo en el almacenamiento interno
        try {
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean createRetportById(){
        Boolean bRet = false;
        int count=10;
        Row rowNum;
        Row rowNum2;
        Cell cell;
        try {

            AdminBaseDatos adminBaseDatos = new AdminBaseDatos(context);
            List<Registros> registerList = adminBaseDatos.reg_getAllByUser(cedula);
            User user = adminBaseDatos.usu_getByUser(cedula);
            adminBaseDatos.closeBaseDtos();

            if(registerList == null)
                return false;
            FileInputStream fis = new FileInputStream(template);

            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(1);  // Accede a la primera hoja

            rowNum = sheet.getRow(5);
            cell = rowNum.getCell(4);
            cell.setCellValue(user.getNombre());

            rowNum = sheet.getRow(6);
            cell = rowNum.getCell(4);
            cell.setCellValue(user.getCedula());

            rowNum = sheet.getRow(7);
            cell = rowNum.getCell(4);
            cell.setCellValue(user.getCargo());

            Log.d("DP_DLOG","------------------------------------");
            for (int i=0;i<registerList.size();i++){
                Registros register = registerList.get(i);

                rowNum = sheet.getRow(count);
                cell = rowNum.getCell(ID_DIA);
                cell.setCellValue(register.getDia());
                Log.d("DP_DLOG","Dia "+register.getDia());

                String[] fechaIn = register.getFecha_in().split(" ",-1);

                cell = rowNum.getCell(ID_FECHA);
                cell.setCellValue(fechaIn[0]);
                Log.d("DP_DLOG","fecha "+fechaIn[0]);

                cell = rowNum.getCell(ID_HORA_IN);
                cell.setCellValue(fechaIn[1]);
                Log.d("DP_DLOG","fecha "+fechaIn[1]);

                cell = rowNum.getCell(ID_EQUIPO_IN);
                cell.setCellValue(register.getEquipo_in());
                Log.d("DP_DLOG","equipo "+register.getEquipo_in());

                cell = rowNum.getCell(ID_ACTIVIDAD_IN);
                cell.setCellValue(register.getActividad_in());
                Log.d("DP_DLOG","actividad "+register.getActividad_in());

                cell = rowNum.getCell(ID_COMENTARIO_IN);
                cell.setCellValue(register.getComentario_in());
                Log.d("DP_DLOG","comentario "+register.getComentario_in());

                cell = rowNum.getCell(ID_COORDENADAS_IN);
                cell.setCellValue(register.getLatitud_in()+" "+ register.getLongitud_in());
                Log.d("DP_DLOG","Coordenadas "+register.getComentario_in());

                count++;

                if(register.getFecha_out() != null) {
                    rowNum2 = sheet.getRow(count);
                    String[] fechaOut = register.getFecha_out().split(" ", -1);
                    cell = rowNum2.getCell(ID_HORA_OUT);
                    cell.setCellValue(fechaOut[1]);
                    Log.d("DP_DLOG", "fecha " + fechaOut[1]);

                    cell = rowNum2.getCell(ID_EQUIPO_OUT);
                    cell.setCellValue(register.getEquipo_out());
                    Log.d("DP_DLOG","equipo "+register.getEquipo_out());

                    cell = rowNum2.getCell(ID_ACTIVIDAD_OUT);
                    cell.setCellValue(register.getActividad_out());
                    Log.d("DP_DLOG","actividad "+register.getActividad_out());

                    cell = rowNum2.getCell(ID_COMENTARIO_OUT);
                    cell.setCellValue(register.getComentario_out());
                    Log.d("DP_DLOG","comentario "+register.getComentario_out());

                    cell = rowNum2.getCell(ID_COORDENADAS_OUT);
                    cell.setCellValue(register.getLatitud_out()+" "+ register.getLongitud_out());
                    Log.d("DP_DLOG","Coordenadas "+register.getComentario_out());
                }

                count++;
            }

            File path2 = new File(Environment.getExternalStorageDirectory(), rutaFile+"/TEMPLATE_USER.xlsx");
            try {
                FileOutputStream outputStream = new FileOutputStream(path2);
                workbook.write(outputStream);
                outputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
                bRet = false;
            }
            bRet = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            Log.d("DP_DLOG","fin");
        }

        return bRet;
    }

    private boolean createRetportAll(){
        Boolean bRet = false;
        List<Registros> registerList;
        List<User> userList;
        int count=10;
        Row rowNum;
        Row rowNum2;
        Cell cell;
        boolean bWhile;

        try {
            userList = adminBaseDatos.usu_getAll();
            for(int j=0;j<userList.size();j++) {
                count=10;
                user = userList.get(j);
                registerList = adminBaseDatos.reg_getAllByUserMes(fechaMes,user.getCedula());


                if (registerList == null)
                    continue;

                FileInputStream fis = new FileInputStream(template);
                Workbook workbook = new XSSFWorkbook(fis);
                Sheet sheet = workbook.getSheetAt(1);  // Accede a la primera hoja
                workbook.setSheetName(1,user.getNombre());

                rowNum = sheet.getRow(5);
                cell = rowNum.getCell(4);
                cell.setCellValue(user.getNombre());

                rowNum = sheet.getRow(6);
                cell = rowNum.getCell(4);
                cell.setCellValue(user.getCedula());

                rowNum = sheet.getRow(7);
                cell = rowNum.getCell(4);
                cell.setCellValue(user.getCargo());

                Log.d("DP_DLOG", "------------------------------------");
                int contaDay=1;
                Registros register;
                for (int i = 0; i < registerList.size(); i++) {
                    bWhile = true;
                    Registros registerSave = registerList.get(i);

                    while (bWhile) {
                        String dateDay = fechaMes+"-"+String.format("%02d",contaDay);
                        String[] dateSpace = registerSave.getFecha_in().split(" ",-1);
                        if(dateDay.equals(dateSpace[0])) {
                            register = registerSave;
                            bWhile = false;
                        }else
                            register = buildRegister(dateDay);

                        rowNum = sheet.getRow(count);
                        cell = rowNum.getCell(ID_DIA);
                        cell.setCellValue(register.getDia());
                        Log.d("DP_DLOG", "Dia " + register.getDia());

                        String[] fechaIn = register.getFecha_in().split(" ", -1);

                        cell = rowNum.getCell(ID_FECHA);
                        cell.setCellValue(fechaIn[0]);
                        Log.d("DP_DLOG", "fecha " + fechaIn[0]);

                        cell = rowNum.getCell(ID_HORA_IN);
                        cell.setCellValue(fechaIn[1]);
                        Log.d("DP_DLOG", "fecha " + fechaIn[1]);

                        cell = rowNum.getCell(ID_EQUIPO_IN);
                        cell.setCellValue(register.getEquipo_in());
                        Log.d("DP_DLOG", "equipo " + register.getEquipo_in());

                        cell = rowNum.getCell(ID_ACTIVIDAD_IN);
                        cell.setCellValue(register.getActividad_in());
                        Log.d("DP_DLOG", "actividad " + register.getActividad_in());

                        cell = rowNum.getCell(ID_COMENTARIO_IN);
                        cell.setCellValue(register.getComentario_in());
                        Log.d("DP_DLOG", "comentario " + register.getComentario_in());

                        cell = rowNum.getCell(ID_COORDENADAS_IN);
                        cell.setCellValue(register.getLatitud_in() + " " + register.getLongitud_in());
                        Log.d("DP_DLOG", "comentario " + register.getLatitud_in());

                        count++;

                        if (register.getFecha_out() != null) {
                            rowNum2 = sheet.getRow(count);
                            String[] fechaOut = register.getFecha_out().split(" ", -1);
                            cell = rowNum2.getCell(ID_HORA_OUT);
                            cell.setCellValue(fechaOut[1]);
                            Log.d("DP_DLOG", "fecha " + fechaOut[1]);

                            cell = rowNum2.getCell(ID_EQUIPO_OUT);
                            cell.setCellValue(register.getEquipo_out());
                            Log.d("DP_DLOG", "equipo " + register.getEquipo_out());

                            cell = rowNum2.getCell(ID_ACTIVIDAD_OUT);
                            cell.setCellValue(register.getActividad_out());
                            Log.d("DP_DLOG", "actividad " + register.getActividad_out());

                            cell = rowNum2.getCell(ID_COMENTARIO_OUT);
                            cell.setCellValue(register.getComentario_out());
                            Log.d("DP_DLOG", "comentario " + register.getComentario_out());

                            cell = rowNum2.getCell(ID_COORDENADAS_OUT);
                            cell.setCellValue(register.getLatitud_out() + " " + register.getLongitud_out());
                            Log.d("DP_DLOG", "comentario " + register.getLatitud_out());
                        }

                        count++;
                        contaDay++;
                    }
                }

                File path2 = new File(Environment.getExternalStorageDirectory(), rutaFile+"/"+getNameFile());
                try {
                    FileOutputStream outputStream = new FileOutputStream(path2);
                    workbook.write(outputStream);
                    outputStream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    bRet = false;
                }
            }
            bRet = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            Log.d("DP_DLOG","fin");
        }

        return bRet;
    }

    private Registros buildRegister(String date){
        Registros registros = new Registros();
        registros.setDia("-----");
        registros.setFecha_in(date+" 00:00");
        registros.setFecha_out(date+" 00:00");
        registros.setEquipo_in("---");
        registros.setEquipo_out("---");
        registros.setActividad_in("---");
        registros.setActividad_out("---");
        registros.setComentario_in("----");
        registros.setComentario_out("----");
        registros.setLatitud_in("---");
        registros.setLatitud_out("---");
        registros.setLongitud_in("---");
        registros.setLongitud_out("---");

        return registros;
    }
    private String getNameFile(){
        String st[] = user.getNombre().split(" ",-1);
        return fechaMes+"_"+ st[0]+"_"+String.valueOf(user.getCedula())+".xlsx";
    }

}
