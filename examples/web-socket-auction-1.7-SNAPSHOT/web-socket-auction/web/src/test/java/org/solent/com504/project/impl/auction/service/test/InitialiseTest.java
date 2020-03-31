/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service.test;

import org.junit.Test;
import static org.junit.Assert.*;
import org.solent.com504.project.impl.auction.dao.MockServiceObjectFactory;
import org.solent.com504.project.impl.auction.service.Initialise;

/**
 *
 * @author cgallen
 */
public class InitialiseTest {

    @Test
    public void testInitialise() {

        MockServiceObjectFactory mockServiceObjectFactory = new MockServiceObjectFactory();
        Initialise initialise = new Initialise(mockServiceObjectFactory);
        initialise.init();

    }
}
