/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.flower.dao.test.manual;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.solent.com504.project.impl.flower.dao.FlowerDaoSpreadsheetImpl;
import org.solent.com504.project.model.flower.dao.FlowerDao;
import org.solent.com504.project.model.flower.dto.Flower;

/**
 *
 * @author cgallen
 */
public class FlowerDaoSpreasdsheetImplTest {

    // only load dao once for all tests 
    private static FlowerDaoSpreadsheetImpl flowerDao = new FlowerDaoSpreadsheetImpl();

    @Before
    public void loadData() {
        flowerDao.init();
    }

    @Test
    public void testinit() {
        System.out.println("start testinit");
        // simply runs lodaData()
    }

    @Test
    public void testfindBySymboOrSynonymSymbol() {
        // synonym
        List<Flower> flowers = flowerDao.findBySymboOrSynonymSymbol("ABAM5");
        assertEquals(1, flowers.size());
        System.out.println(" retreived " + flowers.get(0));
        assertEquals("Abutilon americanum (L.) Sweet", flowers.get(0).getScientificNamewithAuthor());

        // symbol
        flowers = flowerDao.findBySymboOrSynonymSymbol("ABAB");
        assertEquals(4, flowers.size());
    }

    @Test
    public void testfindLikeScientificNamewithAuthor() {
        System.out.println("start testLikeScientificNamewithAuthor()");
        List<Flower> flowers = flowerDao.findLikeScientificNamewithAuthor("Verbesina pauciflora");
        assertEquals(1, flowers.size());
        System.out.println(" retreived " + flowers.get(0));
        assertEquals("Verbesina pauciflora (Nutt.) Small, non Hemsl.", flowers.get(0).getScientificNamewithAuthor());

    }

    @Test
    public void testfindLikeCommonName() {
        System.out.println("start testfindLikeCommonName()");
        List<Flower> flowers = flowerDao.findLikeCommonName("ivyleaf speedwell");
        assertEquals(1, flowers.size());
        System.out.println(" retreived " + flowers.get(0));
        assertEquals("Veronica hederifolia L.", flowers.get(0).getScientificNamewithAuthor());

    }

    @Test
    public void testfindLikefamily() {
        System.out.println("start testfindLikeFamily()");
        List<Flower> flowers = flowerDao.findLikefamily("Asteraceae");
        for (Flower flower : flowers) {
            System.out.println(" retreived " + flower);
            assertEquals("Asteraceae", flower.getFamily());
        }

    }

    @Test
    public void testfindLike() {
        System.out.println("start testfindLike()");
        //Flower(symbol, synonymSymbol, scientificNamewithAuthor, commonName, family,dataUrl)
        Flower flowerTest = new Flower(null, null, "Veronica hederifolia L.", "", "", null);
        List<Flower> flowers = flowerDao.findLike(flowerTest);
        for (Flower flower : flowers) {
            System.out.println(" retreived " + flower);
        }
    }

    @Test
    public void testgetAllFamilies() {
        System.out.println("start getAllFamilies");
        List<String> families = flowerDao.getAllFamilies();
        assertNotNull(families);
        assertFalse(families.isEmpty());
        assertTrue(families.contains("Thuidiaceae"));
    }
}
