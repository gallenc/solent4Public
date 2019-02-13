/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com600.example.journeyplanner.service;

import org.solent.com600.example.journeyplanner.jpadao.DAOFactory;
import org.solent.com600.example.journeyplanner.model.ServiceFacade;
import org.solent.com600.example.journeyplanner.model.ServiceFactory;
import org.solent.com600.example.journeyplanner.model.SysUserDAO;

/**
 *
 * @author gallenc
 */
public class ServiceFactoryImpl implements ServiceFactory{

    private static ServiceFacadeImpl serviceFacade = null;
            
    static {
        serviceFacade = new ServiceFacadeImpl();
        SysUserDAO sysUserDao = DAOFactory.getSysUserDAO();
        serviceFacade.setSysUserDAO(sysUserDao);
    }
    
    @Override
    public ServiceFacade getServiceFacade() {        
        return serviceFacade;    
    }

}
