/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.spring;

import java.util.List;
import org.solent.com504.project.model.dao.springdata.PartyDAOSpringData;
import org.solent.com504.project.model.dto.Party;
import org.solent.com504.project.model.dto.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.solent.com504.project.model.dao.PartyDAO;


/**
 *
 * @author gallenc
 */
@Component
public class PartyDAOImplSpring implements PartyDAO {
    
    @Autowired
    private PartyDAOSpringData partyDAOspring = null;

    @Override
    public Party findById(Long id) {
        return partyDAOspring.getOne(id);
    }

    @Override
    public Party save(Party party) {
        return partyDAOspring.save(party);
    }

    @Override
    public List<Party> findAll() {
        return partyDAOspring.findAll();
    }

    @Override
    public void deleteById(long id) {
        partyDAOspring.deleteById(id);
    }

    @Override
    public Party delete(Party party) {
        partyDAOspring.delete(party);
        return null; // TODO SHOULD CHANGE INTERFACE TO RETURN NULL
    }

    @Override
    public void deleteAll() {
        partyDAOspring.deleteAll();
    }

    @Override
    public List<Party> findByRole(Role role) {
        return partyDAOspring.findByRole(role);
    }

    @Override
    public List<Party> findByName(String firstName, String secondName) {
        return partyDAOspring.findByName(firstName, secondName);
    }
    
}
