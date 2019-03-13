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
import org.solent.com600.example.journeyplanner.model.Rideout;
import org.solent.com600.example.journeyplanner.model.RideoutDAO;
import org.solent.com600.example.journeyplanner.model.RideoutState;
import org.solent.com600.example.journeyplanner.model.Role;
import org.solent.com600.example.journeyplanner.model.SysUserDAO;
import org.solent.com600.example.journeyplanner.model.UserInfo;

/**
 *
 * @author gallenc
 */
public class ServiceFacadeImpl implements ServiceFacade {
    //TODO THIS CLASs STILL ALLOWS SAME USERS TO retrieve THEIR OWN DATA - NEEDS CHANGED

    public static final String ANONYMOUS_SYSUSERNAME = "Anonymous";
    public static final String SUPERADMIN_SYSUSERNAME = "SuperAdmin";

    private SysUserDAO sysUserDAO = null;

    private RideoutDAO rideoutDAO = null;

    public RideoutDAO getRideoutDAO() {
        return rideoutDAO;
    }

    public void setRideoutDAO(RideoutDAO rideoutDAO) {
        this.rideoutDAO = rideoutDAO;
    }

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

    /* *********************************
     * methods from SysUserFacade
     * ********************************
     */

 /* **********************************************
       LOW GRANULARITY RESTRICTED DATA ACCESS METHODS
     */
    @Override
    public SysUser createUser(SysUser sysUser, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to create user");
        }
        if (sysUser.getPassWordHash() == null) {
            throw new AuthenticationException("user must be create with an initial passwordhash set");
        }

        String hash = PasswordUtils.hashPassword(sysUser.getPassword());
        sysUser.setPassword(null);
        sysUser.setPassWordHash(hash);

        SysUser newSysUser = sysUserDAO.create(sysUser);
        return newSysUser;
    }

    @Override
    public SysUser createUser(String userName, String password, String firstname, String surname, String actingSysUserName) throws AuthenticationException {
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        String passwordHash = PasswordUtils.hashPassword(password);
        sysUser.setPassWordHash(passwordHash);
        sysUser.setRole(Role.RIDER);
        sysUser.getUserInfo().setFirstname(firstname);
        sysUser.getUserInfo().setSurname(surname);
        return createUser(sysUser, actingSysUserName);
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
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve user");
        }
        return retrievedUser;
    }

    @Override
    public List<SysUser> retrieveAllUsers(String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        List<SysUser> retrievedUsers = sysUserDAO.retrieveAll();
        return retrievedUsers;
    }

    @Override
    public List<SysUser> retrieveAllUsers(List<Role> userRoles, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        List<SysUser> retrievedUsers = sysUserDAO.retrieveAll(userRoles);
        return retrievedUsers;
    }

    @Override
    public List<SysUser> retrieveLikeMatchingUsers(String surname, String firstname, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        List<SysUser> retrievedUsers = sysUserDAO.retrieveLikeMatching(surname, firstname);
        return retrievedUsers;
    }

    @Override
    public List<SysUser> retrieveLikeMatchingUsers(String surname, String firstname, List<Role> userRoles, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        List<SysUser> retrievedUsers = sysUserDAO.retrieveLikeMatching(surname, firstname, userRoles);
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
        if (retrievedUser == null) {
            return null;
        }
        if (!validateUserAction(retrievedUser, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve user");
        }
        return retrievedUser;
    }

    /* **********************************************
       HI GRANULARITY RESTRICTED DATA ACCESS METHODS
     */
    @Override
    public Role getRoleByUserName(String userName) {
        SysUser retrievedUser = sysUserDAO.retrieveByUserName(userName);
        if (retrievedUser != null) {
            return retrievedUser.getRole();
        } else {
            return null;
        }
    }

    @Override
    public UserInfo getUserInfoByUserName(String userName, String actingSysUserName) throws AuthenticationException {
        SysUser sysUser = retrieveByUserName(userName, actingSysUserName);
        if (sysUser == null) {
            throw new IllegalArgumentException("cannot find user for username " + userName);
        }
        return sysUser.getUserInfo();
    }

    @Override
    public void updateUserInfoByUserName(UserInfo updateUserInfo, String userName, String actingSysUserName) throws AuthenticationException {
        SysUser sysUser = retrieveByUserName(userName, actingSysUserName);
        if (sysUser == null) {
            throw new IllegalArgumentException("cannot find user for username " + userName);
        }
        sysUser.setUserInfo(updateUserInfo);
        updateUser(sysUser, actingSysUserName);
    }

    @Override
    public Boolean getInsuranceVerified(String userName, String actingSysUserName) throws AuthenticationException {
        SysUser sysUser = retrieveByUserName(userName, actingSysUserName);
        if (sysUser == null) {
            throw new IllegalArgumentException("cannot find user for username " + userName);
        }
        return new Boolean(sysUser.getProcessInfo().getInsuranceVerified()); // detach object
    }

    @Override
    public void updateInsuranceVerified(boolean insuranceVerified, String userName, String actingSysUserName) throws AuthenticationException {
        SysUser sysUser = retrieveByUserName(userName, actingSysUserName);
        if (sysUser == null) {
            throw new IllegalArgumentException("cannot find user  for username " + userName);
        }
        sysUser.getProcessInfo().setInsuranceVerified(insuranceVerified);
        updateUser(sysUser, actingSysUserName);
    }

    @Override
    public void updateUserRoleByUserName(Role newRole, String userName, String actingSysUserName) throws AuthenticationException {
        if (newRole == null) {
            throw new IllegalArgumentException("new role cannot be null");
        }
        SysUser sysUser = retrieveByUserName(userName, actingSysUserName);
        if (sysUser == null) {
            throw new IllegalArgumentException("cannot find user  for username " + userName);
        }
        sysUser.setRole(newRole);
        updateUser(sysUser, actingSysUserName);
    }

    @Override
    public void updatePasswordByUserName(String newPassword, String userName, String actingSysUserName) throws AuthenticationException {
        SysUser sysUser = retrieveByUserName(userName, actingSysUserName);
        if (sysUser == null) {
            throw new IllegalArgumentException("cannot find user for username " + userName);
        }
        String passwordHash = PasswordUtils.hashPassword(newPassword);
        sysUser.setPassWordHash(passwordHash);
        updateUser(sysUser, actingSysUserName);
    }

    @Override
    public void updateOldPasswordByUserName(String newPassword, String oldPassword, String userName, String actingSysUserName) throws AuthenticationException {
        SysUser sysUser = retrieveByUserName(userName, actingSysUserName);
        if (sysUser == null) {
            throw new IllegalArgumentException("cannot find user for username " + userName);
        }

        String oldPasswordHash = sysUser.getPassWordHash();
        if (!PasswordUtils.checkPassword(oldPassword, oldPasswordHash)) {
            throw new AuthenticationException("old password does not match");
        }

        String passwordHash = PasswordUtils.hashPassword(newPassword);
        sysUser.setPassWordHash(passwordHash);
        updateUser(sysUser, actingSysUserName);
    }

    @Override
    public boolean checkPasswordByUserName(String password, String userName, String actingSysUserName) throws AuthenticationException {
        SysUser sysUser = retrieveByUserName(userName, actingSysUserName);
        if (sysUser == null) {
            throw new IllegalArgumentException("cannot find user for username " + userName);
        }
        String passwordHash = sysUser.getPassWordHash();
        return PasswordUtils.checkPassword(password, passwordHash);
    }

    /* *********************************
     * methods from Rideout Facade
     * ********************************
     */
    @Override
    public Rideout createRideout(Rideout rideout, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        return rideoutDAO.createRideout(rideout);
    }

    @Override
    public Rideout updateRideout(Rideout rideout, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        return rideoutDAO.update(rideout);
    }

    @Override
    public void deleteRideout(Long id, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        rideoutDAO.delete(id);
    }

    @Override
    public Rideout retrieveRideout(Long id, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER, Role.RIDER));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        return rideoutDAO.retrieve(id);
    }

    @Override
    public List<Rideout> retrieveAllRideouts(String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER, Role.RIDER));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        return rideoutDAO.retrieveAll();
    }

    @Override
    public void deleteAllRideouts(String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        rideoutDAO.deleteAll();
    }

    @Override
    public List<Rideout> retrieveLikeMatchingRideouts(String title, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER, Role.RIDER));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        return rideoutDAO.retrieveLikeMatching(title);
    }

    @Override
    public List<Rideout> retrieveLikeMatchingRideouts(String title, List<RideoutState> rideoutStates, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER, Role.RIDER));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        return rideoutDAO.retrieveLikeMatching(title, rideoutStates);
    }

    @Override
    public List<Rideout> retrieveAllRideoutsByRideLeader(SysUser rideLeader, List<RideoutState> rideoutStates, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        return rideoutDAO.retrieveAllByRideLeader(rideLeader, rideoutStates);
    }

    @Override
    public List<Rideout> retrieveAllRideoutsByRider(SysUser rider, List<RideoutState> rideoutStates, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER, Role.RIDER));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        return rideoutDAO.retrieveAllByRider(rider, rideoutStates);
    }

    @Override
    public List<Rideout> retrieveAllRideouts(List<RideoutState> rideoutStates, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER, Role.RIDER));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        return rideoutDAO.retrieveAll(rideoutStates);
    }

    @Override
    public List<Rideout> retrieveAllRideoutsWaitListByRider(SysUser rider, List<RideoutState> rideoutStates, String actingSysUserName) throws AuthenticationException {
        List<Role> authList = Collections.unmodifiableList(Arrays.asList(Role.ADMIN, Role.RIDELEADER, Role.RIDER));
        if (!validateUserAction(null, actingSysUserName, authList)) {
            throw new AuthenticationException(actingSysUserName + " does not have permissions to retrieve all users");
        }
        return rideoutDAO.retrieveAllWaitListByRider(rider, rideoutStates);
    }

}
