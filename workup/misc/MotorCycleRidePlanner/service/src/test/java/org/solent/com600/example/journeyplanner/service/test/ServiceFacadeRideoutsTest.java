/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com600.example.journeyplanner.service.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.com600.example.journeyplanner.model.ServiceFacade;
import org.solent.com600.example.journeyplanner.model.ServiceFactory;
import org.solent.com600.example.journeyplanner.service.ServiceFactoryImpl;
import static org.solent.com600.example.journeyplanner.service.test.ServiceFacadeUsersTest.createDatabaseTestUsers;

/**
 *
 * @author gallenc
 */
public class ServiceFacadeRideoutsTest {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceFacadeRideoutsTest.class);

    @Test
    public void testRideout() {
            
        
        ServiceFactory serviceFactory = new ServiceFactoryImpl();
        assertNotNull(serviceFactory);

        ServiceFacade serviceFacade = serviceFactory.getServiceFacade();
        assertNotNull(serviceFacade);

        // test we can create users in database
        createDatabaseTestUsers(serviceFacade);

    }
}
