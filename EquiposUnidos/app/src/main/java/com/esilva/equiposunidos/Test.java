package com.esilva.equiposunidos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Test extends AppCompatActivity {
    private Button inicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        inicio = findViewById(R.id.inicio);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inicio.setEnabled(false);
                            }
                        });

                        leerPlantilla();
                    }
                }).start();
            }
        });


    }

    private void leerPlantilla(){
        try {


            File path = new File(Environment.getExternalStorageDirectory(), "ControlAcceso.xlsx");
            FileInputStream fis = new FileInputStream(path);

            Workbook workbook = new XSSFWorkbook(fis);

            CellStyle borderStyle = workbook.createCellStyle();
            borderStyle.setBorderTop(BorderStyle.THIN);
            borderStyle.setBorderBottom(BorderStyle.THIN);
            borderStyle.setBorderLeft(BorderStyle.THIN);
            borderStyle.setBorderRight(BorderStyle.THIN);


            int numberOfSheets = workbook.getNumberOfSheets();
            Log.d("DP_DLOG","leerPlantilla "+"nuSheet "+numberOfSheets);


            Sheet sheet = workbook.getSheetAt(1);  // Accede a la primera hoja
            // modificar
            Row row = sheet.getRow(6);             // Accede a la fila 7 fila (0-based)
            Cell cell = row.getCell(4);            // Accede a la columna 5 celda de esa fila
            cell.setCellValue("1050200224");  // Establece un nuevo valor para la celda



            Row newRow = sheet.getRow(64);
            Cell cell1 = newRow.getCell(1);
            cell1.setCellValue("Ingreso");
            //cell1.setCellStyle(borderStyle);

            Row newRow2 = sheet.getRow(65);
            Cell cell2 = newRow2.getCell(1);
            cell2.setCellValue("Salida");
            //cell2.setCellStyle(borderStyle);

            Row newRow3 = sheet.getRow(64);
            Cell cell3 = newRow3.getCell(2);
            cell3.setCellValue("Sabado");
            //cell2.setCellStyle(borderStyle);

            Row newRow4 = sheet.getRow(64);
            Cell cell4 = newRow4.getCell(3);
            cell4.setCellValue("12/25/2023");
            //cell2.setCellStyle(borderStyle);

            InputStream inputStream = new FileInputStream(Environment.getExternalStorageDirectory()+ "/imageTest.png");
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            CreationHelper helper = workbook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            // Define la posición de la imagen
            anchor.setCol1(1); // Columna B
            anchor.setRow1(23); // Fila 3
            anchor.setCol2(4); // Hasta columna D (es decir, ocupa B, C y D)
            anchor.setRow2(27); // Hasta fila 7 (es decir, ocupa 3, 4, 5 y 6)

            Picture pict = drawing.createPicture(anchor, pictureIdx);
            //pict.resize(); // Esto ajustará automáticamente el tamaño, pero puedes eliminarlo si quieres un tamaño específico




            File path2 = new File(Environment.getExternalStorageDirectory(), "salida.xlsx");
            try {
                FileOutputStream outputStream = new FileOutputStream(path2);
                workbook.write(outputStream);
                outputStream.close();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Test.this, "Archivo Excel creado en: " + path.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    inicio.setEnabled(true);
                }
            });

        }

    }
}