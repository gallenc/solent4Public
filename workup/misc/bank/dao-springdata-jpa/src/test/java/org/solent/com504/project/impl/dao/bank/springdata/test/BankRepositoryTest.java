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
import org.solent.com504.project.impl.dao.bank.springdata.BankAccountRepository;
import org.solent.com504.project.model.bank.dto.BankAccount;
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
public class BankRepositoryTest {

    final static Logger LOG = LogManager.getLogger(BankRepositoryTest.class);

    @Autowired
    private BankAccountRepository bankAccountRepository = null;

    @Before
    public void before() {
        LOG.debug("before test running");
        assertNotNull(bankAccountRepository);
        LOG.debug("before test complete");
    }

    
    @Transactional
    @Test
    public void test1() {
        LOG.debug("start of test1");

        BankAccount bankAccount1 = new BankAccount();
        bankAccount1 = bankAccountRepository.save(bankAccount1);
        System.out.println("bankAccount1=" + bankAccount1);

        Long id = bankAccount1.getId();
        BankAccount bankAccount2 = bankAccountRepository.getOne(id);
        System.out.println("bankAccount2=" + bankAccount2);
        LOG.debug("end of test1");
    }
}