/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.user.springdata.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.solent.com504.project.model.user.dto.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.solent.com504.project.impl.dao.user.springdata.RoleRepository;

/**
 *
 * @author cgallen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class RoleRepositoryTest {

    final static Logger LOG = LogManager.getLogger(RoleRepositoryTest.class);

    @Autowired
    private RoleRepository roleRepository = null;

    @Before
    public void before() {
        LOG.debug("before test running");
        assertNotNull(roleRepository);
        LOG.debug("before test complete");
    }

    
    @Transactional
    @Test
    public void test1() {
        LOG.debug("start of test1");

        Role role1 = new Role();
        role1 = roleRepository.save(role1);
        System.out.println("role1=" + role1);

        Long id = role1.getId();
        Role role2 = roleRepository.getOne(id);
        System.out.println("role2=" + role2);
        LOG.debug("end of test1");
    }
}
