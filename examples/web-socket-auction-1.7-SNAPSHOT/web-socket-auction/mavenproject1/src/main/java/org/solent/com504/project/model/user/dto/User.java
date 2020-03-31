package org.solent.com504.project.model.user.dto;

public class User {

    private String username;

    private String password;

    private String passwordConfirm;

    private String firstName;

    private String secondName;

    private Long id;

    private Set<Role> roles;

    private Address address;
}
