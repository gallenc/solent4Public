/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

import org.solent.com504.project.impl.auction.dao.MockServiceObjectFactory;
import org.solent.com504.project.model.auction.dao.AuctionDAO;
import org.solent.com504.project.model.auction.dao.BidDAO;
import org.solent.com504.project.model.auction.dao.LotDAO;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.Bid;
import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.auction.service.AuctionService;
import org.solent.com504.project.model.party.dao.PartyDAO;
import org.solent.com504.project.model.party.dto.Party;

/**
 *
 * @author cgallen
 */
public class ServiceObjectFactoryTest {

    final static Logger LOG = LogManager.getLogger(ServiceObjectFactoryTest.class);

    private static PartyDAO partyDAO;

    private static AuctionDAO auctionDAO;

    private static LotDAO lotDAO;

    private static BidDAO bidDAO;

    private static AuctionService auctionService;

    @Before
    public void init() {
        partyDAO = MockServiceObjectFactory.getPartyDAO();
        auctionDAO = MockServiceObjectFactory.getAuctionDAO();
        lotDAO = MockServiceObjectFactory.getLotDao();
        bidDAO = MockServiceObjectFactory.getBidDao();
        auctionService = MockServiceObjectFactory.getAuctionService();
    }

    @Test
    public void testObjectFactory() {
        assertNotNull(partyDAO);
        assertNotNull(auctionDAO);
        assertNotNull(lotDAO);
        assertNotNull(bidDAO);
        assertNotNull(auctionService);
    }

    @Test
    public void testPartyDao() {
        LOG.debug("testPartyDao() start");
        List<Party> partyList = partyDAO.findAll();
        for (Party party : partyList) {
            LOG.debug("     " + party);
        }
        LOG.debug("testPartyDao() end");
    }

    @Test
    public void testLotDao() {
        LOG.debug("testLotDao() start");
        List<Lot> lotList = lotDAO.findAll();
        for (Lot lot : lotList) {
            LOG.debug("     " + lot);
        }
        LOG.debug("testLotDao() end");
    }

    @Test
    public void testAuctionDao() {
        LOG.debug("testAuctionDao() start");
        List<Auction> auctionList = auctionDAO.findAll();
        for (Auction auction : auctionList) {
            LOG.debug("     " + auction);
        }
        LOG.debug("testAuctionDao() end");
    }

    @Test
    public void testBidDao() {
        LOG.debug("testBidDao() start");

        // should just print out log
        Bid bid = new Bid();
        bidDAO.save(bid);

        LOG.debug("testBidDao() end");
    }
}
