/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.dao.springdata.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.solent.com504.project.model.dto.Party;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.solent.com504.project.model.dao.springdata.PartyDAOSpringData;

/**
 *
 * @author cgallen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class PartyDAOSpringDataTest {

    final static Logger LOG = LogManager.getLogger(PartyDAOSpringDataTest.class);

    @Autowired
    private PartyDAOSpringData partyDAOspring = null;

    @Before
    public void before() {
        LOG.debug("before test running");
        assertNotNull(partyDAOspring);
        LOG.debug("before test complete");
    }

    
    @Transactional
    @Test
    public void test1() {
        LOG.debug("start of test1");

        Party party1 = new Party();
        party1 = partyDAOspring.save(party1);
        System.out.println("party1=" + party1);

        Long id = party1.getId();
        Party party2 = partyDAOspring.getOne(id);
        System.out.println("party2=" + party2);
        LOG.debug("end of test1");
    }
}
