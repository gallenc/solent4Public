package org.solent.com504.project.impl.dao.user.springdata;

import org.solent.com504.project.model.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
