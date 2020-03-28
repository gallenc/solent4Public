/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
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
import org.solent.com504.project.model.auction.dto.AuctionOrLotStatus;
import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.auction.dto.MessageType;
import org.solent.com504.project.model.auction.message.MessageListener;
import org.solent.com504.project.model.auction.message.MessageService;
import org.solent.com504.project.model.auction.service.AuctionService;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.party.dao.PartyDAO;
import org.solent.com504.project.model.party.dto.Party;

/**
 *
 * @author cgallen
 */
public class AuctionServiceTest implements MessageListener {

    final static Logger LOG = LogManager.getLogger(AuctionServiceTest.class);

    private PartyDAO partyDAO;

    private AuctionDAO auctionDAO;

    private LotDAO lotDAO;

    private BidDAO bidDAO;

    private AuctionService auctionService;

    private MessageService messagesOut;

    // will parse 2009-12-31 23:59:59
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private Party testBuyer = null;
    private Party testSeller = null;
    private List<Message> messagesReceived = new ArrayList();
    private Auction testAuction1 = null;
    private Auction testAuction2 = null;
    private Auction testAuction3 = null;

    @Before
    public void init() {
        messagesReceived.clear();

        MockServiceObjectFactory mockServiceObjectFactory = new MockServiceObjectFactory();
        partyDAO = mockServiceObjectFactory.getPartyDAO();
        auctionDAO = mockServiceObjectFactory.getAuctionDAO();
        lotDAO = mockServiceObjectFactory.getLotDao();
        bidDAO = mockServiceObjectFactory.getBidDao();
        auctionService = mockServiceObjectFactory.getAuctionService();

        // register for messsages
        messagesOut = mockServiceObjectFactory.getMessagesOut();
        messagesOut.registerForMessages(this);

        // pre populate test data
        LOG.debug("\n*********************************  INITIALISING MOCK DATA IN SERVICE OBJECT FACTORY");

        List<Party> partyList = Arrays.asList(new Party("great flowers", "co"),
                new Party("great flowers2", "co"),
                new Party("great flowers3", "co"));
        for (Party party : partyList) {
            partyDAO.save(party);
        }

        testBuyer = partyDAO.findAll().get(0); //first in list

        List<Party> sellerPartyList = Arrays.asList(new Party("grow great flowers", "co"),
                new Party("grow great flowers2", "co"),
                new Party("grow great flowers3", "co"));
        for (Party party : sellerPartyList) {
            partyDAO.save(party);
        }

        List<Party> pList = partyDAO.findAll();
        testSeller = pList.get(pList.size() - 1); // last in list

        List<String> constantDateStrings = Arrays.asList("2020-01-1 09:00",
                "2020-01-1 10:00",
                "2020-01-1 11:00",
                "2020-01-1 12:00",
                "2020-01-1 13:00");
        for (String datestr : constantDateStrings) {
            try {
                Date time = format.parse(datestr);
                Auction auction = new Auction(time, "auction at " + datestr);
                auction.setAuctionStatus(AuctionOrLotStatus.SCHEDULED);

                // add lots to auction
                for (int i = 0; i < 3; i++) {
                    Lot lot = new Lot();
                    Flower flowerType = new Flower();
                    flowerType.setCommonName("rose");
                    flowerType.setSymbol("AAAAA");
                    lot.setFlowerType(flowerType);
                    Double reservePrice = 10000.00;
                    lot.setReservePrice(reservePrice);
                    Party seller;
                    lot.setSeller(sellerPartyList.get(i));
                    auction.getLots().add(lot);
                }
                auctionDAO.save(auction);
            } catch (Exception ex) {
                LOG.error("problem initialising :", ex);
            }

        }

        // get 2 test auctions
        List<Auction> alist = null;
        try {
            alist = auctionDAO.findActiveOrScheduledBefore(format.parse("2020-01-1 11:30"));
        } catch (ParseException ex) {
            LOG.error("problem parsing ", ex);
        }
        assertEquals(3, alist.size());
        testAuction1 = alist.get(0);
        testAuction2 = alist.get(1);
        testAuction3 = alist.get(2);

        LOG.debug("\n*********************************  FINISHED INITIALISING MOCK DATA IN SERVICE OBJECT FACTORY");
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
    public void testAuction() {
        // test messages being received
        assertTrue(messagesReceived.isEmpty());
        messagesOut.broadcastMessage(new Message());
        assertEquals(1, messagesReceived.size());
        messagesReceived.clear();

        LOG.debug("test cant add lot to auction unless registered");
        String auctionuuid = testAuction1.getAuctionuuid();
        String lotuuid = testAuction1.getLots().get(0).getLotuuid();
        String selleruuid = testSeller.getUuid();
        Flower flowertype = new Flower();
        double reserveprice = 100;
        long quantity = 5000;

        try {
            auctionService.addLotToAuction(auctionuuid, selleruuid, flowertype, reserveprice, quantity);
            fail("should throw exception because selleruuid not registered for auction");
        } catch (IllegalArgumentException ex) {
            LOG.debug("expected exception :" + ex.getMessage());
        }

        String sellerAuthKey = auctionService.registerForAuction(auctionuuid, selleruuid);
        assertNotNull(sellerAuthKey);

        try {
            auctionService.addLotToAuction(auctionuuid, selleruuid, flowertype, reserveprice, quantity);
        } catch (IllegalArgumentException ex) {
            fail("should not throw an exception if selleruuid registered for auction");
        }

        String buyeruuid = testBuyer.getUuid();

        Message message = new Message();
        message.setAuctionuuid(auctionuuid);
        message.setAuthKey("wrong auth key");
        message.setBidderuuid(buyeruuid);
        message.setLotuuid(lotuuid);
        message.setValue(reserveprice);
        message.setMessageType(MessageType.ERROR);

        LOG.debug("test send wrong message type");
        Message immediateReply = auctionService.onMessageReceived(message);
        assertNotNull(immediateReply);
        assertEquals(MessageType.ERROR, immediateReply.getMessageType());
        LOG.debug("reply message:" + immediateReply);

        LOG.debug("send bid from unregistered bidder");
        message.setMessageType(MessageType.BID);
        immediateReply = auctionService.onMessageReceived(message);
        assertNotNull(immediateReply);
        assertEquals(MessageType.ERROR, immediateReply.getMessageType());
        LOG.debug("reply message:" + immediateReply);

        LOG.debug("send bid from registered bidder but lot is not active");
        String buyerAuthKey = auctionService.registerForAuction(auctionuuid, buyeruuid);
        assertNotNull(buyerAuthKey);
        message.setAuthKey(buyerAuthKey);

        message.setMessageType(MessageType.BID);
        immediateReply = auctionService.onMessageReceived(message);
        assertNotNull(immediateReply);
        message.setAuthKey(buyerAuthKey);
        assertEquals(MessageType.ERROR, immediateReply.getMessageType());
        LOG.debug("reply message:" + immediateReply);

        LOG.debug("start  auctions at 2020-01-1 09:30");
        Date time = null;
        try {
            time = format.parse("2020-01-1 09:30");
        } catch (ParseException ex) {
        }

        assertTrue(messagesReceived.isEmpty());
        assertEquals(AuctionOrLotStatus.SCHEDULED, testAuction1.getAuctionStatus());
        assertEquals(AuctionOrLotStatus.SCHEDULED, testAuction2.getAuctionStatus());
        assertEquals(AuctionOrLotStatus.SCHEDULED, testAuction3.getAuctionStatus());

        auctionService.runAuctionSchedule(time);

        assertEquals(AuctionOrLotStatus.ACTIVE, testAuction1.getAuctionStatus());
        assertEquals(AuctionOrLotStatus.SCHEDULED, testAuction2.getAuctionStatus());
        assertEquals(AuctionOrLotStatus.SCHEDULED, testAuction3.getAuctionStatus());

        assertEquals(2, messagesReceived.size());
        assertEquals(MessageType.START_OF_AUCTION, messagesReceived.get(0).getMessageType());
        assertEquals(MessageType.START_OF_LOT, messagesReceived.get(1).getMessageType());
        String currentLotuuid = messagesReceived.get(1).getLotuuid();
        assertNotNull(currentLotuuid);
        messagesReceived.clear();

        Lot currentLot = lotDAO.findByLotuuid(currentLotuuid);
        assertNotNull(currentLot);

        LOG.debug("bidding less than reserve price="+currentLot.getReservePrice());
        message.setValue(currentLot.getReservePrice() - 100);
        immediateReply = auctionService.onMessageReceived(message);
        assertNotNull(immediateReply);
        assertEquals(MessageType.ERROR, immediateReply.getMessageType());
        LOG.debug("reply message:" + immediateReply);

        for (int i = 1; i < 6; i++) {
            double price = currentLot.getReservePrice() * i;
            LOG.debug(" bid value " + price);
            message.setValue(price);
            immediateReply = auctionService.onMessageReceived(message);
            assertNull(immediateReply);
        }
        // check received 5 new highest bid messages
        assertEquals(5, messagesReceived.size());
        for (Message m : messagesReceived) {
            assertEquals(MessageType.NEW_HIGHEST_BID, m.getMessageType());
        }
        messagesReceived.clear();

        // check that same price is not double  bid and no message sent for low bid
        LOG.debug(" same bid value " + message.getValue());
        immediateReply = auctionService.onMessageReceived(message);
        assertNull(immediateReply);
        assertTrue(messagesReceived.isEmpty());

    }

    // @Test
    public void testAuctionSchedule() {
        LOG.debug("\n*********************************  START OF AUCTION SCHEDULE TEST ");
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
                for (int i = 0; i < 4; i++) {
                    Long timems = time.getTime();
                    timems = timems + (i * 1000 * 60 * 10); // ten minutes
                    time = new Date(timems);
                    LOG.debug("\n*********************************  NEW TIME auctionService.runAuctionSchedule(" + format.format(time) + ")");
                    auctionService.runAuctionSchedule(time);
                }

            } catch (Exception ex) {
                LOG.error("problem incrementing time :", ex);
            }
        }
        LOG.debug("\n*********************************  END OF AUCTION SCHEDULE TEST ");
    }

    @Override
    public Message onMessageReceived(Message message) {

        LOG.debug("received message: " + message);
        messagesReceived.add(message);
        return null;
    }

}
