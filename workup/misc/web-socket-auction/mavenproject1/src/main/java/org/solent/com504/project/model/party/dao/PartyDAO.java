package org.solent.com504.project.model.party.dao;

public interface PartyDAO {

    public Long findById(Long id);

    public Party save(Party party);

    public List<Party> findAll();

    public long deleteById(long id);

    public Party delete(Party party);

    public void deleteAll();

    public Role findByRole(Role role);

    public String findByName(String firstName, String secondName);

    public Party findByTemplate(Party template);
}
