/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com600.example.journeyplanner.web;

import org.solent.com600.example.journeyplanner.model.ServiceFactory;
import org.solent.com600.example.journeyplanner.service.ServiceFactoryImpl;

/**
 *
 * @author cgallen
 */
public class WebObjectFactory {

    private static ServiceFactory serviceFactory = null;

    public static ServiceFactory getServiceFactory() {

        if (serviceFactory == null) {
            synchronized (WebObjectFactory.class) {
                if (serviceFactory == null) {
                    serviceFactory = new ServiceFactoryImpl();
                }
            }
        }
        return serviceFactory;
    }
}
