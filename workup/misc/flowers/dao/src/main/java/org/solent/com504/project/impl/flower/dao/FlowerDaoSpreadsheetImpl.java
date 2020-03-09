/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.flower.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.flower.dao.FlowerDao;

/**
 *
 * @author cgallen
 */
public class FlowerDaoSpreadsheetImpl implements FlowerDao {
     final static Logger LOG = LogManager.getLogger(FlowerDaoSpreadsheetImpl.class);

    // COLUMNS IN SPREADSHEET
    private int SYMBOL = 0;
    private int SYNONYM_SYMBOL = 1;
    private int SCIENTIFIC_NAME_WITH_AUTHOR = 2;
    private int COMMON_NAME = 3;
    private int FAMILY = 4;
    private int URL = 5;

    private XSSFSheet sheet = null;

    private String cellToString(Cell cell) {
        switch (cell.getCellType()) {
            case NUMERIC:
                return Double.toString(cell.getNumericCellValue());

            case STRING:
                return cell.getStringCellValue();
        }
        return null;
    }

    private Flower rowToFlower(Row row) {
        Flower flower = new Flower(
                (row.getCell(SYMBOL) == null) ? null : cellToString(row.getCell(SYMBOL)),
                (row.getCell(SYNONYM_SYMBOL) == null) ? null : cellToString(row.getCell(SYNONYM_SYMBOL)),
                (row.getCell(SCIENTIFIC_NAME_WITH_AUTHOR) == null) ? null : cellToString(row.getCell(SCIENTIFIC_NAME_WITH_AUTHOR)),
                (row.getCell(COMMON_NAME) == null) ? null : cellToString(row.getCell(COMMON_NAME)),
                (row.getCell(FAMILY) == null) ? null : cellToString(row.getCell(FAMILY)),
                (row.getCell(URL) == null) ? null : cellToString(row.getCell(URL)));
        return flower;
    }

    @Override
    public synchronized List<Flower> findBySymbolOrSynonymSymbol(String symbol) {
        //Iterate through each row
        List<Flower> flowers = new ArrayList();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if ((row.getCell(SYMBOL) != null)
                    && (symbol.equals(cellToString(row.getCell(SYMBOL))))
                    || ((row.getCell(SYNONYM_SYMBOL) != null)
                    && symbol.equals(cellToString(row.getCell(SYNONYM_SYMBOL))))) {
                Flower foundFlower = rowToFlower(row);
                flowers.add(foundFlower);
            }
        }

        return flowers;
    }

    @Override
    public synchronized List<Flower> findLikeScientificNamewithAuthor(String scientificNamewithAuthor) {
        //Iterate through each row
        String scientificNamewithAuthorUpper = scientificNamewithAuthor.toUpperCase();
        List<Flower> flowers = new ArrayList();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getCell(SCIENTIFIC_NAME_WITH_AUTHOR) != null
                    && cellToString(row.getCell(SCIENTIFIC_NAME_WITH_AUTHOR)).toUpperCase()
                            .contains(scientificNamewithAuthorUpper)) {
                Flower foundFlower = rowToFlower(row);
                flowers.add(foundFlower);
            }
        };
        return flowers;
    }

    @Override
    public synchronized List<Flower> findLikeCommonName(String commonName) {
        //Iterate through each row
        String commonNameUpper = commonName.toUpperCase();
        List<Flower> flowers = new ArrayList();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getCell(COMMON_NAME) != null
                    && cellToString(row.getCell(COMMON_NAME)).toUpperCase().contains(commonNameUpper)) {
                Flower foundFlower = rowToFlower(row);
                flowers.add(foundFlower);
            }
        };
        return flowers;
    }

    @Override
    public synchronized List<Flower> findLikefamily(String family) {
        //Iterate through each row
        String familyUpper = family.toUpperCase();
        List<Flower> flowers = new ArrayList();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getCell(this.FAMILY) != null && cellToString(row.getCell(this.FAMILY)).toUpperCase().contains(familyUpper)) {
                Flower foundFlower = rowToFlower(row);
                flowers.add(foundFlower);
            }
        };
        return flowers;
    }

    private boolean compareLikeFlower(Flower flowerValue, Flower flowerTest) {

        if (flowerTest.getSymbol() != null && !flowerTest.getSymbol().isEmpty()) {
            if (flowerValue.getSymbol() == null) {
                return false;
            }
            if (!flowerValue.getSymbol().toUpperCase().contains(flowerTest.getSymbol().toUpperCase())) {
                return false;
            }
        }

        if (flowerTest.getSynonymSymbol() != null && !flowerTest.getSynonymSymbol().isEmpty()) {
            if (flowerValue.getSynonymSymbol() == null) {
                return false;
            }
            if (!flowerValue.getSynonymSymbol().toUpperCase()
                    .contains(flowerTest.getSynonymSymbol().toUpperCase())) {
                return false;
            }
        }
        if (flowerTest.getScientificNamewithAuthor() != null && !flowerTest.getScientificNamewithAuthor().isEmpty()) {
            if (flowerValue.getScientificNamewithAuthor() == null) {
                return false;
            }
            if (!flowerValue.getScientificNamewithAuthor().toUpperCase()
                    .contains(flowerTest.getScientificNamewithAuthor().toUpperCase())) {
                return false;
            }
        }
        if (flowerTest.getFamily() != null && !flowerTest.getFamily().isEmpty()) {
            if (flowerValue.getFamily() == null) {
                return false;
            }
            if (!flowerValue.getFamily().toUpperCase()
                    .contains(flowerTest.getFamily().toUpperCase())) {
                return false;
            }
        }

        if (flowerTest.getDataUrl() != null && !flowerTest.getDataUrl().isEmpty()) {
            if (flowerValue.getDataUrl() == null) {
                return false;
            }
            if (!flowerValue.getDataUrl().toUpperCase()
                    .contains(flowerTest.getDataUrl().toUpperCase())) {
                return false;
            }
        }
        // if all tests pass 
        return true;
    }

    @Override
    public synchronized List<Flower> findLike(Flower flowerTest) {
        List<Flower> flowers = new ArrayList();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Flower flowerValue = rowToFlower(row);
            if (compareLikeFlower(flowerValue, flowerTest)) {
                flowers.add(flowerValue);
            }
        };
        return flowers;
    }

    @Override
    public synchronized List<String> getAllFamilies() {
        Set<String> families = new TreeSet(); // preserves natural order
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell cell = row.getCell(this.FAMILY);
            if (cell != null) {
                families.add(cellToString(cell));
            }
        };
        return new ArrayList(families);
    }

    public synchronized void init() {
        LOG.debug("Loading excel sheet");
        // only initialise sheet if init has not been called before
        if (sheet == null) {
            InputStream fileis = null;
            try {
                // loading from classpath in WAR
                fileis = this.getClass().getClassLoader().getResourceAsStream("data/completePlantsChecklistUSDA.xlsx");
                //Create Workbook instance holding reference to .xlsx file
                XSSFWorkbook workbook = new XSSFWorkbook(fileis);
                //Get first/desired sheet from the workbook
                sheet = workbook.getSheetAt(0);

            } catch (Exception e) {
                throw new RuntimeException("problem loading excel file ", e);
            } finally {
                if (fileis != null) {
                    try {
                        fileis.close();
                    } catch (IOException ex) {
                    }
                }
            }
            LOG.debug("finished loading excel sheet");
        } else {
            LOG.debug("sheet already loaded");
        }
    }

}
