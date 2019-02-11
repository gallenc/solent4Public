package org.solent.com600.example.journeyplanner.model;

import java.util.List;

public interface SysUserDAO {

    public SysUser create(SysUser user);

    public Integer delete(Integer id);

    public Integer retrieve(Integer id);

    public List<SysUser> retrieveAll();

    public SysUser retrieveMatching(SysUser template);

    public SysUser update(SysUser entity);

    public void deleteAll();
}
