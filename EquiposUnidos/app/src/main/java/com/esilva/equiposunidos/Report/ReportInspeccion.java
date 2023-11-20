package com.esilva.equiposunidos.Report;

import static com.esilva.equiposunidos.Report.ReportManteCorrec.REPORT_TYPE_CARGADOR;
import static com.esilva.equiposunidos.Report.ReportManteCorrec.REPORT_TYPE_EXCAVADORA;
import static com.esilva.equiposunidos.util.Constantes.FILE_REPORT;
import static com.esilva.equiposunidos.util.Constantes.IMAGE_FIRMA;

import android.content.Context;
import android.os.Environment;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.application.UnidosApplication;
import com.esilva.equiposunidos.db.models.DataInspeccion;
import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.Manteni;
import com.esilva.equiposunidos.util.Util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ReportInspeccion {
    private final String TEMPLATE_INSP_CARGA = "template_inspec_cargador.xlsx";
    private final String TEMPLATE_INSP_EXCAVA = "template_inspec_excavadora.xlsx";
    private final String TEMPLATE_INSP_VEHIC = "template_inspec_vehiculo.xlsx";

    private final Context context;
    private String operador,cedula,supervisor,horometro,lugar,observacion;
    private Equipos miEquipo;
    private List<DataInspeccion> lisInspeccion;

    private int TypeReport;
    private File template;
    private String rutaFile;

    public ReportInspeccion(Context context) {
        this.context = context;
        miEquipo = UnidosApplication.getEquipo();
        lisInspeccion = UnidosApplication.getListInspeccion();
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public void setHorometro(String horometro) {
        this.horometro = horometro;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean buildReport(){
        TypeReport = miEquipo.getTipo();
        if(!isExitFileTemplate())
            copyTemplate();
        createFile();

        if(TypeReport==REPORT_TYPE_CARGADOR)
            return createReportCarga();
        else if(TypeReport==REPORT_TYPE_EXCAVADORA)
            return createReportExcava();
        else
            return createReportVehiculo();
    }

    private boolean isExitFileTemplate() {
        if(TypeReport==REPORT_TYPE_CARGADOR)
            template = new File(context.getFilesDir(), TEMPLATE_INSP_CARGA);
        else if(TypeReport==REPORT_TYPE_EXCAVADORA)
            template = new File(context.getFilesDir(), TEMPLATE_INSP_EXCAVA);
        else
            template = new File(context.getFilesDir(), TEMPLATE_INSP_VEHIC);

        if(template.exists()) {
            template.delete();
            return false;
        }

        return false;
    }

    private void copyTemplate(){
        File outputFile;
        InputStream inputStream;

        if(TypeReport==REPORT_TYPE_CARGADOR){
            inputStream = context.getResources().openRawResource(R.raw.template_inspec_cargador);
            outputFile = new File(context.getFilesDir(), TEMPLATE_INSP_CARGA);
        }else if(TypeReport==REPORT_TYPE_EXCAVADORA){
            inputStream = context.getResources().openRawResource(R.raw.template_inspec_excavadora);
            outputFile = new File(context.getFilesDir(), TEMPLATE_INSP_EXCAVA);
        }else{
            inputStream = context.getResources().openRawResource(R.raw.template_inspec_vehiculo);
            outputFile = new File(context.getFilesDir(), TEMPLATE_INSP_VEHIC);
        }

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

    private void createFile() {
        rutaFile = FILE_REPORT+"/Inspeccion";
        File directorio2 = new File(Environment.getExternalStorageDirectory(), rutaFile);
        if (!directorio2.exists()) {
            directorio2.mkdirs();
        }
    }

    private boolean createReportCarga() {
        boolean bRet;
        int conta = 4;
        Row rowNum;
        Cell cellB1;
        Cell cellM1;
        Cell cellPare1;
        Cell cellB2;
        Cell cellM2;
        Cell cellPare2;
        Cell cellB3;
        Cell cellM3;
        Cell cellPare3;
        FileInputStream fis = null;

        CellStyle styleRojo;
        CellStyle styleVerde;
        CellStyle styleNaranja;
        String[] fechaHora = Util.getFechaHora().split(" ");
        try {

            fis = new FileInputStream(template);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            Font font = workbook.createFont();
            font.setColor(IndexedColors.WHITE.getIndex());  // Color blanco

            styleRojo = workbook.createCellStyle();
            styleRojo.setFillForegroundColor(IndexedColors.RED.getIndex());  // Puedes cambiar a otro color
            styleRojo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleRojo.setFont(font);

            styleVerde = workbook.createCellStyle();
            styleVerde.setFillForegroundColor(IndexedColors.GREEN.getIndex());  // Puedes cambiar a otro color
            styleVerde.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleVerde.setFont(font);

            styleNaranja = workbook.createCellStyle();
            styleNaranja.setFillForegroundColor(IndexedColors.ORANGE.getIndex());  // Puedes cambiar a otro color
            styleNaranja.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleNaranja.setFont(font);

            // datos basicos

            rowNum = sheet.getRow(conta);                   // FILA 5
            cellB1 = rowNum.getCell(3);      // D5
            cellB1.setCellValue(miEquipo.getEquipo());
            cellB2 = rowNum.getCell(12);      //F5
            cellB2.setCellValue(fechaHora[0]);
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 6
            cellB1 = rowNum.getCell(3);      // D6
            cellB1.setCellValue(miEquipo.getNumeroSerie());
            cellB2 = rowNum.getCell(12);      //F6
            cellB2.setCellValue(lugar);
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 7
            cellB1 = rowNum.getCell(3);      // C7
            cellB1.setCellValue(horometro);
            cellB2 = rowNum.getCell(12);      // G7
            cellB2.setCellValue(fechaHora[1]);
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 8
            cellB1 = rowNum.getCell(3);      // C8
            cellB1.setCellValue(miEquipo.getPropietario());
            cellB2 = rowNum.getCell(12);      //F8
            cellB2.setCellValue("");
            conta++;

            conta+=4;

            int colum = 0;
            int[] columnas = new int[]{3,9,13};
            conta = 12;

            // DESCRIPCION
            for(DataInspeccion inspec:lisInspeccion) {
                rowNum = sheet.getRow(conta);                   // inicia FILA 12
                if(inspec.isPosB()) {
                    cellB1 = rowNum.getCell(columnas[colum]);
                    cellB1.setCellValue("X");
                    if(inspec.isCheckRojo()){
                        cellPare1 = rowNum.getCell(columnas[colum]+2);
                        cellPare1.setCellValue("OK");
                        cellPare1.setCellStyle(styleVerde);
                    }
                }else {
                    cellM1 = rowNum.getCell(columnas[colum]+1);
                    cellM1.setCellValue("X");
                    cellPare1 = rowNum.getCell(columnas[colum]+2);
                    if(inspec.isCheckRojo()){
                        cellPare1.setCellValue("PARE");
                        cellPare1.setCellStyle(styleRojo);
                    }else{
                        cellPare1.setCellValue("OBS");
                        cellPare1.setCellStyle(styleNaranja);
                    }
                }


                conta ++;
                if(conta>25) {
                    conta = 12;
                    colum++;
                }
            }

            rowNum = sheet.getRow(28);                   // FILA 8
            cellB1 = rowNum.getCell(2);      // C8
            cellB1.setCellValue(operador);
            cellB2 = rowNum.getCell(12);      //F8
            cellB2.setCellValue(supervisor);

            rowNum = sheet.getRow(31);                   // FILA 8
            cellB1 = rowNum.getCell(0);      // C8
            cellB1.setCellValue(observacion);




            File path2 = new File(Environment.getExternalStorageDirectory(), rutaFile+"/"+getNameFile());
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
            bRet = false;
        } catch (IOException e) {
            e.printStackTrace();
            bRet = false;
        }
        return bRet;
    }

    private boolean createReportExcava() {
        boolean bRet;
        int conta = 4;
        Row rowNum;
        Cell cellB1;
        Cell cellM1;
        Cell cellPare1;
        Cell cellB2;
        Cell cellM2;
        Cell cellPare2;
        Cell cellB3;
        Cell cellM3;
        Cell cellPare3;
        FileInputStream fis = null;

        CellStyle styleRojo;
        CellStyle styleVerde;
        CellStyle styleNaranja;
        String[] fechaHora = Util.getFechaHora().split(" ");
        try {

            fis = new FileInputStream(template);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            Font font = workbook.createFont();
            font.setColor(IndexedColors.WHITE.getIndex());  // Color blanco

            styleRojo = workbook.createCellStyle();
            styleRojo.setFillForegroundColor(IndexedColors.RED.getIndex());  // Puedes cambiar a otro color
            styleRojo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleRojo.setFont(font);

            styleVerde = workbook.createCellStyle();
            styleVerde.setFillForegroundColor(IndexedColors.GREEN.getIndex());  // Puedes cambiar a otro color
            styleVerde.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleVerde.setFont(font);

            styleNaranja = workbook.createCellStyle();
            styleNaranja.setFillForegroundColor(IndexedColors.ORANGE.getIndex());  // Puedes cambiar a otro color
            styleNaranja.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleNaranja.setFont(font);

            // datos basicos

            rowNum = sheet.getRow(conta);                   // FILA 5
            cellB1 = rowNum.getCell(3);      // D5
            cellB1.setCellValue(miEquipo.getEquipo());
            cellB2 = rowNum.getCell(12);      //F5
            cellB2.setCellValue(fechaHora[0]);
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 6
            cellB1 = rowNum.getCell(3);      // D6
            cellB1.setCellValue(miEquipo.getNumeroSerie());
            cellB2 = rowNum.getCell(12);      //F6
            cellB2.setCellValue(lugar);
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 7
            cellB1 = rowNum.getCell(3);      // C7
            cellB1.setCellValue(horometro);
            cellB2 = rowNum.getCell(12);      // G7
            cellB2.setCellValue(fechaHora[1]);
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 8
            cellB1 = rowNum.getCell(3);      // C8
            cellB1.setCellValue(miEquipo.getPropietario());
            cellB2 = rowNum.getCell(12);      //F8
            cellB2.setCellValue("");

            int colum = 0;
            int valida = 24;
            int[] columnas = new int[]{3,9,13};
            conta = 12;

            // DESCRIPCION
            for(DataInspeccion inspec:lisInspeccion) {
                rowNum = sheet.getRow(conta);                   // inicia FILA 12
                if(inspec.isPosB()) {
                    cellB1 = rowNum.getCell(columnas[colum]);
                    cellB1.setCellValue("X");
                    if(inspec.isCheckRojo()){
                        cellPare1 = rowNum.getCell(columnas[colum]+2);
                        cellPare1.setCellValue("OK");
                        cellPare1.setCellStyle(styleVerde);
                    }
                }else {
                    cellM1 = rowNum.getCell(columnas[colum]+1);
                    cellM1.setCellValue("X");
                    cellPare1 = rowNum.getCell(columnas[colum]+2);
                    if(inspec.isCheckRojo()){
                        cellPare1.setCellValue("PARE");
                        cellPare1.setCellStyle(styleRojo);
                    }else{
                        cellPare1.setCellValue("OBS");
                        cellPare1.setCellStyle(styleNaranja);
                    }
                }


                conta ++;
                if(conta>valida) {
                    conta = 12;
                    colum++;
                    valida=23;
                }
            }

            rowNum = sheet.getRow(26);                   // FILA 8
            cellB1 = rowNum.getCell(2);      // C8
            cellB1.setCellValue(operador);
            cellB2 = rowNum.getCell(12);      //F8
            cellB2.setCellValue(supervisor);

            rowNum = sheet.getRow(29);                   // FILA 8
            cellB1 = rowNum.getCell(0);      // C8
            cellB1.setCellValue(observacion);



            File path2 = new File(Environment.getExternalStorageDirectory(), rutaFile+"/"+getNameFile());
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
            bRet = false;
        } catch (IOException e) {
            e.printStackTrace();
            bRet = false;
        }
        return bRet;
    }

    private boolean createReportVehiculo(){
        boolean bRet;
        int conta = 4;
        Row rowNum;
        Cell cellB1;
        Cell cellM1;
        Cell cellPare1;
        Cell cellB2;

        FileInputStream fis = null;

        CellStyle styleRojo;
        CellStyle styleVerde;
        CellStyle styleNaranja;

        try {

            fis = new FileInputStream(template);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            Font font = workbook.createFont();
            font.setColor(IndexedColors.WHITE.getIndex());  // Color blanco

            styleRojo = workbook.createCellStyle();
            styleRojo.setFillForegroundColor(IndexedColors.RED.getIndex());  // Puedes cambiar a otro color
            styleRojo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleRojo.setFont(font);

            styleVerde = workbook.createCellStyle();
            styleVerde.setFillForegroundColor(IndexedColors.GREEN.getIndex());  // Puedes cambiar a otro color
            styleVerde.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleVerde.setFont(font);

            styleNaranja = workbook.createCellStyle();
            styleNaranja.setFillForegroundColor(IndexedColors.ORANGE.getIndex());  // Puedes cambiar a otro color
            styleNaranja.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleNaranja.setFont(font);

            // datos basicos

            rowNum = sheet.getRow(conta);                   // FILA 5
            cellB1 = rowNum.getCell(3);      // D5
            cellB1.setCellValue(miEquipo.getEquipo());
            cellB2 = rowNum.getCell(12);      //F5
            cellB2.setCellValue(miEquipo.getPropietario());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 6
            cellB1 = rowNum.getCell(3);      // D6
            cellB1.setCellValue(miEquipo.getPlaca());
            cellB2 = rowNum.getCell(12);      //F6
            cellB2.setCellValue(Util.getFechaHora());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 7
            cellB1 = rowNum.getCell(3);      // C7
            cellB1.setCellValue(miEquipo.getNumeroRegistro());
            cellB2 = rowNum.getCell(12);      // G7
            cellB2.setCellValue(lugar);
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 8
            cellB1 = rowNum.getCell(3);      // C8
            cellB1.setCellValue(horometro);


            int colum = 0;
            int[] columnas = new int[]{3,9,13};
            conta = 12;

            // DESCRIPCION
            for(DataInspeccion inspec:lisInspeccion) {
                rowNum = sheet.getRow(conta);                   // inicia FILA 12
                if(inspec.isPosB()) {
                    cellB1 = rowNum.getCell(columnas[colum]);
                    cellB1.setCellValue("X");
                    if(inspec.isCheckRojo()){
                        cellPare1 = rowNum.getCell(columnas[colum]+2);
                        cellPare1.setCellValue("OK");
                        cellPare1.setCellStyle(styleVerde);
                    }
                }else {
                    cellM1 = rowNum.getCell(columnas[colum]+1);
                    cellM1.setCellValue("X");
                    cellPare1 = rowNum.getCell(columnas[colum]+2);
                    if(inspec.isCheckRojo()){
                        cellPare1.setCellValue("PARE");
                        cellPare1.setCellStyle(styleRojo);
                    }else{
                        cellPare1.setCellValue("OBS");
                        cellPare1.setCellStyle(styleNaranja);
                    }
                }


                conta ++;
                if(conta>24) {
                    conta = 12;
                    colum++;
                }
            }

            rowNum = sheet.getRow(27);                   // FILA 8
            cellB1 = rowNum.getCell(2);      // C8
            cellB1.setCellValue(operador);
            cellB2 = rowNum.getCell(9);      //F8
            cellB2.setCellValue(supervisor);

            rowNum = sheet.getRow(28);                   // FILA 8
            cellB1 = rowNum.getCell(2);      // C8
            cellB1.setCellValue(cedula);

            rowNum = sheet.getRow(30);                   // FILA 8
            cellB1 = rowNum.getCell(0);      // C8
            cellB1.setCellValue(observacion);


            File path2 = new File(Environment.getExternalStorageDirectory(), rutaFile+"/"+getNameFile());
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
            bRet = false;
        } catch (IOException e) {
            e.printStackTrace();
            bRet = false;
        }
        return bRet;
    }

    private String getNameFile(){
        return "INSPEC_"+Util.getFechaHora()+"_"+miEquipo.getNombre()+".xlsx";
    }
}
