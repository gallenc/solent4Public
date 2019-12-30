/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.web;


import org.solent.com504.project.impl.dao.user.springdata.RoleRepository;
import org.solent.com504.project.model.user.dto.Role;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author cgallen
 */
public class DBInitialise {

    @Autowired
    private RoleRepository roleRepository;

    public void init() {
        if (roleRepository.findAll().isEmpty()) {
            Role role = new Role();
            role.setName("ROLE_USER");
            roleRepository.saveAndFlush(role);
        }
    }

}
