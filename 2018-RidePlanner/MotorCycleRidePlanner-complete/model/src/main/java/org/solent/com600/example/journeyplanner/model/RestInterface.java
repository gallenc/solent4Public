package org.solent.com600.example.journeyplanner.model;

public interface RestInterface {

    public SysUser retrieveMatchingUsers(SysUser tempate);

    public Integer retrieveUser(Integer id);
}
