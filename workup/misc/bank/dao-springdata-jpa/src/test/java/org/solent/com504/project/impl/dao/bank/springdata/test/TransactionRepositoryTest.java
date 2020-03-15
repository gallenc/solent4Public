/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.bank.springdata.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.solent.com504.project.impl.dao.bank.springdata.TransactionRepository;
import org.solent.com504.project.model.bank.dto.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cgallen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class TransactionRepositoryTest {

    final static Logger LOG = LogManager.getLogger(TransactionRepositoryTest.class);

    @Autowired
    private TransactionRepository transactionRepository = null;

    @Before
    public void before() {
        LOG.debug("before test running");
        assertNotNull(transactionRepository);
        LOG.debug("before test complete");
    }

    
    @Transactional
    @Test
    public void test1() {
        LOG.debug("start of test1");

        Transaction transaction1 = new Transaction();
        transaction1 = transactionRepository.save(transaction1);
        System.out.println("transaction1=" + transaction1);

        Long id = transaction1.getId();
        Transaction transaction2 = transactionRepository.getOne(id);
        System.out.println("transaction2=" + transaction2);
        LOG.debug("end of test1");
    }
}