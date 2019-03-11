package org.solent.com600.example.journeyplanner.model;

import java.util.List;

public interface SysUserDAO {

    public SysUser create(SysUser user);

    public void delete(Long id);

    public SysUser retrieve(Long id);

    public SysUser retrieveByUserName(String username);

    public List<SysUser> retrieveAll();

    public List<SysUser> retrieveLikeMatching(String surname, String firstname);

    public List<SysUser> retrieveAll(List<Role> userRoles);

    public List<SysUser> retrieveLikeMatching(String surname, String firstname, List<Role> userRoles);

    public SysUser update(SysUser entity);

    public void deleteAll();
}
