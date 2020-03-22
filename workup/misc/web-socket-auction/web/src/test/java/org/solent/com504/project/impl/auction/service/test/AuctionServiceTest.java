/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service.test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
public class AuctionServiceTest {

    final static Logger LOG = LogManager.getLogger(AuctionServiceTest.class);

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
    public void testBidsFactory() {
        // will parse 2009-12-31 23:59:59
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        List<String> constantDateStrings = Arrays.asList("2020-01-1 09:00",
                "2020-01-1 09:30",
                "2020-01-1 10:00",
                "2020-01-1 10:30",
                "2020-01-1 11:00",
                "2020-01-1 11:30",
                "2020-01-1 12:00",
                "2020-01-1 12:30",
                "2020-01-1 13:00",
                "2020-01-1 13:30");
        for (String datestr : constantDateStrings) {
            try {
                Date time = format.parse(datestr);
                auctionService.runAuctionSchedule(time);

            } catch (Exception ex) {
                LOG.error("problem initialising :", ex);
            }
        }

    }

}
