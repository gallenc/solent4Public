package org.solent.com600.example.journeyplanner.model;

import java.util.List;
import javax.naming.AuthenticationException;

public interface ServiceFacade {

    public SysUser createUser(SysUser sysUser, String actingSysUserName) throws AuthenticationException;

    public void deleteUser(Long id, String actingSysUserName) throws AuthenticationException;

    public SysUser retrieveUser(Long id, String actingSysUserName) throws AuthenticationException;
    
    public SysUser retrieveByUserName(String username, String actingSysUserName) throws AuthenticationException;

    public List<SysUser> retrieveAllUsers(String actingSysUserName) throws AuthenticationException;

    public List<SysUser> retrieveLikeMatchingUsers(String surname, String firstname, String actingSysUserName) throws AuthenticationException;

    // TODO note problem - USER SHOULD NOT BE ABLE TO CHANGE ROLE OR USERNAME
    public SysUser updateUser(SysUser sysUser, String actingSysUserName) throws AuthenticationException;

    public void deleteAllUsers(String actingSysUserName) throws AuthenticationException;
}
