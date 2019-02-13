/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com600.example.journeyplanner.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.solent.com600.example.journeyplanner.model.ServiceFacade;
import org.solent.com600.example.journeyplanner.model.SysUser;
import javax.naming.AuthenticationException;
import org.solent.com600.example.journeyplanner.model.Role;
import org.solent.com600.example.journeyplanner.model.SysUserDAO;

/**
 *
 * @author gallenc
 */
public class ServiceFacadeImpl implements ServiceFacade {
    
    public static final String ANONYMOUS_SYSUSERNAME = "Anonymous";
    public static final String SUPERADMIN_SYSUSERNAME = "SuperAdmin";
    
    private SysUserDAO sysUserDAO = null;
    
    public SysUserDAO getSysUserDAO() {
        return sysUserDAO;
    }
    
    public void setSysUserDAO(SysUserDAO sysUserDAO) {
        this.sysUserDAO = sysUserDAO;
    }
    
    private boolean validateUserAction(SysUser modifiedSysUser, String actingSysUserName, List<Role> authList) {
        if (actingSysUserName == null) {
            throw new IllegalArgumentException("actingSysUserName must not be null when calling operation");
        }
        if (authList == null) {
            throw new IllegalArgumentException("authList must not be null when authenticating operation");
        }
        // superadmin can always make a call. 
        // Used for testing in absence of Admin user in database
        if (SUPERADMIN_SYSUSERNAME.equals(actingSysUserName)) {
            return true;
        }
        // anonymous is a user which has not logged in. No actual user account.
        if (ANONYMOUS_SYSUSERNAME.equals(actingSysUserName)) {
            if (!authList.contains(Role.ANONYMOUS)) {
                return false;
            }
        } else {
            SysUser actingSysUser = sysUserDAO.retrieveByUserName(actingSysUserName);
            // unknown actingSysUser
            if (actingSysUser == null) {
                return false;
            }
            
            Role actingRole = actingSysUser.getRole();
            if (authList.contains(actingRole)) {
                return true;
            }
            if (modifiedSysUser != null && modifiedSysUser.getUserName().equals(actingSysUserName)) {
                if (authList.contains(Role.SAME_USER)) {
                    return true;
                }
            }
            
        }
        return false;
    }
    
    @Override
    public SysUser createUser(SysUser sysUser, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to create user");
        }
        SysUser newSysUser = sysUserDAO.create(sysUser);
        return newSysUser;
    }
    
    @Override
    public void deleteUser(Long id, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to delete user");
        }
        sysUserDAO.delete(id);
    }
    
    @Override
    public SysUser retrieveUser(Long id, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER, Role.SAME_USER));
        SysUser retrievedUser = sysUserDAO.retrieve(id);
        if (!validateUserAction(retrievedUser, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retreive user");
        }
        return retrievedUser;
    }
    
    @Override
    public List<SysUser> retrieveAllUsers(String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retreive all users");
        }
        List<SysUser> retrievedUsers = sysUserDAO.retrieveAll();
        return retrievedUsers;
    }
    
    @Override
    public List<SysUser> retrieveLikeMatchingUsers(String surname, String firstname, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retreive all users");
        }
        List<SysUser> retrievedUsers = sysUserDAO.retrieveAll();
        return retrievedUsers;
    }
    
    @Override
    public SysUser updateUser(SysUser sysUser, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.SAME_USER));
        if (!validateUserAction(sysUser, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to update user");
        }
        SysUser newSysUser = sysUserDAO.update(sysUser);
        return newSysUser;
    }
    
    @Override
    public void deleteAllUsers(String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to delete all users");
        }
        sysUserDAO.deleteAll();
    }
    
    @Override
    public SysUser retrieveByUserName(String username, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER, Role.SAME_USER));
        SysUser retrievedUser = sysUserDAO.retrieveByUserName(username);
        if (!validateUserAction(retrievedUser, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retreive user");
        }
        return retrievedUser;
    }
}
