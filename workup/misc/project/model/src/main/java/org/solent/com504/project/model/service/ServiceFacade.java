package org.solent.com504.project.model.service;

import java.util.List;
import org.solent.com504.project.model.dto.Party;
import org.solent.com504.project.model.dto.Role;

public interface ServiceFacade {

    public String getHeartbeat();

    /**
     * find all partys in database by role if role is null return all partys
     *
     * @param role
     * @return list of party objects
     */
    public List<Party> findByRole(Role role);

}
