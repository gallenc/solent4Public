package org.solent.com504.project.model.user.dao;

import java.util.List;
import org.solent.com504.project.model.user.dto.Role;

public interface RoleDAO {

    public Long findById(Long id);

    public Role save(Role role);

    public List<Role> findAll();

    public long deleteById(long id);

    public Role delete(Role role);

    public void deleteAll();

    public String findByRoleName(String rolename);
}
