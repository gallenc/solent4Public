/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.impl.auction.dao.MockServiceObjectFactory;
import org.solent.com504.project.model.auction.dao.AuctionDAO;
import org.solent.com504.project.model.auction.dao.BidDAO;
import org.solent.com504.project.model.auction.dao.LotDAO;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.AuctionOrLotStatus;
import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.auction.service.AuctionService;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.party.dao.PartyDAO;
import org.solent.com504.project.model.party.dto.Party;

/**
 *
 * @author cgallen
 */
public class Initialise {

    final static Logger LOG = LogManager.getLogger(Initialise.class);

    public final static long LOT_DURATION = 1000 * 60 * 1L; // 1 minute
    public final static int LOTS_IN_AUCTION = 5;
    public final static int NUMBER_OF_AUCTIONS = 20;

    private PartyDAO partyDAO;

    private AuctionDAO auctionDAO;

    private LotDAO lotDAO;

    private BidDAO bidDAO;

    private AuctionService auctionService;

    public Initialise(MockServiceObjectFactory mockServiceObjectFactory) {
        partyDAO = mockServiceObjectFactory.getPartyDAO();
        auctionDAO = mockServiceObjectFactory.getAuctionDAO();
        lotDAO = mockServiceObjectFactory.getLotDao();
        bidDAO = mockServiceObjectFactory.getBidDao();
        auctionService = mockServiceObjectFactory.getAuctionService();
    }

    public void init() {

        // pre populate test data
        LOG.debug("\n*********************************  INITIALISING MOCK DATA IN SERVICE OBJECT FACTORY");

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
        
        Long timems = new Date().getTime();

        for (int x=0; x<NUMBER_OF_AUCTIONS; x++) {
      
            timems=timems+(x * LOT_DURATION * LOTS_IN_AUCTION );
            Date time = new Date(timems);
               
                Auction auction = new Auction(time, "auction at " + time);

                auction.setLotDuration(LOT_DURATION);
                auction.setAuctionStatus(AuctionOrLotStatus.SCHEDULED);

                // add lots to auction
                for (int i = 0; i < LOTS_IN_AUCTION; i++) {
                    Lot lot = new Lot();
                    Flower flowerType = new Flower();
                    flowerType.setCommonName("rose");
                    flowerType.setSymbol("AAAAA");
                    lot.setFlowerType(flowerType);
                    Double reservePrice = 10000.00;
                    lot.setReservePrice(reservePrice);
                    lot.setSeller(sellerPartyList.get(i % sellerPartyList.size()));
                    auction.getLots().add(lot);
                }
                auctionDAO.save(auction);

        }
    }

}
