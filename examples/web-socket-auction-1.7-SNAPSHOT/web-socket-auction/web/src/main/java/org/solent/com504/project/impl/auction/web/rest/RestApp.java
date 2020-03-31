/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.web.rest;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationPath("/rest")
public class RestApp extends ResourceConfig {

     private static final Logger LOG = LoggerFactory.getLogger(RestApp.class);
    public RestApp() {
        LOG.debug("starting rest application");
        packages("org.solent.com504.project.impl.auction.web.rest");
    }
}