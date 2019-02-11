package org.solent.com600.example.journeyplanner.jpadao.test;

import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;

import org.junit.Test;
import org.solent.com600.example.journeyplanner.jpadao.DAOFactory;
import org.solent.com600.example.journeyplanner.model.Address;
import org.solent.com600.example.journeyplanner.model.Insurance;
import org.solent.com600.example.journeyplanner.model.Role;
import org.solent.com600.example.journeyplanner.model.SysUser;
import org.solent.com600.example.journeyplanner.model.SysUserDAO;

public class TestUserDAO {

    @Test
    public void testDAOFactory() {
        SysUserDAO userDAO = DAOFactory.getSysUserDAO();
        assertNotNull(userDAO);
    }

    @Test
    public void testCreateSysUser() {
        SysUserDAO userDAO = DAOFactory.getSysUserDAO();
        assertNotNull(userDAO);

        // populate sysUser with data
        SysUser sysUser = new SysUser();

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
        
        sysUser.setFirstname("John");
        sysUser.setSurname("Doe");

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
        
        // now read back
        List<SysUser> sysUserList = userDAO.retrieveAll();
                for (SysUser employee : sysUserList) {
            System.out.println(employee);
        }
        System.out.println("Size: " + sysUserList.size());

    }

}
