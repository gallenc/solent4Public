package org.solent.com504.project.model.dao;

import java.util.List;
import org.solent.com504.project.model.dto.Party;
import org.solent.com504.project.model.dto.PartyRole;

public interface PartyDAO {

    public Party findById(Long id);

    public Party save(Party party);

    public List<Party> findAll();

    public void deleteById(long id);

    public Party delete(Party party);

    public void deleteAll();

    public List<Party> findByPartyRole(PartyRole partyRole);

    public List<Party> findByName(String firstName, String secondName);
}
