/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com600.example.journeyplanner.service;

import javax.naming.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.com600.example.journeyplanner.jpadao.DAOFactory;
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

    private static ServiceFacadeImpl serviceFacade = null;

    static {
        serviceFacade = new ServiceFacadeImpl();
        SysUserDAO sysUserDao = DAOFactory.getSysUserDAO();

        // add admin user if not present
        if (sysUserDao.retrieveByUserName("admin") == null) {
            LOG.warn("admin user not present in database. Created default admin user. You should change password");
            SysUser adminUser1 = new SysUser();
            adminUser1.setUserName(DEFAULT_ADMIN_USER);
            adminUser1.setPassword(DEFAULT_ADMIN_USER_PASSWORD);
            adminUser1.getUserInfo().setFirstname("Default Admin User First Name");
            adminUser1.getUserInfo().setSurname("Default Admin User Surname");
            adminUser1.setRole(Role.ADMIN);
  
            sysUserDao.create(adminUser1);
         
        }

        serviceFacade.setSysUserDAO(sysUserDao);
    }

    @Override
    public ServiceFacade getServiceFacade() {
        return serviceFacade;
    }

}
