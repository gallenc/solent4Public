/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com600.example.journeyplanner.service;

import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.com600.example.journeyplanner.jpadao.DAOFactory;
import org.solent.com600.example.journeyplanner.model.RideoutDAO;
import org.solent.com600.example.journeyplanner.model.Role;
import org.solent.com600.example.journeyplanner.model.ServiceFacade;
import org.solent.com600.example.journeyplanner.model.ServiceFactory;
import org.solent.com600.example.journeyplanner.model.SysUser;
import org.solent.com600.example.journeyplanner.model.SysUserDAO;

/**
 *
 * @author gallenc
 */
public class ServiceFactoryImpl implements ServiceFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceFactoryImpl.class);

    public static final String DEFAULT_ADMIN_USER = "admin";
    public static final String DEFAULT_ADMIN_USER_PASSWORD = "admin";
    public static final String DEFAULT_ANONYMOUS_USER = "anonymous";
    public static final String DEFAULT_ANONYMOUS_USER_PASSWORD = "anonymous";

    private static ServiceFacadeImpl serviceFacade = null;

    static {
        serviceFacade = new ServiceFacadeImpl();
        
        RideoutDAO rideoutDAO = DAOFactory.getRideoutDAO();
        serviceFacade.setRideoutDAO(rideoutDAO);
        
        SysUserDAO sysUserDao = DAOFactory.getSysUserDAO();

        // add admin user if not present
        SysUser adminUser1 = null;
        try {
            adminUser1 = sysUserDao.retrieveByUserName(DEFAULT_ADMIN_USER);
        } catch (NoResultException nrex) {
        }
        if (adminUser1 == null) {
            LOG.warn("admin user not present in database. Creating default admin user. You should change password");
            adminUser1 = new SysUser();
            adminUser1.setUserName(DEFAULT_ADMIN_USER);
            adminUser1.setPassWordHash(PasswordUtils.hashPassword(DEFAULT_ADMIN_USER_PASSWORD));
            adminUser1.getUserInfo().setFirstname(DEFAULT_ADMIN_USER);
            adminUser1.getUserInfo().setSurname(DEFAULT_ADMIN_USER);
            adminUser1.setRole(Role.ADMIN);
            sysUserDao.create(adminUser1);
        }
        // add anonymous user if not present
        SysUser anonUser1 = null;
        try {
            anonUser1 = sysUserDao.retrieveByUserName(DEFAULT_ANONYMOUS_USER);
        } catch (NoResultException nrex) {
        }
        if (anonUser1 == null) {
            LOG.warn("admin user not present in database. Creating default admin user. You should change password");
            anonUser1 = new SysUser();
            anonUser1.setUserName(DEFAULT_ANONYMOUS_USER);
            anonUser1.setPassWordHash(PasswordUtils.hashPassword(DEFAULT_ANONYMOUS_USER_PASSWORD));
            anonUser1.getUserInfo().setFirstname(DEFAULT_ANONYMOUS_USER);
            anonUser1.getUserInfo().setSurname(DEFAULT_ANONYMOUS_USER);
            anonUser1.setRole(Role.ANONYMOUS);
            sysUserDao.create(anonUser1);
        }
        serviceFacade.setSysUserDAO(sysUserDao);
    }

    @Override
    public ServiceFacade getServiceFacade() {
        return serviceFacade;
    }

}
