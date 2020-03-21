/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.dao;

import java.util.LinkedHashMap;
import java.util.List;
import org.solent.com504.project.model.party.dao.PartyDAO;
import org.solent.com504.project.model.party.dto.Party;
import org.solent.com504.project.model.party.dto.PartyRole;

/**
 *
 * @author cgallen
 */
public class PartyMockDAO implements PartyDAO{
    
    private LinkedHashMap<String, Party> partyMap = new LinkedHashMap();

    @Override
    public Party findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Party save(Party party) {
        partyMap.put(party.getUuid(), party);
        return party;
    }

    @Override
    public List<Party> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Party party) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Party> findByPartyRole(PartyRole partyRole) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Party> findByName(String firstName, String secondName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Party findByUuid(String uuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
