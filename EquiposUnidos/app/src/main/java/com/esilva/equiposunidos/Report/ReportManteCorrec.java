package com.esilva.equiposunidos.Report;

import static com.esilva.equiposunidos.util.Constantes.FILE_REPORT;
import static com.esilva.equiposunidos.util.Constantes.IMAGE_FIRMA;

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

public class ReportManteCorrec {
    public static int REPORT_TYPE_CARGADOR=0;
    public static int REPORT_TYPE_EXCAVADORA=1;
    private File template;
    private Context context;

    private User user;
    private Insumos insumos;
    private List<Manteni> manteniList;
    private DataManteni dataManteni;
    private Equipos equipos;

    private int TypeReport;
    private String rutaFile;

    private final String TEMPLATE_CO_CARGA = "template_correc_carga.xlsx";
    private final String TEMPLATE_CO_EXCA = "template_correc_excava.xlsx";

    public ReportManteCorrec(Context context) {
        this.context = context;
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

    public void setDataManteni(DataManteni dataManteni) {
        this.dataManteni = dataManteni;
    }

    public boolean buildReport(int typeMachine){
        TypeReport = typeMachine;
        if(!isExitFileTemplate())
            copyTemplate();
        createFile();
        return createReport();
    }

    private boolean createReport() {
        boolean bRet;
        int conta = 4;
        Row rowNum;
        Cell cell1;
        Cell cell2;
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
            cell2.setCellValue(dataManteni.getFechaHora());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 6
            cell1 = rowNum.getCell(2);      // C6
            if(cell1 != null)
                cell1.setCellValue(equipos.getNumeroMotor());
            cell2 = rowNum.getCell(5);      //F6
            if(cell2 != null)
                cell2.setCellValue(equipos.getPropietario());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 7
            cell1 = rowNum.getCell(2);      // C7
            if(cell1 != null)
                cell1.setCellValue(equipos.getNumeroSerie());
            cell2 = rowNum.getCell(6);      // G7
            if(cell2 != null)
                cell2.setCellValue(dataManteni.getConsecutivo());

            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 8
            cell1 = rowNum.getCell(2);      // C8
            if(cell1 != null)
                cell1.setCellValue(dataManteni.getHorometro());
            cell2 = rowNum.getCell(5);      //F8
            if(cell2 != null)
                cell2.setCellValue(user.getNombre());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 9
            cell1 = rowNum.getCell(2);      // C7
            if(cell1 != null)
                cell1.setCellValue(equipos.getEquipo());
            conta+=2;

            // PROGRAMACION DE MANTENIMIENTO CORRECTIVO
            rowNum = sheet.getRow(conta);                   // FILA 11
            cell1 = rowNum.getCell(6);      // G11
            if(cell1 != null)
                cell1.setCellValue("SI");
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 12
            cell1 = rowNum.getCell(6);      // G12
            if(cell1 != null)
                cell1.setCellValue("SI");
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 13
            cell1 = rowNum.getCell(6);      // G13
            if(cell1 != null)
                cell1.setCellValue("SI");
            conta+=2;

            // DESCRIPCION
            for(Manteni mante:manteniList) {
                rowNum = sheet.getRow(conta);                   // inicia FILA 15
                cell1 = rowNum.getCell(3);      // D15

                if(mante.isbSI())
                    cell1.setCellValue("SI");
                else
                    cell1.setCellValue("NO");

                cell2 = rowNum.getCell(4);
                cell2.setCellValue(mante.getComentario());

                conta += 5;
            }

            // insumos
            conta = 75;

            if(!insumos.getAceite_1().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getAceite_1());
                cell1 = rowNum.getCell(4);      // E76
                cell1.setCellValue("Cantidad: " + insumos.getCant_aceite_1());
            }
            conta++;

            if(!insumos.getAceite_2().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getAceite_2());
                cell1 = rowNum.getCell(4);      // E76
                cell1.setCellValue("Cantidad: " + insumos.getCant_aceite_2());
            }
            conta++;

            if(!insumos.getAceite_3().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getAceite_3());
                cell1 = rowNum.getCell(4);      // E76
                cell1.setCellValue("Cantidad: " + insumos.getCant_aceite_3());
            }
            conta++;

            if(!insumos.getFiltroMotor().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroMotor());
                cell1 = rowNum.getCell(4);      // E76
                cell1.setCellValue("Cantidad: " + insumos.getCant_filtroMotor());
            }
            conta++;

            if(!insumos.getFiltroCombuPri().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroCombuPri());
                cell1 = rowNum.getCell(4);      // E76
                cell1.setCellValue("Cantidad: " + insumos.getCant_filtroCombuPri());
            }
            conta++;

            if(!insumos.getFiltroCombuSeg().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroCombuSeg());
                cell1 = rowNum.getCell(4);      // E76
                cell1.setCellValue("Cantidad: " + insumos.getCant_filtroCombuSeg());
            }
            conta++;

            if(!insumos.getFiltroServoMotor().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroServoMotor());
                cell1 = rowNum.getCell(4);      // E76
                cell1.setCellValue("Cantidad: " + insumos.getCant_filtroServoMotor());
            }
            conta++;

            if(!insumos.getFiltroHidraPri().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroHidraPri());
                cell1 = rowNum.getCell(4);      // E76
                cell1.setCellValue("Cantidad: " + insumos.getCant_filtroHidraPri());
            }
            conta++;

            if(!insumos.getFiltroHidraSeg().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroHidraSeg());
                cell1 = rowNum.getCell(4);      // E76
                cell1.setCellValue("Cantidad: " + insumos.getCant_filtroHidraSeg());
            }
            conta++;

            if(!insumos.getFiltroAC().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getFiltroAC());
                cell1 = rowNum.getCell(4);      // E76
                cell1.setCellValue("Cantidad: " + insumos.getCant_filtroAC());
            }
            conta++;

            if(!insumos.getPreFiltro().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getPreFiltro());
                cell1 = rowNum.getCell(4);      // E76
                cell1.setCellValue("Cantidad: " + insumos.getCant_preFiltro());
            }
            conta++;

            if(!insumos.getDesincrustante().isEmpty()) {
                rowNum = sheet.getRow(conta);                   // FILA 76
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getDesincrustante());
                cell1 = rowNum.getCell(4);      // E76
                cell1.setCellValue("Cantidad: " + insumos.getCant_desincrustante());
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
                cell1 = rowNum.getCell(2);      // C76
                cell1.setCellValue(insumos.getObservaciones());
            }

            InputStream inputStream = new FileInputStream(context.getFilesDir()+"/"+ IMAGE_FIRMA);
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            CreationHelper helper = workbook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();

            anchor.setCol1(4);
            anchor.setRow1(92);
            anchor.setCol2(7);
            anchor.setRow2(95);

            Picture pict = drawing.createPicture(anchor, pictureIdx);


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
            inputStream = context.getResources().openRawResource(R.raw.template_correc_carga);
            outputFile = new File(context.getFilesDir(), TEMPLATE_CO_CARGA);
        }else{
            inputStream = context.getResources().openRawResource(R.raw.template_correc_excava);
            outputFile = new File(context.getFilesDir(), TEMPLATE_CO_EXCA);
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
            template = new File(context.getFilesDir(), TEMPLATE_CO_CARGA);
        else
            template = new File(context.getFilesDir(), TEMPLATE_CO_EXCA);

        if(template.exists()) {
            return true;
        }

        return false;
    }

    private String getNameFile(){
        String st[] = equipos.getNombre().split(" ",-1);
        return "CO_"+Util.getFechaHora()+"_"+equipos.getNombre()+".xlsx";
    }
}
