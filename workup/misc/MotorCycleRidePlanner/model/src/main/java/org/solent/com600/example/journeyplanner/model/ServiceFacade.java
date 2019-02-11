package org.solent.com600.example.journeyplanner.model;

import java.util.List;

public interface ServiceFacade {

    public SysUser create(SysUser user, SysUser actingUser);

    public Integer delete(Integer id, SysUser actingUser);

    public Integer retrieve(Integer id, SysUser actingUser);

    public List<SysUser> retrieveAll();

    public SysUser retrieveMatching(SysUser template, SysUser actingUser);

    public SysUser update(SysUser user, SysUser actingUser);

    public void deleteAll(SysUser actingUser);
}
