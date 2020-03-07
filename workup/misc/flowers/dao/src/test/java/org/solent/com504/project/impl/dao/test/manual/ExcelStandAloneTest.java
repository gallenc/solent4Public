/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.test.manual;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 * Java program to read / write excel file using apache POI library. see https://mkyong.com/java/apache-poi-reading-and-writing-excel-file-in-java/ see
 * https://howtodoinjava.com/library/readingwriting-excel-files-in-java-poi-tutorial/
 *
 * @author gallenc
 */
public class ExcelStandAloneTest {

    @Test
    public void testreadfile() {

        InputStream fileis = null;

        try {
            // loading from absolute file
            //File file = new File("./src/main/resources/data/completePlantsChecklistUSDA.xlsx");
            //System.out.println(file.getAbsolutePath());
            //fileis = new FileInputStream(file);

            // loading from classpath in WAR
            fileis = this.getClass().getClassLoader().getResourceAsStream("data/completePlantsChecklistUSDA.xlsx");

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(fileis);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + ",");
                            break;
                        case STRING:
                            System.out.print(cell.getStringCellValue() + ",");
                            break;
                    }
                }
                System.out.println("");
            }
            fileis.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileis != null) {
                try {
                    fileis.close();
                } catch (IOException ex) {
                }
            }
        }
    }

}
