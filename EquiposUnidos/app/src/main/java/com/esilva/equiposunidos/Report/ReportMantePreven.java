package com.esilva.equiposunidos.Report;

import static com.esilva.equiposunidos.util.Constantes.FILE_REPORT;
import static com.esilva.equiposunidos.util.Constantes.IMAGE_FIRMA;
import static com.esilva.equiposunidos.util.Constantes.IMAGE_MANTE_1;
import static com.esilva.equiposunidos.util.Constantes.IMAGE_MANTE_2;
import static com.esilva.equiposunidos.util.Constantes.IMAGE_MANTE_3;
import static com.esilva.equiposunidos.util.Constantes.IMAGE_MANTE_4;

import android.content.Context;
import android.os.Environment;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.db.models.DataManteni;
import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.Insumos;
import com.esilva.equiposunidos.db.models.Manteni;
import com.esilva.equiposunidos.db.models.User;
import com.esilva.equiposunidos.util.Util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
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

public class ReportMantePreven {
    public static int REPORT_TYPE_CARGADOR=0;
    public static int REPORT_TYPE_EXCAVADORA=1;
    public static int REPORT_TYPE_VOLQUETA=2;
    private File template;
    private Context context;

    private User user;
    private Insumos insumos;
    private List<Manteni> manteniList;
    private DataManteni dataManteni;
    private Equipos equipos;

    private int TypeReport;
    private String rutaFile;

    private final String TEMPLATE_PRE_CARGA = "template_preven_carga.xlsx";
    private final String TEMPLATE_PRE_EXCA =  "template_preven_excava.xlsx";
    private final String TEMPLATE_PRE_VOLQ =  "template_preven_volqueta.xlsx";

    private int tipoMnto=3;
    private String comment1, comment2, comment3, comment4;

    public ReportMantePreven(Context context) {
        this.context = context;
    }

    public void setComments(String comment1, String comment2, String comment3, String comment4){
        this.comment1 = comment1;
        this.comment2 = comment2;
        this.comment3 = comment3;
        this.comment4 = comment4;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEquipos(Equipos equipos) {
        this.equipos = equipos;
    }

    public void setInsumos(Insumos insumos) {
        this.insumos = insumos;
    }

    public void setManteniList(List<Manteni> manteniList) {
        this.manteniList = manteniList;
    }

    public void setDataManteniOtros(DataManteni dataManteni) {
        String[] stringArray = context.getResources().getStringArray(R.array.tipo_mantenimiento_otro);
        if(dataManteni.getTipoManteni().equals(stringArray[0]))
            tipoMnto = 3;
        else if(dataManteni.getTipoManteni().equals(stringArray[1]))
            tipoMnto = 4;
        else if(dataManteni.getTipoManteni().equals(stringArray[2]))
            tipoMnto = 5;
        else if(dataManteni.getTipoManteni().equals(stringArray[3]))
            tipoMnto = 6;
        else
            tipoMnto = 7;

        this.dataManteni = dataManteni;
    }

    public void setDataManteniVolq(DataManteni dataManteni) {
        String[] stringArray = context.getResources().getStringArray(R.array.tipo_mantenimiento_vol);
        if(dataManteni.getTipoManteni().equals(stringArray[0]))
            tipoMnto = 3;
        else if(dataManteni.getTipoManteni().equals(stringArray[1]))
            tipoMnto = 4;
        else
            tipoMnto = 5;

        this.dataManteni = dataManteni;
    }

    public boolean buildReport(int typeMachine){
        TypeReport = typeMachine;
        if(!isExitFileTemplate())
            copyTemplate();
        createFile();

        if(TypeReport==REPORT_TYPE_VOLQUETA){
            return createReportVol();
        }else{
            return createReport();
        }
    }

    private boolean createReport() {
        boolean bRet;
        int conta = 4;
        Row rowNum;
        Cell cell1;
        Cell cell2;
        Cell cell3;
        FileInputStream fis = null;
        try {


            fis = new FileInputStream(template);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            // datos basicos

            rowNum = sheet.getRow(conta);                   // FILA 5
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(equipos.getNumeroRegistro());
            cell2 = rowNum.getCell(7);      //F5
            cell2.setCellValue(equipos.getEquipo());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 6
            cell1 = rowNum.getCell(2);      // C6
            if(cell1 != null)
                cell1.setCellValue(equipos.getNumeroMotor());
            cell2 = rowNum.getCell(7);      //F6
            if(cell2 != null)
                cell2.setCellValue(dataManteni.getFechaHora());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 7
            cell1 = rowNum.getCell(2);      // C7
            cell1.setCellValue(equipos.getNumeroSerie());
            cell2 = rowNum.getCell(7);      // G7
            cell2.setCellValue(equipos.getPropietario());
            cell3 = rowNum.getCell(9);      // G7
            cell3.setCellValue(dataManteni.getConsecutivo());

            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 8
            cell1 = rowNum.getCell(2);      // C8
            cell1.setCellValue(dataManteni.getHorometro());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 9
            cell1 = rowNum.getCell(2);      // C7
            cell1.setCellValue(dataManteni.getHorometroProx());
            cell2 = rowNum.getCell(7);      // G7
            cell2.setCellValue(user.getNombre());
            conta+=3;



            // PROGRAMACION DE MANTENIMIENTO CORRECTIVO
            rowNum = sheet.getRow(conta);                   // FILA 11
            cell1 = rowNum.getCell(tipoMnto);      // G11
            cell1.setCellValue("SI");
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 12
            cell1 = rowNum.getCell(tipoMnto);      // G12
            cell1.setCellValue("SI");
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 13
            cell1 = rowNum.getCell(tipoMnto);      // G13
            cell1.setCellValue("SI");
            conta++;



            for(Manteni mto:manteniList){
                rowNum = sheet.getRow(conta);                   // FILA 13
                cell1 = rowNum.getCell(tipoMnto);
                if(!mto.isbTitle()) {
                    if (mto.isbSI())
                        cell1.setCellValue("SI");
                    else if(mto.isbNO())
                        cell1.setCellValue("NO");
                    else
                        cell1.setCellValue("NA");
                }
                conta++;
            }
            conta+=2;



            // insumos

            if(!insumos.getCant_aceite_1().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getAceite_1());
                cell1 = rowNum.getCell(8);      // E76
                cell1.setCellValue(insumos.getCant_aceite_1());
            }
            conta++;

            if(!insumos.getCant_aceite_2().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getAceite_2());
                cell1 = rowNum.getCell(8);      // E76
                cell1.setCellValue(insumos.getCant_aceite_2());
            }
            conta++;

            if(!insumos.getCant_aceite_3().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getAceite_3());
                cell1 = rowNum.getCell(8);      // E76
                cell1.setCellValue(insumos.getCant_aceite_3());
            }
            conta++;

            if(!insumos.getCant_filtroMotor().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroMotor());
                cell1 = rowNum.getCell(8);      // E76
                cell1.setCellValue(insumos.getCant_filtroMotor());
            }
            conta++;

            if(!insumos.getCant_filtroCombuPri().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroCombuPri());
                cell1 = rowNum.getCell(8);      // E76
                cell1.setCellValue(insumos.getCant_filtroCombuPri());
            }
            conta++;

            if(!insumos.getCant_filtroCombuSeg().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroCombuSeg());
                cell1 = rowNum.getCell(8);      // E76
                cell1.setCellValue(insumos.getCant_filtroCombuSeg());
            }
            conta++;

            if(!insumos.getCant_filtroServoMotor().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroServoMotor());
                cell1 = rowNum.getCell(8);      // E76
                cell1.setCellValue(insumos.getCant_filtroServoMotor());
            }
            conta++;

            if(!insumos.getCant_filtroHidraPri().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroHidraPri());
                cell1 = rowNum.getCell(8);      // E76
                cell1.setCellValue(insumos.getCant_filtroHidraPri());
            }
            conta++;

            if(!insumos.getCant_filtroHidraSeg().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroHidraSeg());
                cell1 = rowNum.getCell(8);      // E76
                cell1.setCellValue(insumos.getCant_filtroHidraSeg());
            }
            conta++;

            if(!insumos.getCant_filtroAC().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroAC());
                cell1 = rowNum.getCell(8);      // E76
                cell1.setCellValue(insumos.getCant_filtroAC());
            }
            conta++;

            if(!insumos.getCant_preFiltro().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getPreFiltro());
                cell1 = rowNum.getCell(8);      // E76
                cell1.setCellValue(insumos.getCant_preFiltro());
            }
            conta++;

            if(!insumos.getCant_desincrustante().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getDesincrustante());
                cell1 = rowNum.getCell(8);      // E76
                cell1.setCellValue(insumos.getCant_desincrustante());
            }
            conta++;

            if(!insumos.getRepuestos().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getRepuestos());
            }
            conta++;

            if(!insumos.getOtros().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getOtros());
            }
            conta+=2;

            if(!insumos.getObservaciones().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(1);      // C76
                cell1.setCellValue(insumos.getObservaciones());
            }

            conta+=3;
            InputStream inputStream = new FileInputStream(context.getFilesDir()+"/"+ IMAGE_MANTE_1);
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            CreationHelper helper = workbook.getCreationHelper();
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();

            anchor.setCol1(1);
            anchor.setRow1(conta);
            anchor.setCol2(2);
            anchor.setRow2(conta+1);

            drawing.createPicture(anchor, pictureIdx);


            inputStream = new FileInputStream(context.getFilesDir()+"/"+ IMAGE_MANTE_2);
            imageBytes = IOUtils.toByteArray(inputStream);
            pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            helper = workbook.getCreationHelper();
            drawing = sheet.createDrawingPatriarch();
            anchor = helper.createClientAnchor();

            anchor.setCol1(2);
            anchor.setRow1(conta);
            anchor.setCol2(3);
            anchor.setRow2(conta+1);

            drawing.createPicture(anchor, pictureIdx);

            inputStream = new FileInputStream(context.getFilesDir()+"/"+ IMAGE_MANTE_3);
            imageBytes = IOUtils.toByteArray(inputStream);
            pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            helper = workbook.getCreationHelper();
            drawing = sheet.createDrawingPatriarch();
            anchor = helper.createClientAnchor();

            anchor.setCol1(3);
            anchor.setRow1(conta);
            anchor.setCol2(8);
            anchor.setRow2(conta+1);

            drawing.createPicture(anchor, pictureIdx);

            inputStream = new FileInputStream(context.getFilesDir()+"/"+ IMAGE_MANTE_4);
            imageBytes = IOUtils.toByteArray(inputStream);
            pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            helper = workbook.getCreationHelper();
            drawing = sheet.createDrawingPatriarch();
            anchor = helper.createClientAnchor();

            anchor.setCol1(8);
            anchor.setRow1(conta);
            anchor.setCol2(10);
            anchor.setRow2(conta+1);

            drawing.createPicture(anchor, pictureIdx);

            conta++;
            rowNum = sheet.getRow(conta);
            cell1 = rowNum.getCell(1);
            cell1.setCellValue(comment1);

            cell1 = rowNum.getCell(2);
            cell1.setCellValue(comment2);

            cell1 = rowNum.getCell(3);
            cell1.setCellValue(comment3);

            cell1 = rowNum.getCell(8);
            cell1.setCellValue(comment4);


            conta+=2;

            inputStream = new FileInputStream(context.getFilesDir()+"/"+ IMAGE_FIRMA);
            imageBytes = IOUtils.toByteArray(inputStream);
            pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            helper = workbook.getCreationHelper();
            drawing = sheet.createDrawingPatriarch();
            anchor = helper.createClientAnchor();

            anchor.setCol1(4);
            anchor.setRow1(conta);
            anchor.setCol2(9);
            anchor.setRow2(conta+2);

            drawing.createPicture(anchor, pictureIdx);

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

    private boolean createReportVol() {
        boolean bRet;
        int conta = 4;
        Row rowNum;
        Cell cell1;
        Cell cell2;
        Cell cell3;
        FileInputStream fis = null;
        try {


            fis = new FileInputStream(template);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            // datos basicos

            rowNum = sheet.getRow(conta);                   // FILA 5
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(equipos.getNumeroRegistro());
            cell2 = rowNum.getCell(5);      //F5
            cell2.setCellValue(equipos.getEquipo());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 6
            cell1 = rowNum.getCell(2);      // C6
            if(cell1 != null)
                cell1.setCellValue(equipos.getNumeroMotor());
            cell2 = rowNum.getCell(5);      //F6
            if(cell2 != null)
                cell2.setCellValue(dataManteni.getFechaHora());

            //

            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 7
            cell1 = rowNum.getCell(2);      // C7
            cell1.setCellValue(equipos.getNumeroSerie());
            cell2 = rowNum.getCell(5);      // G7
            cell2.setCellValue(equipos.getPropietario());

            cell3 = rowNum.getCell(7);      // G7
            cell3.setCellValue(dataManteni.getConsecutivo());


            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 8
            cell1 = rowNum.getCell(2);      // C8
            cell1.setCellValue(dataManteni.getHorometro());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 9
            cell1 = rowNum.getCell(2);      // C7
            cell1.setCellValue(dataManteni.getHorometroProx());
            cell2 = rowNum.getCell(5);      // G7
            cell2.setCellValue(user.getNombre());
            conta+=3;



            // PROGRAMACION DE MANTENIMIENTO CORRECTIVO
            rowNum = sheet.getRow(conta);                   // FILA 11
            cell1 = rowNum.getCell(tipoMnto);      // G11
            cell1.setCellValue("SI");
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 12
            cell1 = rowNum.getCell(tipoMnto);      // G12
            cell1.setCellValue("SI");
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 13
            cell1 = rowNum.getCell(tipoMnto);      // G13
            cell1.setCellValue("SI");
            conta++;



            for(Manteni mto:manteniList){
                rowNum = sheet.getRow(conta);                   // FILA 13
                cell1 = rowNum.getCell(tipoMnto);
                if(!mto.isbTitle()) {
                    if (mto.isbSI())
                        cell1.setCellValue("SI");
                    else if(mto.isbNO())
                        cell1.setCellValue("NO");
                    else
                        cell1.setCellValue("NA");
                }
                conta++;
            }
            conta+=2;



            // insumos

            if(!insumos.getCant_aceite_1().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getAceite_1());
                cell1 = rowNum.getCell(6);      // E76
                cell1.setCellValue(insumos.getCant_aceite_1());
            }
            conta++;

            if(!insumos.getCant_aceite_2().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getAceite_2());
                cell1 = rowNum.getCell(6);      // E76
                cell1.setCellValue(insumos.getCant_aceite_2());
            }
            conta++;

            if(!insumos.getCant_aceite_3().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getAceite_3());
                cell1 = rowNum.getCell(6);      // E76
                cell1.setCellValue(insumos.getCant_aceite_3());
            }
            conta++;

            if(!insumos.getCant_filtroMotor().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroMotor());
                cell1 = rowNum.getCell(6);      // E76
                cell1.setCellValue(insumos.getCant_filtroMotor());
            }
            conta++;

            if(!insumos.getCant_filtroCombuPri().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroCombuPri());
                cell1 = rowNum.getCell(6);      // E76
                cell1.setCellValue(insumos.getCant_filtroCombuPri());
            }
            conta++;

            if(!insumos.getCant_filtroCombuSeg().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroCombuSeg());
                cell1 = rowNum.getCell(6);      // E76
                cell1.setCellValue(insumos.getCant_filtroCombuSeg());
            }
            conta++;

            if(!insumos.getCant_filtroServoMotor().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroServoMotor());
                cell1 = rowNum.getCell(6);      // E76
                cell1.setCellValue(insumos.getCant_filtroServoMotor());
            }
            conta++;

            if(!insumos.getCant_filtroHidraPri().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroHidraPri());
                cell1 = rowNum.getCell(6);      // E76
                cell1.setCellValue(insumos.getCant_filtroHidraPri());
            }
            conta++;

            if(!insumos.getCant_filtroHidraSeg().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroHidraSeg());
                cell1 = rowNum.getCell(6);      // E76
                cell1.setCellValue(insumos.getCant_filtroHidraSeg());
            }
            conta++;

            if(!insumos.getCant_filtroAC().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroAC());
                cell1 = rowNum.getCell(6);      // E76
                cell1.setCellValue(insumos.getCant_filtroAC());
            }
            conta++;

            if(!insumos.getCant_preFiltro().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getPreFiltro());
                cell1 = rowNum.getCell(6);      // E76
                cell1.setCellValue(insumos.getCant_preFiltro());
            }
            conta++;

            if(!insumos.getCant_desincrustante().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getDesincrustante());
                cell1 = rowNum.getCell(6);      // E76
                cell1.setCellValue(insumos.getCant_desincrustante());
            }
            conta++;

            if(!insumos.getRepuestos().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getRepuestos());
            }
            conta++;

            if(!insumos.getOtros().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getOtros());
            }
            conta+=2;

            if(!insumos.getObservaciones().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(1);      // C76
                cell1.setCellValue(insumos.getObservaciones());
            }
            conta+=3;


            InputStream inputStream = new FileInputStream(context.getFilesDir()+"/"+ IMAGE_MANTE_1);
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            CreationHelper helper = workbook.getCreationHelper();
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();

            anchor.setCol1(1);
            anchor.setRow1(conta);
            anchor.setCol2(2);
            anchor.setRow2(conta+1);

            drawing.createPicture(anchor, pictureIdx);


            inputStream = new FileInputStream(context.getFilesDir()+"/"+ IMAGE_MANTE_2);
            imageBytes = IOUtils.toByteArray(inputStream);
            pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            helper = workbook.getCreationHelper();
            drawing = sheet.createDrawingPatriarch();
            anchor = helper.createClientAnchor();

            anchor.setCol1(2);
            anchor.setRow1(conta);
            anchor.setCol2(3);
            anchor.setRow2(conta+1);

            drawing.createPicture(anchor, pictureIdx);

            inputStream = new FileInputStream(context.getFilesDir()+"/"+ IMAGE_MANTE_3);
            imageBytes = IOUtils.toByteArray(inputStream);
            pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            helper = workbook.getCreationHelper();
            drawing = sheet.createDrawingPatriarch();
            anchor = helper.createClientAnchor();

            anchor.setCol1(3);
            anchor.setRow1(conta);
            anchor.setCol2(6);
            anchor.setRow2(conta+1);

            drawing.createPicture(anchor, pictureIdx);

            inputStream = new FileInputStream(context.getFilesDir()+"/"+ IMAGE_MANTE_4);
            imageBytes = IOUtils.toByteArray(inputStream);
            pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            helper = workbook.getCreationHelper();
            drawing = sheet.createDrawingPatriarch();
            anchor = helper.createClientAnchor();

            anchor.setCol1(6);
            anchor.setRow1(conta);
            anchor.setCol2(8);
            anchor.setRow2(conta+1);

            drawing.createPicture(anchor, pictureIdx);

            conta++;
            rowNum = sheet.getRow(conta);
            cell1 = rowNum.getCell(1);
            cell1.setCellValue(comment1);

            cell1 = rowNum.getCell(2);
            cell1.setCellValue(comment2);

            cell1 = rowNum.getCell(3);
            cell1.setCellValue(comment3);

            cell1 = rowNum.getCell(6);
            cell1.setCellValue(comment4);

            inputStream = new FileInputStream(context.getFilesDir()+"/"+ IMAGE_FIRMA);
            imageBytes = IOUtils.toByteArray(inputStream);
            pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            helper = workbook.getCreationHelper();
            drawing = sheet.createDrawingPatriarch();
            anchor = helper.createClientAnchor();

            anchor.setCol1(4);
            anchor.setRow1(109);
            anchor.setCol2(9);
            anchor.setRow2(112);

            drawing.createPicture(anchor, pictureIdx);

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

    private void createFile() {
        rutaFile = FILE_REPORT+"/Mantenimiento";
        File directorio2 = new File(Environment.getExternalStorageDirectory(), rutaFile);
        if (!directorio2.exists()) {
            directorio2.mkdirs();
        }
    }

    private void copyTemplate(){
        File outputFile;
        InputStream inputStream;

        if(TypeReport==REPORT_TYPE_CARGADOR){
            inputStream = context.getResources().openRawResource(R.raw.template_preven_carga);
            outputFile = new File(context.getFilesDir(), TEMPLATE_PRE_CARGA);
        }else if(TypeReport==REPORT_TYPE_VOLQUETA){
            inputStream = context.getResources().openRawResource(R.raw.template_preven_volqueta);
            outputFile = new File(context.getFilesDir(), TEMPLATE_PRE_VOLQ);
        }else{
            inputStream = context.getResources().openRawResource(R.raw.template_preven_excava);
            outputFile = new File(context.getFilesDir(), TEMPLATE_PRE_EXCA);
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

    private boolean isExitFileTemplate() {
        if(TypeReport==REPORT_TYPE_CARGADOR)
            template = new File(context.getFilesDir(), TEMPLATE_PRE_CARGA);
        else if(TypeReport==REPORT_TYPE_VOLQUETA)
            template = new File(context.getFilesDir(), TEMPLATE_PRE_VOLQ);
        else
            template = new File(context.getFilesDir(), TEMPLATE_PRE_EXCA);

        if(template.exists()) {
            return true;
        }

        return false;
    }

    private String getNameFile(){
        String st[] = equipos.getNombre().split(" ",-1);
        return "PRE_"+Util.getFechaHora()+"_"+equipos.getNombre()+".xlsx";
    }
}
