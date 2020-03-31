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
import org.solent.com504.project.impl.auction.service.BankingServiceImpl;
import org.solent.com504.project.model.auction.dao.AuctionDAO;
import org.solent.com504.project.model.auction.dao.BidDAO;
import org.solent.com504.project.model.auction.dao.LotDAO;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.AuctionOrLotStatus;
import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.auction.message.MessageService;
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


    private MessageService messagesOut = new SimpleMessageServiceImpl();
    
    private BankingService bankService = new BankingServiceImpl();

    private AuctionService auctionService
            = new AuctionServiceImpl(partyDAO, auctionDAO, lotDao, bidDao, messagesOut, bankService);

    public MockServiceObjectFactory(){
    
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
