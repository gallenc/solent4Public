package org.solent.com504.project.impl.dao.user.springdata;

import org.solent.com504.project.model.user.dto.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
