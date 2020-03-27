/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.dao;

import org.solent.com504.project.impl.auction.service.SimpleMessageServiceImpl;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.impl.auction.service.AuctionServiceImpl;
import org.solent.com504.project.impl.auction.service.AuctionServiceMockImpl;
import org.solent.com504.project.impl.auction.service.BankingServiceImpl;
import org.solent.com504.project.model.auction.dao.AuctionDAO;
import org.solent.com504.project.model.auction.dao.BidDAO;
import org.solent.com504.project.model.auction.dao.LotDAO;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.AuctionOrLotStatus;
import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.auction.dto.MessageService;
import org.solent.com504.project.model.auction.service.AuctionService;
import org.solent.com504.project.model.auction.service.BankingService;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.party.dao.PartyDAO;
import org.solent.com504.project.model.party.dto.Party;

/**
 *
 * @author cgallen
 */
public class MockServiceObjectFactory {

    final static Logger LOG = LogManager.getLogger(MockServiceObjectFactory.class);

    private PartyDAO partyDAO = new PartyMockDAO();

    private AuctionDAO auctionDAO = new AuctionMockDAO();

    private LotDAO lotDao = new LotMockDAO(auctionDAO);

    private BidDAO bidDao = new BidMockDAO();

    private MessageService messagesIn = new SimpleMessageServiceImpl();

    private MessageService messagesOut = new SimpleMessageServiceImpl();
    
    private BankingService bankService = new BankingServiceImpl();

    private AuctionService auctionService
            = new AuctionServiceImpl(partyDAO, auctionDAO, lotDao, bidDao, messagesIn, messagesOut, bankService);

    public MockServiceObjectFactory(){
    // mock initialisation code 
   
        LOG.debug("\n*********************************  INITIALISING MOCK DATA IN SERVICE OBJECT FACTORY");
        // will parse 2009-12-31 23:59:59
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        List<Party> partyList = Arrays.asList(new Party("great flowers", "co"),
                new Party("great flowers2", "co"),
                new Party("great flowers3", "co"));
        for (Party party : partyList) {
            partyDAO.save(party);
        }

        List<Party> sellerPartyList = Arrays.asList(new Party("grow great flowers", "co"),
                new Party("grow great flowers2", "co"),
                new Party("grow great flowers3", "co"));
        for (Party party : sellerPartyList) {
            partyDAO.save(party);
        }

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
        LOG.debug("\n*********************************  FINISHED INITIALISING MOCK DATA IN SERVICE OBJECT FACTORY");
    }

    public PartyDAO getPartyDAO() {
        return partyDAO;
    }

    public void setPartyDAO(PartyDAO partyDAO) {
        this.partyDAO = partyDAO;
    }

    public AuctionDAO getAuctionDAO() {
        return auctionDAO;
    }

    public void setAuctionDAO(AuctionDAO auctionDAO) {
        this.auctionDAO = auctionDAO;
    }

    public LotDAO getLotDao() {
        return lotDao;
    }

    public void setLotDao(LotDAO lotDao) {
        this.lotDao = lotDao;
    }

    public BidDAO getBidDao() {
        return bidDao;
    }

    public void setBidDao(BidDAO bidDao) {
        this.bidDao = bidDao;
    }

    public MessageService getMessagesIn() {
        return messagesIn;
    }

    public void setMessagesIn(MessageService messagesIn) {
        this.messagesIn = messagesIn;
    }

    public MessageService getMessagesOut() {
        return messagesOut;
    }

    public void setMessagesOut(MessageService messagesOut) {
        this.messagesOut = messagesOut;
    }

    public AuctionService getAuctionService() {
        return auctionService;
    }

    public void setAuctionService(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

  

}
