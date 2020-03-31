/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.impl.auction.dao.MockServiceObjectFactory;
import org.solent.com504.project.impl.auction.service.Initialise;
import org.solent.com504.project.model.auction.message.MessageService;
import org.solent.com504.project.model.auction.service.AuctionService;

/**
 *
 * @author cgallen
 */
public class WebObjectFactory {

    final static Logger LOG = LogManager.getLogger(WebObjectFactory.class);

    private static AuctionService auctionService = null;

    private static MockServiceObjectFactory serviceObjectFactory = null;

    private static MessageService messagesOut = null;

    public static AuctionService getAuctionService() {
        if (auctionService == null) {
            synchronized (WebObjectFactory.class) {
                if (auctionService == null) {
                    LOG.debug("web application auction service starting");
                    serviceObjectFactory = new MockServiceObjectFactory();
                    Initialise initialise = new Initialise(serviceObjectFactory);
                    initialise.init();
                    auctionService = serviceObjectFactory.getAuctionService();
                    messagesOut = serviceObjectFactory.getMessagesOut();
                }
            }
        }
        return auctionService;
    }

    public static MessageService getMessagesOut() {
        if (messagesOut == null) {
            synchronized (WebObjectFactory.class) {
                if (messagesOut == null) {
                    getAuctionService();
                }
            }
        }
        return messagesOut;
    }

}
