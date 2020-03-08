/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.flower.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.flower.service.ServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author gallenc
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appconfig-service.xml"})
public class ServiceFacadeTest {

    final static Logger LOG = LogManager.getLogger(ServiceFacadeTest.class);

    @Autowired
    ServiceFacade serviceFacade = null;

    @Test
    public void testFactory() {
        LOG.debug("start ServiceFacadeTest testFactory");
        assertNotNull(serviceFacade);

        LOG.debug("end ServiceFacadeTest  testFactory");
    }

    @Test
    public void testGetHeartbeat() {
        LOG.debug("start ServiceFacadeTest testGetHeartbeat()");
        assertNotNull(serviceFacade);

        String heartbeat = serviceFacade.getHeartbeat();
        LOG.debug("recieved heartbeat: " + heartbeat);
        assertNotNull(heartbeat);

        LOG.debug("end FarmFacadeTest testGetHeartbeat()");
    }

// tests from dao
        @Test
    public void testfindBySymbolOrSynonymSymbol(){
        List<Flower> flowers =serviceFacade.findBySymbolOrSynonymSymbol("ABAM5");
        assertFalse(flowers.isEmpty());
    }

    @Test
    public void testfindLike(){
        //Flower(symbol, synonymSymbol, scientificNamewithAuthor, commonName, family,dataUrl)
        Flower flower = new Flower(null, null, "Veronica hederifolia L.", "", "", null);
        List<Flower> flowers =serviceFacade.findLike(flower);
        assertFalse(flowers.isEmpty());
    }

    @Test
    public void testfindLikeCommonName(){
        List<Flower> flowers =serviceFacade.findLikeCommonName("ivyleaf speedwell");
        assertFalse(flowers.isEmpty());
    }
    
    @Test
    public void testfindLikeScientificNamewithAuthor(){
        List<Flower> flowers =serviceFacade.findLikeScientificNamewithAuthor("Verbesina pauciflora");
        assertFalse(flowers.isEmpty());
    }
    
    @Test
    public void testfindLikefamily(){
        List<Flower> flowers =serviceFacade.findLikefamily("Asteraceae");
        assertFalse(flowers.isEmpty());
    }
    
    @Test
    public void testgetAllFamilies(){
        List<String> commonNames =serviceFacade.getAllFamilies();
        assertFalse(commonNames.isEmpty());
    }
    
}
