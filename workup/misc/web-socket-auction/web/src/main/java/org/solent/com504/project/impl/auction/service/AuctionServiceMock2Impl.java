/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.impl.auction.dao.AuctionMockDAO;
import org.solent.com504.project.impl.auction.dao.LotMockDAO;
import org.solent.com504.project.impl.auction.dao.PartyMockDAO;
import org.solent.com504.project.model.auction.dao.AuctionDAO;
import org.solent.com504.project.model.auction.dao.BidDAO;
import org.solent.com504.project.model.auction.dao.LotDAO;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.AuctionStatus;
import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.auction.service.AuctionService;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.party.dao.PartyDAO;

/**
 *
 * @author cgallen
 */
public class AuctionServiceMock2Impl implements AuctionService {
    
    final static Logger LOG = LogManager.getLogger(AuctionServiceMock2Impl.class);
    
    private PartyDAO partyDAO;
    
    private AuctionDAO auctionDAO;
    
    private LotDAO lotDao;
    
    private BidDAO bidDAO;

    public AuctionServiceMock2Impl(PartyDAO partyDAO, AuctionDAO auctionDAO, LotDAO lotDao, BidDAO bidDAO) {
        this.partyDAO = partyDAO;
        this.auctionDAO = auctionDAO;
        this.lotDao = lotDao;
        this.bidDAO = bidDAO;
    }
    
    
    

    // hashmap of lotuuid / active auctions
    private LinkedHashMap<String, Auction> activeAuctions = new LinkedHashMap();

    // normal run method
    @Override
    public synchronized void runAuctionSchedule() {
        Date currentTime = new Date();
        runAuctionSchedule(currentTime);
    }

    // used to inject dates in tests
    @Override
    public synchronized void runAuctionSchedule(Date currentTime) {
        LOG.debug("runAuctionSchedule(currentTime) for " + currentTime);
        
        Long currentTimeMs = currentTime.getTime();

        // todo add filter to dao to only find current auctions
        List<Auction> activeandScheduledAuctions = auctionDAO.findAll();

        // for all found scheduled auctions in database, add to active auctions
        // for all found finished auctions in scheduled auctions, save to database
        LOG.debug("iterating through "+activeandScheduledAuctions.size()
                + " activeandScheduledAuctions");
        for (Auction dbAuction : activeandScheduledAuctions) {
            
            switch (dbAuction.getAuctionStatus()) {
                
                case SCHEDULED:
                    // do not run auctions with no lots
                    if (dbAuction.getLots().isEmpty()) {
                        dbAuction.setAuctionStatus(AuctionStatus.FINISHED);
                        LOG.debug("setting scheduled auction finished because it has no lots " + dbAuction);
                        auctionDAO.save(dbAuction);
                    } else {
                        // if auction in database scheduled to start, add it to the active auctions list
                        if (dbAuction.getStartTime().getTime() <= currentTimeMs) {
                            LOG.debug("starting scheduled auction because starttime <= current time "
                                    + currentTime + "auction=" + dbAuction);
                            dbAuction.setAuctionStatus(AuctionStatus.ACTIVE);
                            auctionDAO.save(dbAuction);
                            activeAuctions.put(dbAuction.getAuctionuuid(), dbAuction);
                        } else {
                            LOG.debug("Not starting scheduled auction because starttime > current time "
                                    + currentTime + "auction=" + dbAuction);
                        }
                    }
                    
                    break;
                
                default:
                    LOG.debug("addAuctions not adding new dbAauction with status " + dbAuction.toString());
                
            }
        }

        // now check active auctions and update in database if needed
        for (Auction activeAuction : activeAuctions.values()) {
            
            switch (activeAuction.getAuctionStatus()) {
                case ACTIVE:

                    // find next active lot and check if time expired
                    // or find next schedulec lot and make a lot active
                    for (int i = 0; i < activeAuction.getLots().size(); i++) {
                        Lot lot = activeAuction.getLots().get(i);

                        // find the first active lot and check if time expired
                        if (AuctionStatus.ACTIVE == lot.getLotStatus()) {
                            // check if lot time has expired 
                            long lotDuration = activeAuction.getLotDuration();
                            if ((lot.getCurrentLotStart().getTime() + lotDuration) <= currentTimeMs) {
                                // lot expired
                                lot.setLotStatus(AuctionStatus.FINISHED);
                                LOG.debug("setting lot finished lot=" + lot);
                            }
                            LOG.debug("keeping lot running lot=" + lot);
                            // if time not expired do nothing keep running auction for this lot
                            break;

                            // else find the first scheduled lot and set active for bidding
                        } else if (AuctionStatus.SCHEDULED == lot.getLotStatus()) {
                            lot.setLotStatus(AuctionStatus.ACTIVE);
                            lot.setCurrentLotStart(currentTime);
                            lot.setLotDuraton(activeAuction.getLotDuration());
                            activeAuction.setCurrentLotIndex(i);
                            activeAuctions.put(activeAuction.getAuctionuuid(), activeAuction);
                            // may need to reattach object to persistance manager but might be OK ?
                            LOG.debug("setting new active lot in active auction =" + lot);
                            auctionDAO.save(activeAuction);
                            break;
                        } else {
                            // no scheduled or running lots so end auction
                            activeAuction.setAuctionStatus(AuctionStatus.FINISHED);
                            LOG.debug("no scheduled or running lots so finishing auction =" + activeAuction);
                            activeAuctions.remove(activeAuction.getAuctionuuid());
                            // may need to reattach object to persistance manager but might be OK ?
                            auctionDAO.save(activeAuction);
                        }
                        
                    }
                    
                    break;
                
                default:
                    LOG.debug("active auctions has auction with status " + activeAuction.toString());
                
            }
        }
        
    }
    
    @Override
    public String registerForAuction(String auctionuuid, String partyUuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Auction> getAuctionList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Auction getAuctionDetails(String auctionuuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Lot getLotDetails(String lotuuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Lot> getAuctionLots(String auctionuuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Lot addLotToAuction(String auctionuuid, String selleruuid, Flower flowertype, Double reserveprice, Long quantity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Message bidForLot(String bidderuuid, String auctionuuid, String authKey, String lotuuid, Double value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
