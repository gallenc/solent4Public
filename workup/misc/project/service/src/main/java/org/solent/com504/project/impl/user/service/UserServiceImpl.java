package org.solent.com504.project.impl.user.service;

import org.solent.com504.project.model.user.dto.User;
import org.solent.com504.project.model.user.service.UserService;
import org.solent.com504.project.impl.dao.user.springdata.RoleRepository;
import org.solent.com504.project.impl.dao.user.springdata.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import org.solent.com504.project.model.user.dao.UserDAO;
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

}
