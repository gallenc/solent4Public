/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.examples.com600.mcride.service.test;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.solent.examples.com600.mcride.model.Entity;
import org.solent.examples.com600.mcride.model.ServiceFacade;
import org.solent.examples.com600.mcride.model.ServiceFactory;
import org.solent.examples.com600.mcride.service.ServiceFactoryImpl;

/**
 *
 * @author cgallen
 */
public class ServiceFacadeImplTest {

    public static final String TEST_DATA_FILE = "./target/testfile.xml";

    // Only some basic tests as most tests already done in EntityDAO tests
    @Test
    public void simpleServiceFacadeTest() {

        // use service factory to get access to service
        ServiceFactory serviceFactory = new ServiceFactoryImpl(TEST_DATA_FILE);
        assertNotNull(serviceFactory);

        ServiceFacade serviceFacade = serviceFactory.getServiceFacade();
        assertNotNull(serviceFacade);
        
        // clear file before anything else
        serviceFacade.deleteAllEntities();

        Entity entity = new Entity();
        entity.setField_A("testFieldA");

        serviceFacade.createEntity(entity);
        List<Entity> retrievedEntities = serviceFacade.retrieveMatchingEntities(entity);

        assertEquals(1, retrievedEntities.size());
    }
}
