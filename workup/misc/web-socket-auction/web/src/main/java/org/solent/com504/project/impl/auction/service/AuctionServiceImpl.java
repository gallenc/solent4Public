/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.auction.dao.AuctionDAO;
import org.solent.com504.project.model.auction.dao.BidDAO;
import org.solent.com504.project.model.auction.dao.LotDAO;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.AuctionOrLotStatus;
import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.auction.dto.MessageListener;
import org.solent.com504.project.model.auction.service.AuctionService;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.party.dao.PartyDAO;
import org.solent.com504.project.model.auction.dto.MessageService;
import org.solent.com504.project.model.auction.dto.MessageType;

/**
 *
 * @author cgallen
 */
public class AuctionServiceImpl implements AuctionService, MessageListener {

    final static Logger LOG = LogManager.getLogger(AuctionServiceImpl.class);

    private PartyDAO partyDAO;

    private AuctionDAO auctionDAO;

    private LotDAO lotDao;

    private BidDAO bidDAO;

    private MessageService messagesIn;

    private MessageService messagesOut;

    public AuctionServiceImpl(PartyDAO partyDAO, AuctionDAO auctionDAO, LotDAO lotDao, BidDAO bidDAO, MessageService messagesIn, MessageService messagesOut) {
        this.partyDAO = partyDAO;
        this.auctionDAO = auctionDAO;
        this.lotDao = lotDao;
        this.bidDAO = bidDAO;
        this.messagesIn = messagesIn;
        this.messagesOut = messagesOut;

        messagesIn.registerForMessages(this);

    }

    // concurrent hashmap of lotuuid / active lots
    //private ConcurrentHashMap<String, Auction> activeAuctions = new ConcurrentHashMap();
    private ConcurrentHashMap<String, Lot> activeLots = new ConcurrentHashMap();

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
        // for all found finished auctions in active auctions, save to database
        LOG.debug("iterating through " + activeandScheduledAuctions.size()
                + " activeandScheduledAuctions");
        for (Auction dbAuction : activeandScheduledAuctions) {

            switch (dbAuction.getAuctionStatus()) {

                case SCHEDULED:
                    // do not run auctions with no lots
                    if (dbAuction.getLots().isEmpty()) {
                        dbAuction.setAuctionStatus(AuctionOrLotStatus.FINISHED);

                        String debugMessage = "setting scheduled auction finished because it has no lots " + dbAuction.getAuctionuuid();
                        LOG.debug(debugMessage);
                        auctionDAO.save(dbAuction);

                        Message message = new Message();
                        message.setMessageType(MessageType.LOT_OR_AUCTION_CLOSED);
                        message.setAuctionuuid(dbAuction.getAuctionuuid());
                        message.setDebugMessage(debugMessage);
                        messagesOut.broadcastMessage(message);

                    } else {
                        // if auction in database scheduled to start, set active
                        if (dbAuction.getStartTime().getTime() <= currentTimeMs) {
                            String debugMessage = "starting scheduled auction because starttime <= current time "
                                    + currentTime + "auction=" + dbAuction.getAuctionuuid() + " start time:" + dbAuction.getStartTime();
                            LOG.debug(debugMessage);
                            dbAuction.setAuctionStatus(AuctionOrLotStatus.ACTIVE);
                            auctionDAO.save(dbAuction);

                            Message message = new Message();
                            message.setMessageType(MessageType.START_OF_AUCTION);
                            message.setAuctionuuid(dbAuction.getAuctionuuid());
                            message.setDebugMessage(debugMessage);
                            messagesOut.broadcastMessage(message);

                        } else {
                            LOG.debug("Not starting scheduled auction because starttime > current time "
                                    + currentTime + "auction=" + dbAuction.getAuctionuuid() + " start time:" + dbAuction.getStartTime());
                        }
                    }

                //break; no break because we are now treating the auction as active so drop through to ACTIVE case
                case ACTIVE:

                    // find next active lot and check if time expired
                    // or find next scheduled lot and make a lot active
                    boolean auctionHasActivelots = false;

                    for (int i = 0; i < dbAuction.getLots().size(); i++) {
                        Lot lot = dbAuction.getLots().get(i);

                        // find the first active lot and check if time expired
                        if (AuctionOrLotStatus.ACTIVE == lot.getLotStatus()) {
                            // check if lot time has expired 
                            long lotDuration = dbAuction.getLotDuration();
                            if ((lot.getCurrentLotStart().getTime() + lotDuration) <= currentTimeMs) {
                                // lot expired
                                lot.setLotStatus(AuctionOrLotStatus.FINISHED);
                                activeLots.remove(lot.getLotuuid());

                                String debugMessage = "lot duraton finished lot=" + lot.getLotuuid();
                                LOG.debug(debugMessage);

                                Message message = new Message();
                                message.setMessageType(MessageType.LOT_WITHDRAWN);
                                message.setAuctionuuid(dbAuction.getAuctionuuid());
                                message.setDebugMessage(debugMessage);
                                messagesOut.broadcastMessage(message);

                            } else {
                                // if time not expired do nothing keep running lot for this auction

                                LOG.debug("keeping lot running lot=" + lot.getLotuuid()
                                        + " lotindex=" + dbAuction.getCurrentLotIndex()
                                        + " in active auction =" + dbAuction.getAuctionuuid());
                                auctionHasActivelots = true;

                            }

                            break;

                            // else find the next scheduled lot and set active for bidding
                        } else if (AuctionOrLotStatus.SCHEDULED == lot.getLotStatus()) {

                            lot.setLotStatus(AuctionOrLotStatus.ACTIVE);
                            lot.setCurrentLotStart(currentTime);
                            lot.setLotDuraton(dbAuction.getLotDuration());
                            dbAuction.setCurrentLotIndex(i);

                            activeLots.put(lot.getLotuuid(), lot);

                            String debugMessage = "setting new active lot " + lot.getLotuuid()
                                    + " lotindex=" + dbAuction.getCurrentLotIndex()
                                    + " in active auction =" + dbAuction.getAuctionuuid();
                            LOG.debug(debugMessage);

                            // may need to reattach object to persistance manager but might be OK ?
                            auctionDAO.save(dbAuction);
                            auctionHasActivelots = true;

                            Message message = new Message();
                            message.setMessageType(MessageType.START_OF_LOT);
                            message.setAuctionuuid(dbAuction.getAuctionuuid());
                            message.setDebugMessage(debugMessage);
                            messagesOut.broadcastMessage(message);

                            break;
                        } else {
                            // do nothing if no lot not scheduled or active
                        }

                    }

                    if (!auctionHasActivelots) {
                        // no scheduled or running lots so end auction
                        dbAuction.setAuctionStatus(AuctionOrLotStatus.FINISHED);
                        String debugMessage = "no scheduled or running lots so finishing auction =" + dbAuction.getAuctionuuid();
                        LOG.debug(debugMessage);
                        //activeAuctions.remove(activeAuction.getAuctionuuid());
                        // may need to reattach object to persistance manager but might be OK ?
                        auctionDAO.save(dbAuction);

                        Message message = new Message();
                        message.setMessageType(MessageType.START_OF_LOT);
                        message.setAuctionuuid(dbAuction.getAuctionuuid());

                        message.setDebugMessage(debugMessage);
                        messagesOut.broadcastMessage(message);
                    }

                    break;

                default:
                    LOG.debug("not processing default active auctions has auction " + dbAuction.getAuctionuuid()
                            + "with status " + dbAuction.getAuctionStatus());

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

    @Override
    public void onMessageReceived(Message message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
