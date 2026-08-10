package com.esilva.equiposunidos.Report;

import static com.esilva.equiposunidos.util.Constantes.FILE_REPORT;
import static com.esilva.equiposunidos.util.Constantes.IMAGE_FIRMA;

import android.content.Context;
import android.os.Environment;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.DataManteni;
import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.Historial;
import com.esilva.equiposunidos.db.models.Insumos;
import com.esilva.equiposunidos.db.models.InventarioAceite;
import com.esilva.equiposunidos.db.models.InventarioCaterpila;
import com.esilva.equiposunidos.db.models.InventarioDoorsan;
import com.esilva.equiposunidos.db.models.InventarioKomatsu1;
import com.esilva.equiposunidos.db.models.InventarioKomatsu2;
import com.esilva.equiposunidos.db.models.InventarioMicelanio;
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

public class ReportInventario {
    private File template;
    private Context context;

    private User user;

    private String rutaFile;

    private final String TEMPLATE_INVENTARIO = "template_inventario.xlsx";

    private AdminBaseDatos adminBaseDatos;
    private InventarioMicelanio inventarioMicelanio;
    private InventarioAceite inventarioAceite;
    private InventarioCaterpila inventarioCaterpila;
    private InventarioDoorsan inventarioDoorsan;
    private InventarioKomatsu1 inventarioKomatsu1;
    private InventarioKomatsu2 inventarioKomatsu2;
    private List<Historial> all10_histo;

    public ReportInventario(Context context) {
        this.context = context;

        adminBaseDatos = new AdminBaseDatos(context);
        all10_histo = adminBaseDatos.getAll10_histo();
        inventarioMicelanio = adminBaseDatos.micelanio_get();
        inventarioAceite = adminBaseDatos.aceite_get();
        inventarioCaterpila = adminBaseDatos.cat_get();
        inventarioDoorsan = adminBaseDatos.door_get();
        inventarioKomatsu1 = adminBaseDatos.komat1_get();
        inventarioKomatsu2 = adminBaseDatos.komat2_get();



    }

    public void setUser(User user) {
        this.user = user;
    }


    public boolean buildReport(){
        if(!isExitFileTemplate())
            copyTemplate();
        createFile();
        return createReport();
    }

    private boolean createReport() {
        boolean bRet;
        int conta = 6;
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

            rowNum = sheet.getRow(conta);                   // FILA 7
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu2.getfAceiteMotor());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioDoorsan.getfAceiteMotor());
            cell3 = rowNum.getCell(10);      //F5
            cell3.setCellValue(inventarioAceite.getA15W40());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 8
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu2.getfTrampaCombustible());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioDoorsan.getfCombustible4005());
            cell3 = rowNum.getCell(10);      //F5
            cell3.setCellValue(inventarioAceite.getIso68());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 9
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu2.getfCombustible());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioDoorsan.getfCombustible4004());
            cell3 = rowNum.getCell(10);      //F5
            cell3.setCellValue(inventarioAceite.getTo30());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 10
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu2.getfCabina());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioDoorsan.getfServoTrasmision());
            cell3 = rowNum.getCell(10);      //F5
            cell3.setCellValue(inventarioAceite.getA80W90());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 11
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu2.getfHidraulico());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioDoorsan.getfPiloto());
            cell3 = rowNum.getCell(10);      //F5
            cell3.setCellValue(inventarioAceite.getS527());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 12
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu2.getfRespiradero());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioDoorsan.getfHidrailico());
            cell3 = rowNum.getCell(10);      //F5
            cell3.setCellValue(inventarioAceite.getG_Litio());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 13
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu2.getfPiloto());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioDoorsan.getfAireCabina());
            cell3 = rowNum.getCell(10);      //F5
            cell3.setCellValue(inventarioAceite.getMotor());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 14
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu2.getfAireInterno());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioDoorsan.getfAireInterno());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 15
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu2.getfAireExterno());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioDoorsan.getfAireExterno());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 16
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu2.getfRefregerante());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioDoorsan.getfPrefijo());
            conta+=3;





            rowNum = sheet.getRow(conta);                   // FILA 10
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu1.getfAceiteMotor());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioCaterpila.getfAceiteMotor());
            cell3 = rowNum.getCell(10);      //F5
            cell3.setCellValue(inventarioMicelanio.getAC_R134());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 10
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu1.getfTrampaCombustible());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioCaterpila.getfCombustibleBF13());
            cell3 = rowNum.getCell(10);      //F5
            cell3.setCellValue(inventarioMicelanio.getX70());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 10
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu1.getfCombustible());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioCaterpila.getfCombustibleBF77());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 10
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu1.getfCabina());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioCaterpila.getfServo());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 10
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu1.getfHidraulico());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioCaterpila.getfCabina());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 10
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu1.getfRespiradero());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioCaterpila.getfHidrailicoBT93());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 10
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu1.getfPiloto());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioCaterpila.getfHidrailicoBT83());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 10
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu1.getfAireInterno());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioCaterpila.getfAireInterno());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 10
            cell1 = rowNum.getCell(2);      // C5
            cell1.setCellValue(inventarioKomatsu1.getfAireExterno());
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioCaterpila.getfAireExterno());
            conta++;

            rowNum = sheet.getRow(conta);                   // FILA 10
            cell2 = rowNum.getCell(6);      //F5
            cell2.setCellValue(inventarioCaterpila.getfPrefijo());
            conta++;



            if(all10_histo!= null){
                int countNN=33;
                int countItem=33;
                for (Historial historial:all10_histo) {

                    rowNum = sheet.getRow(countNN);
                    cell1 = rowNum.getCell(0);
                    cell1.setCellValue(historial.getUser());
                    cell2 = rowNum.getCell(1);
                    cell2.setCellValue(historial.getFecha());

                    historial.decodeData();
                    String[] cod = historial.getCod();
                    String[] cant = historial.getCant();
                    int numItems=historial.getNumItems();

                    for(int i=0;i<numItems;i++) {
                        rowNum = sheet.getRow(countNN);
                        cell1 = rowNum.getCell(4);
                        cell1.setCellValue(cod[i]);
                        cell2 = rowNum.getCell(5);
                        cell2.setCellValue(cant[i]);
                        countNN++;
                        if(i>=9)
                            break;
                    }

                    if(numItems>10){
                        countNN = countItem;
                        for(int i=10;i<numItems;i++) {
                            rowNum = sheet.getRow(countNN);
                            cell1 = rowNum.getCell(8);
                            cell1.setCellValue(cod[i]);
                            cell2 = rowNum.getCell(9);
                            cell2.setCellValue(cant[i]);
                            countNN++;
                            if(i>=19)
                                break;
                        }
                    }

                    countItem+=12;
                    countNN = countItem;

                }
            }



            File path2 = new File(Environment.getExternalStorageDirectory(), rutaFile+"/"+getNameFile());

            if(path2.exists()) {
                path2.delete();
            }


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
        rutaFile = FILE_REPORT+"/Inventario";
        File directorio2 = new File(Environment.getExternalStorageDirectory(), rutaFile);
        if (!directorio2.exists()) {
            directorio2.mkdirs();
        }
    }

    private void copyTemplate(){
        File outputFile;
        InputStream inputStream;

        inputStream = context.getResources().openRawResource(R.raw.template_inventario);
        outputFile = new File(context.getFilesDir(), TEMPLATE_INVENTARIO);

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
        template = new File(context.getFilesDir(), TEMPLATE_INVENTARIO);
        if(template.exists()) {
            return true;
        }
        return false;
    }

    private String getNameFile(){
        return "Inventario.xlsx";
    }
}
