/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import org.junit.Test;

/**
 * Java program to read / write excel file using apache POI library. see
 * https://howtodoinjava.com/library/readingwriting-excel-files-in-java-poi-tutorial/
 *
 * @author gallenc
 */
public class excelStandAloneTest {

    @Test
    public void testreadfile() {
        {
            try {
                FileInputStream file = new FileInputStream(new File("howtodoinjava_demo.xlsx"));

                //Create Workbook instance holding reference to .xlsx file
                XSSFWorkbook workbook = new XSSFWorkbook(file);

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
                            case Cell.CELL_TYPE_NUMERIC:
                                System.out.print(cell.getNumericCellValue() + "t");
                                break;
                            case Cell.CELL_TYPE_STRING:
                                System.out.print(cell.getStringCellValue() + "t");
                                break;
                        }
                    }
                    System.out.println("");
                }
                file.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
