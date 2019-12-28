/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com600.example.journeyplanner.model;

import java.util.List;
import javax.naming.AuthenticationException;

/**
 *
 * @author cgallen
 */
public interface UserFacade {
    
    public SysUser createUser(SysUser sysUser, String actingSysUserName) throws AuthenticationException;

    public SysUser createUser(String userName, String password, String firstname, String lastname, Role role, String actingSysUserName) throws AuthenticationException;

    public void deleteUser(Long id, String actingSysUserName) throws AuthenticationException;

    public SysUser retrieveUser(Long id, String actingSysUserName) throws AuthenticationException;

    public SysUser retrieveByUserName(String username, String actingSysUserName) throws AuthenticationException;

    public List<SysUser> retrieveAllUsers(String actingSysUserName) throws AuthenticationException;

    public List<SysUser> retrieveLikeMatchingUsers(String surname, String firstname, String actingSysUserName) throws AuthenticationException;

    public SysUser updateUser(SysUser sysUser, String actingSysUserName) throws AuthenticationException;

    public void deleteAllUsers(String actingSysUserName) throws AuthenticationException;

    public UserInfo getUserInfoByUserName(String userName, String actingSysUserName) throws AuthenticationException;

    public void updateUserInfoByUserName(UserInfo updateUserInfo, String userName, String actingSysUserName) throws AuthenticationException;

    public void updatePasswordByUserName(String newPassword, String userName, String actingSysUserName) throws AuthenticationException;

    public void updateOldPasswordByUserName(String newPassword, String oldPassword, String userName, String actingSysUserName) throws AuthenticationException;

    public boolean checkPasswordByUserName(String password, String userName, String actingSysUserName) throws AuthenticationException;

    public Boolean getInsuranceVerified(String userName, String actingSysUserName) throws AuthenticationException;

    public void updateInsuranceVerified(boolean insuranceVerified, String userName, String actingSysUserName) throws AuthenticationException;

    public void updateUserRoleByUserName(Role newRole, String userName, String actingSysUserName) throws AuthenticationException;

    public Role getRoleByUserName(String userName);
    
    public List<SysUser> retrieveAllUsers(List<Role> userRoles, String actingSysUserName) throws AuthenticationException;
    
    public List<SysUser> retrieveLikeMatchingUsers(String surname, String firstname, List<Role> userRoles, String actingSysUserName) throws AuthenticationException;

}
