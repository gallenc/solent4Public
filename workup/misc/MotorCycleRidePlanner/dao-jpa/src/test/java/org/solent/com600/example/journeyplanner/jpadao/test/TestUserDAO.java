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

public class TestUserDAO {

    private static final Logger LOG = LoggerFactory.getLogger(TestUserDAO.class);

    @Test
    public void testDAOFactory() {
        SysUserDAO userDAO = DAOFactory.getSysUserDAO();
        assertNotNull(userDAO);
    }

    @Test
    public void testFindUserRoles() {

        SysUserDAO userDAO = DAOFactory.getSysUserDAO();
        assertNotNull(userDAO);

        testCreateSysUsersDatabase();

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
        assertTrue(user1.getFirstname().equals(admin.getFirstname()));
        assertTrue(user1.getRole().equals(Role.ADMIN));

        // get third user by username
        SysUser user3 = userDAO.retrieveByUserName(anonName);
        assertTrue(user3.getId().equals(anon.getId()));
        assertTrue(user3.getFirstname().equals(anon.getFirstname()));
        assertTrue(user3.getRole().equals(Role.ANONYMOUS));

    }

    @Test
    public void testFindByNearMatch() {

        SysUserDAO userDAO = DAOFactory.getSysUserDAO();
        assertNotNull(userDAO);

        testCreateSysUsersDatabase();

        // now read back
        List<SysUser> sysUserList = userDAO.retrieveAll();
        printOutValues(sysUserList);

        // make user1 admin
        SysUser admin = sysUserList.get(0);
        assertNotNull(admin);
        String adminName = admin.getUserName();
        admin.setRole(Role.ADMIN);
        admin.setSurname("aaabbbccc");
        admin.setFirstname("dddeeefff");
        userDAO.update(admin);

        // find near match
        String surname = "abbbc";
        String firstname = "";
        List<SysUser> userListResult = userDAO.retrieveLikeMatching(surname, firstname);
        LOG.debug("test select surname=" + surname + " firstname=" + firstname + " userListResult:");
        printOutValues(userListResult);
        assertTrue(userListResult.size()==1);
        assertTrue(userListResult.get(0).getUserName().equals(admin.getUserName()));

    }

// utility methods
    public void testCreateSysUsersDatabase() {
        SysUserDAO userDAO = DAOFactory.getSysUserDAO();
        assertNotNull(userDAO);

        // empty database before test
        userDAO.deleteAll();
        List<SysUser> emptyList = userDAO.retrieveAll();
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

            sysUser.setAddress(address);

            sysUser.setEmergencyContactAddress(address);
            sysUser.setEmergencyContactFirstName("econtactfirstName_" + i);
            sysUser.setEmergencyContactSurname("econtactSurname_" + i);
            sysUser.setEmergencyContactRelationship("friend");

            sysUser.setFirstname("firstname_" + i);
            sysUser.setSurname("surname_" + i);

            Insurance insurance = new Insurance();
            insurance.setExpirydate(new Date());
            insurance.setInsuranceNo("12345467");
            insurance.setSeenByStaff(true);
            sysUser.setInsurance(insurance);

            sysUser.setPassWordHash("XXX");
            sysUser.setPasswordSalt("YYY");
            sysUser.setPassword("ZZZ");
            sysUser.setRole(Role.RIDER);
            sysUser.setMedicalMd("#test Markdown");

            userDAO.create(sysUser);

        }

    }

    public void printOutValues(List<SysUser> sysUserList) {
        LOG.debug("sysUserList Size: " + sysUserList.size());
        for (SysUser sysUser : sysUserList) {
            LOG.debug(sysUser.toString());
        }

    }

}
