package org.solent.com504.project.model.service;

import java.util.List;
import org.solent.com504.project.model.dto.Party;
import org.solent.com504.project.model.dto.PartyRole;

public interface ServiceFacade {

    public String getHeartbeat();

    /**
     * find all partys in database by partyRole if partyRole is null return all partys
     *
     * @param partyRole
     * @return list of party objects
     */
    public List<Party> findByPartyRole(PartyRole partyRole);

}
