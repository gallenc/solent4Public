/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.dao;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.impl.auction.service.AuctionServiceMock2Impl;
import org.solent.com504.project.impl.auction.service.AuctionServiceMockImpl;
import org.solent.com504.project.model.auction.dao.AuctionDAO;
import org.solent.com504.project.model.auction.dao.BidDAO;
import org.solent.com504.project.model.auction.dao.LotDAO;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.AuctionStatus;
import org.solent.com504.project.model.auction.service.AuctionService;
import org.solent.com504.project.model.party.dao.PartyDAO;
import org.solent.com504.project.model.party.dto.Party;

/**
 *
 * @author cgallen
 */
public class MockServiceObjectFactory {

    final static Logger LOG = LogManager.getLogger(MockServiceObjectFactory.class);

    private static PartyDAO partyDAO = new PartyMockDAO();

    private static AuctionDAO auctionDAO = new AuctionMockDAO();

    private static LotDAO lotDao = new LotMockDAO(auctionDAO);

    private static BidDAO bidDao = new BidMockDAO();

    private static AuctionService auctionService 
            = new AuctionServiceMock2Impl(partyDAO,  auctionDAO,  lotDao, bidDao) ;
    

    // mock initialisation code 
    static {
        
        // will parse 2009-12-31 23:59:59
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        List<Party> partyList = Arrays.asList(new Party("great flowers", "co"),
                new Party("great flowers2", "co"),
                new Party("great flowers3", "co"));
        for (Party party : partyList) {
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
                auction.setAuctionStatus(AuctionStatus.SCHEDULED);
                auctionDAO.save(auction);
            } catch (Exception ex) {
                LOG.error("problem initialising :", ex);
            }
        }
    }

    public static AuctionService getAuctionService() {
        return auctionService;
    }

    public static PartyDAO getPartyDAO() {
        return partyDAO;
    }

    public static AuctionDAO getAuctionDAO() {
        return auctionDAO;
    }

    public static LotDAO getLotDao() {
        return lotDao;
    }

    public static BidDAO getBidDao() {
        return bidDao;
    }

}
