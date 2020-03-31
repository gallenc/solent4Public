package org.solent.com504.project.model.user.dao;

public interface UserDAO {

    public Long findById(Long id);

    public User save(User user);

    public List<User> findAll();

    public long deleteById(long id);

    public User delete(User user);

    public void deleteAll();

    public String findByRoleName(String rolename);

    public String findByNames(String firstName, String secondName);

    public String findByUsername(String username);
}
