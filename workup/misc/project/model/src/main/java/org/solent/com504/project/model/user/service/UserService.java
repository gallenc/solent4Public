package org.solent.com504.project.model.user.service;

import org.solent.com504.project.model.user.dto.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
