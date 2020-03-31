/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.web;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.impl.auction.service.AuctionServiceImpl;
import org.solent.com504.project.model.auction.service.AuctionService;

// see  https://stackoverflow.com/questions/4691132/how-to-run-a-background-task-in-a-servlet-based-web-application
@WebListener
public class BackgroundJobManager implements ServletContextListener {
    final static Logger LOG = LogManager.getLogger(BackgroundJobManager.class);

    private ScheduledExecutorService scheduler;
    
    private AuctionService auctionService = WebObjectFactory.getAuctionService();

    @Override
    public void contextInitialized(ServletContextEvent event) {
        LOG.info("starting auction scheduler ");
        scheduler = Executors.newSingleThreadScheduledExecutor();
        //scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
        scheduler.scheduleAtFixedRate(auctionService, 20, 20, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        LOG.info("shutting down auction scheduler");
        scheduler.shutdownNow();
    }

}