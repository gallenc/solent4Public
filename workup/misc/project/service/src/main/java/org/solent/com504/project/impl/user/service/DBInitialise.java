/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.user.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.impl.dao.user.springdata.RoleRepository;
import org.solent.com504.project.model.user.dto.Role;
import org.solent.com504.project.model.user.dto.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author cgallen
 */
public class DBInitialise {

    final static Logger LOG = LogManager.getLogger(DBInitialise.class);

    @Autowired
    private RoleRepository roleRepository;

    public void init() {
        if (roleRepository.findAll().isEmpty()) {

            // add all roles in model to database note need to convert enum to correct values
            UserRoles[] allRoles = UserRoles.values();
            for (int i = 0; i < allRoles.length; i++) {
                String roleName = allRoles[i].name();
                LOG.debug("initialising user role " + roleName + " to database");
                Role role = new Role();
                role.setName(roleName);
                roleRepository.saveAndFlush(role);
            }

        }
    }

}
