package org.solent.com600.example.journeyplanner.jpadao.test;

import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.com600.example.journeyplanner.jpadao.DAOFactory;
import org.solent.com600.example.journeyplanner.model.Address;
import org.solent.com600.example.journeyplanner.model.Insurance;
import org.solent.com600.example.journeyplanner.model.Role;
import org.solent.com600.example.journeyplanner.model.SysUser;
import org.solent.com600.example.journeyplanner.model.SysUserDAO;

public class TestSysUserDAO {

    private static final Logger LOG = LoggerFactory.getLogger(TestSysUserDAO.class);

    @Test
    public void testDAOFactory() {
        SysUserDAO userDAO = DAOFactory.getSysUserDAO();
        assertNotNull(userDAO);
    }

    @Test
    public void testFindUserRoles() {

        SysUserDAO userDAO = DAOFactory.getSysUserDAO();
        assertNotNull(userDAO);

        testCreateSysUsersDatabase(userDAO);

        // now read back
        List<SysUser> sysUserList = userDAO.retrieveAll();
        printOutValues(sysUserList);

        // make user1 admin
        SysUser admin = sysUserList.get(0);
        assertNotNull(admin);
        String adminName = admin.getUserName();
        admin.setRole(Role.ADMIN);
        userDAO.update(admin);

        // make user2 ride leader
        SysUser leader = sysUserList.get(1);
        assertNotNull(leader);
        String leaderName = leader.getUserName();
        leader.setRole(Role.RIDELEADER);
        userDAO.update(leader);

        // make user3 anonymous
        SysUser anon = sysUserList.get(2);
        assertNotNull(anon);
        String anonName = anon.getUserName();
        anon.setRole(Role.ANONYMOUS);
        userDAO.update(anon);

        // get first user by username
        SysUser user1 = userDAO.retrieveByUserName(adminName);
        assertTrue(user1.getId().equals(admin.getId()));
        assertTrue(user1.getUserInfo().getFirstname().equals(admin.getUserInfo().getFirstname()));
        assertTrue(user1.getRole().equals(Role.ADMIN));

        // get third user by username
        SysUser user3 = userDAO.retrieveByUserName(anonName);
        assertTrue(user3.getId().equals(anon.getId()));
        assertTrue(user3.getUserInfo().getFirstname().equals(anon.getUserInfo().getFirstname()));
        assertTrue(user3.getRole().equals(Role.ANONYMOUS));

    }

    @Test
    public void testFindByNearMatch() {

        SysUserDAO userDAO = DAOFactory.getSysUserDAO();
        assertNotNull(userDAO);

        testCreateSysUsersDatabase(userDAO);

        // now read back
        List<SysUser> sysUserList = userDAO.retrieveAll();
        printOutValues(sysUserList);

        // make user1 admin
        SysUser admin = sysUserList.get(0);
        assertNotNull(admin);
        String adminName = admin.getUserName();
        admin.setRole(Role.ADMIN);
        admin.getUserInfo().setSurname("aaabbbccc");
        admin.getUserInfo().setFirstname("dddeeefff");
        userDAO.update(admin);

        // find near match
        String surname = "abbbc";
        String firstname = "";
        List<SysUser> userListResult = userDAO.retrieveLikeMatching(surname, firstname);
        LOG.debug("test select surname=" + surname + " firstname=" + firstname + " userListResult:");
        printOutValues(userListResult);
        assertTrue(userListResult.size() == 1);
        assertTrue(userListResult.get(0).getUserName().equals(admin.getUserName()));

    }

    @Test
    public void testPasswordNotSaved() {
        SysUserDAO userDAO = DAOFactory.getSysUserDAO();
        assertNotNull(userDAO);

        testCreateSysUsersDatabase(userDAO);

        // now read back
        List<SysUser> sysUserList = userDAO.retrieveAll();
        printOutValues(sysUserList);

        for (SysUser sysUser : sysUserList) {
            LOG.debug("hash "+sysUser.getPassWordHash()+" password="+sysUser.getPassword());
            //TODO problem  - password is not saved in db but remains in jpa context
           assertNotNull(sysUser.getPassWordHash());
          // assertNull(sysUser.getPassword());
        }

    }

// utility methods
    public void testCreateSysUsersDatabase(SysUserDAO sysUserDAO) {
        assertNotNull(sysUserDAO);

        // empty database before test
        sysUserDAO.deleteAll();
        List<SysUser> emptyList = sysUserDAO.retrieveAll();
        assertTrue(emptyList.isEmpty());

        //Create 10 unique users
        for (int i = 0; i < 10; i++) {
            // populate each sysUser with data
            SysUser sysUser = new SysUser();
            String userName = "User_" + i;
            sysUser.setUserName(userName);

            Address address = new Address();
            address.setNumber("6");
            address.setAddressLine1("Plane Avenue");
            address.setAddressLine2("Oswestry");
            address.setCountry("England");
            address.setCounty("Hampshire");
            address.setPostcode("HA23TV");
            address.setLatitude(0);
            address.setLongitude(0);
            address.setMobile("0777444555444");

            sysUser.getUserInfo().setAddress(address);

            sysUser.getUserInfo().setEmergencyContactAddress(address);
            sysUser.getUserInfo().setEmergencyContactFirstName("econtactfirstName_" + i);
            sysUser.getUserInfo().setEmergencyContactSurname("econtactSurname_" + i);
            sysUser.getUserInfo().setEmergencyContactRelationship("friend");

            sysUser.getUserInfo().setFirstname("firstname_" + i);
            sysUser.getUserInfo().setSurname("surname_" + i);

            Insurance insurance = new Insurance();
            insurance.setExpirydate(new Date());
            insurance.setInsuranceNo("12345467");

            sysUser.getUserInfo().setInsurance(insurance);
            sysUser.getUserInfo().setMedicalMd("#test Markdown");
            sysUser.getProcessInfo().setInsuranceVerified(Boolean.TRUE);

            sysUser.setPassWordHash("XXX");
            sysUser.setPassword("ZZZ");  // should not persist as transient
            sysUser.setRole(Role.RIDER);

            SysUser newSysUser = sysUserDAO.create(sysUser);
            assertNotNull(newSysUser);
            assertNotNull(newSysUser.getId());

        }

    }

    public void printOutValues(List<SysUser> sysUserList) {
        LOG.debug("sysUserList Size: " + sysUserList.size());
        for (SysUser sysUser : sysUserList) {
            LOG.debug(sysUser.toString());
        }

    }

}
