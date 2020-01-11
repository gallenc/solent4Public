package org.solent.com504.project.impl.user.service;

import java.util.ArrayList;
import org.solent.com504.project.model.user.dto.User;
import org.solent.com504.project.model.user.service.UserService;
import org.solent.com504.project.impl.dao.user.springdata.RoleRepository;
import org.solent.com504.project.impl.dao.user.springdata.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.solent.com504.project.model.user.dao.UserDAO;
import org.solent.com504.project.model.user.dto.Role;
import org.solent.com504.project.model.user.dto.UserRoles;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // @Autowired
    // private UserDAO userDAO;
    @Override
    public void create(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findByName(UserRoles.ROLE_USER.toString())));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        // return userDAO.findAll();
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUserRoles(String username, List<String> roleNames) {
        User user = userRepository.findByUsername(username);

        // this could be more efficient
        Set newRoles = new HashSet();
        for (String rolename : roleNames) {
            List<Role> roles = roleRepository.findByName(rolename);
            if (roles.isEmpty()) {
                throw new IllegalArgumentException("rolename is not known to system: " + rolename);
            }
        }
        user.getRoles().clear();
        user.getRoles().addAll(newRoles);

        user = userRepository.save(user);

        return user;

    }

    @Override
    public List<Role> getAvailableUserRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<String> getAvailableUserRoleNames() {
        List<Role> roles = roleRepository.findAll();
        List<String> roleStrings = new ArrayList();
        for (Role role : roles) {
            roleStrings.add(role.getName());
        }
        return roleStrings;
    }

}
