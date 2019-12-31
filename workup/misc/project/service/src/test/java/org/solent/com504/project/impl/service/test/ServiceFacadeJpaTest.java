/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.service.test;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.solent.com504.project.model.dto.Party;
import org.solent.com504.project.model.dto.PartyRole;
import org.solent.com504.project.model.service.ServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author gallenc
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appconfig-service.xml"})
public class ServiceFacadeJpaTest {

    @Autowired
    ServiceFacade serviceFacade = null;

    @Test
    public void testFactory() {
        System.out.println("start ServiceFacadeTest testFpartyy");
        assertNotNull(serviceFacade);

        System.out.println("end ServiceFacadeTest testFpartyy");
    }

    @Test
    public void testGetHeartbeat() {
        System.out.println("start ServiceFacadeTest testGetHeartbeat()");
        assertNotNull(serviceFacade);

        String heartbeat = serviceFacade.getHeartbeat();
        System.out.println("recieved heartbeat: " + heartbeat);
        assertNotNull(heartbeat);

        System.out.println("end FarmFacadeTest testGetHeartbeat()");
    }

    @Test
    public void testGetAllByPartyRole() {

        // you may want to create people first but you need to add this to the facade :)
        List<Party> partyList = serviceFacade.findByPartyRole(null);
        assertNotNull(partyList);

        partyList = serviceFacade.findByPartyRole(PartyRole.ANONYMOUS);
        assertNotNull(partyList);

    }

    // WHAT OTHER TESTS DO YOU NEED FOR THE SERVICE?
}
