/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.solent.com504.project.model.auction.dao.AuctionDAO;
import org.solent.com504.project.model.auction.dao.BidDAO;
import org.solent.com504.project.model.auction.dao.LotDAO;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.AuctionOrLotStatus;
import org.solent.com504.project.model.auction.dto.AuctionType;
import org.solent.com504.project.model.auction.dto.Bid;
import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.auction.message.MessageListener;
import org.solent.com504.project.model.auction.service.AuctionService;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.party.dao.PartyDAO;
import org.solent.com504.project.model.auction.message.MessageService;
import org.solent.com504.project.model.auction.dto.MessageType;
import org.solent.com504.project.model.auction.service.BankingService;
import org.solent.com504.project.model.party.dto.Party;

/**
 *
 * @author cgallen
 */
public class AuctionServiceImpl implements AuctionService, MessageListener {

    final static Logger LOG = LogManager.getLogger(AuctionServiceImpl.class);

    private PartyDAO partyDAO;

    private AuctionDAO auctionDAO;

    private LotDAO lotDAO;

    private BidDAO bidDAO;

    private MessageService messagesOut;

    private BankingService bankingService;

    private int noSymultaneousAuctions = 2;

    public AuctionServiceImpl(PartyDAO partyDAO, AuctionDAO auctionDAO, LotDAO lotDao, BidDAO bidDAO, MessageService messagesOut, BankingService bankingService) {
        this.partyDAO = partyDAO;
        this.auctionDAO = auctionDAO;
        this.lotDAO = lotDao;
        this.bidDAO = bidDAO;
        this.messagesOut = messagesOut;
        this.bankingService = bankingService;

    }

    // concurrent hashmap of lotuuid / active lots
    //private ConcurrentHashMap<String, Auction> activeAuctions = new ConcurrentHashMap();
    private ConcurrentHashMap<String, Lot> activeLots = new ConcurrentHashMap();

    @Override
    public synchronized String registerForAuction(String auctionuuid, String partyUuid) {
        LOG.debug("registerForAuction called auctionuuid," + auctionuuid + " partyUuid" + partyUuid);

        // would use DAO here
        Auction auction = auctionDAO.findByAuctionuuid(auctionuuid);
        if (auction == null) {
            throw new IllegalArgumentException("cannot find auction with uuid=" + auctionuuid);
        }

        Party party = partyDAO.findByUuid(partyUuid);
        if (party == null) {
            throw new IllegalArgumentException("cannot find party with uuid=" + partyUuid);
        }

        auction.getRegisteredPartys().add(party);
        auctionDAO.save(auction);

        // generate auth key
        String authkey = CheckAuth.createAuctionKey(auctionuuid, partyUuid);
        LOG.debug("registerForAuction authkey=" + authkey
                + " auctionuuid," + auctionuuid + " partyUuid" + partyUuid);

        return authkey;

    }

    @Override
    public List<Auction> getAuctionList() {
        return auctionDAO.findAll();
    }

    @Override
    public Auction getAuctionDetails(String auctionuuid) {
        return auctionDAO.findByAuctionuuid(auctionuuid);
    }

    @Override
    public Lot getLotDetails(String lotuuid) {
        return lotDAO.findByLotuuid(lotuuid);
    }

    @Override
    public List<Lot> getAuctionLots(String auctionuuid) {
        Auction auction = auctionDAO.findByAuctionuuid(auctionuuid);
        if (auction == null) {
            throw new IllegalArgumentException("unknown auction " + auctionuuid);
        }
        return auction.getLots();
    }

    @Override
    public Lot addLotToAuction(String auctionuuid, String selleruuid, Flower flowertype, double reserveprice, long quantity) throws IllegalArgumentException {
        LOG.debug("addLotToAuction called auctionuuid=" + auctionuuid
                + " selleruuid=" + selleruuid
                + " flowertype=" + flowertype
                + " reserveprice=" + reserveprice
                + " quantity=" + quantity);

        Auction auction = auctionDAO.findByAuctionuuid(auctionuuid);
        if (auction == null) {
            throw new IllegalArgumentException("unknown auction " + auctionuuid);
        }
        Party seller = partyDAO.findByUuid(selleruuid);
        if (seller == null) {
            throw new IllegalArgumentException("unknown seller party " + selleruuid);
        }

        boolean registered = false;
        for (Party party : auction.getRegisteredPartys()) {
            if (party.getUuid().equals(selleruuid)) {
                registered = true;
                break;
            }
        }
        if (registered == false) {
            throw new IllegalArgumentException(
                    "Seller selleruuid=" + selleruuid + " is not registered for auction auctionuuid=" + auctionuuid);
        }
        if (reserveprice < 0) {
            throw new IllegalArgumentException("reserve price cannot be less than 0");
        }

        if (quantity < 0) {
            throw new IllegalArgumentException("quantity cannot be less than 0");
        }

        Lot lot = new Lot();
        lot.setLotDuraton(auction.getLotDuration());
        lot.setAuctionType(auction.getAuctionType());
        lot.setSeller(seller);
        lot.setQuantity(quantity);
        lot.setReservePrice(reserveprice);

        lot = lotDAO.save(lot);
        return lot;
    }

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

        List<Auction> activeandScheduledAuctions = auctionDAO.findActiveOrScheduledBefore(currentTime);

        // limit number of auctions which can run from list
        List<Auction> runnableAuctions = new ArrayList();
        int x = 0;
        while (x < noSymultaneousAuctions && x < activeandScheduledAuctions.size()) {
            runnableAuctions.add(activeandScheduledAuctions.get(x));
            x++;
        }

        // for all found scheduled auctions in database, add to active auctions
        // for all found finished auctions in active auctions, save to database
        LOG.debug("iterating through " + runnableAuctions.size()
                + " activeandScheduledAuctions");
        for (Auction dbAuction : runnableAuctions) {

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
                                // lot time expired
                                String debugMessage = "lot duraton finished lot=" + lot.getLotuuid();
                                LOG.debug(debugMessage);

                                if (lot.getCurrentBidderUuid() != null) {
                                    // lotSold(String auctionuuid, String lotuuid, Double price, String buyeruuid)
                                    lotSold(dbAuction.getAuctionuuid(), lot.getLotuuid(), lot.getCurrentPrice(), lot.getCurrentBidderUuid());
                                } else {
                                    lot.setLotStatus(AuctionOrLotStatus.FINISHED);
                                    Message message = new Message();
                                    message.setMessageType(MessageType.LOT_WITHDRAWN);
                                    message.setAuctionuuid(dbAuction.getAuctionuuid());
                                    message.setLotuuid(lot.getLotuuid());
                                    message.setDebugMessage(debugMessage);
                                    messagesOut.broadcastMessage(message);
                                }
                                activeLots.remove(lot.getLotuuid());

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
                            message.setLotuuid(lot.getLotuuid());
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
                        message.setMessageType(MessageType.END_OF_AUCTION);
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
    public void bidForLot(String bidderuuid, String auctionuuid, String authKey, String lotuuid, double amount) throws IllegalArgumentException {
        LOG.debug("bidForLot called bidderuuid=" + bidderuuid
                + " auctionuuid=" + auctionuuid
                + " authKey=" + authKey
                + " lotuuid=" + lotuuid
                + " amount" + amount);

        Message message = new Message();
        message.setAuctionuuid(auctionuuid);
        message.setBidderuuid(bidderuuid);
        message.setLotuuid(lotuuid);

        if (!CheckAuth.checkAuctionKey(authKey, auctionuuid, bidderuuid)) {
            throw new IllegalArgumentException("unauthorised bid message authKey=" + authKey
                    + " auctionuuid=" + auctionuuid
                    + " bidderuuid=" + bidderuuid);
        }

        Lot lot = activeLots.get(lotuuid);
        if (lot == null) {
            throw new IllegalArgumentException("lot lotuuid=" + lotuuid + " in auction auctionuuid=" + auctionuuid
                    + " is not currently active ");

        }

        if (amount < 0) {
            throw new IllegalArgumentException("amount cannot be less than 0");
        }

        if (amount < lot.getReservePrice()) {
            throw new IllegalArgumentException("lot lotuuid=" + lotuuid + " in auction auctionuuid=" + auctionuuid
                    + " bid amount ("+amount
                            + ") less than reserve price "+lot.getReservePrice());
        }

        if (AuctionType.NORMAL == lot.getAuctionType()) {
            if (lot.getCurrentPrice() < amount) {
                lot.setCurrentPrice(amount);
                message.setMessageType(MessageType.NEW_HIGHEST_BID);
                message.setValue(amount);
                messagesOut.broadcastMessage(message);
            }
        } else if (AuctionType.DUTCH == lot.getAuctionType()) {
            if (amount >= lot.getCurrentPrice()) {
                lot.setCurrentPrice(amount);
                message.setMessageType(MessageType.LOT_SOLD);
                message.setValue(amount);
                message.setBidderuuid(bidderuuid);

                lotSold(auctionuuid, lotuuid, amount, bidderuuid);

                messagesOut.broadcastMessage(message);
            }
        } else {
            throw new IllegalStateException("unknown auction type " + lot.getAuctionType()
                    + "in lot=" + lot);
        }

        // saving minimal bid information without looking up database
        Bid bid = new Bid();
        bid.setBidderuuid(bidderuuid);
        bid.setLot(lot);
        bid.setAmount(amount);
        bid.setTime(new Date());
        lot.getBids().add(bid);

    }

    public synchronized void lotSold(String auctionuuid, String lotuuid, Double price, String buyeruuid) {
        Auction auction = auctionDAO.findByAuctionuuid(auctionuuid);
        if (auction == null) {
            throw new IllegalArgumentException("lotSold cannot find auction with uuid=" + auctionuuid);
        }

        Lot activeLot = activeLots.get(lotuuid);
        if (activeLot == null) {
            throw new IllegalArgumentException("lotSold cannot find activelot lotuuid=" + lotuuid);
        }

        Party buyer = partyDAO.findByUuid(buyeruuid);
        if (buyer == null) {
            throw new IllegalArgumentException("lotSold called for=" + lotuuid + " buyer buyeruuid=" + buyeruuid
                    + " not found");
        }

        for (Lot foundlot : auction.getLots()) {
            if (foundlot.getLotuuid().equals(lotuuid)) {

                foundlot.setBuyer(buyer);
                foundlot.setSoldPrice(price);
                foundlot.setLotStatus(AuctionOrLotStatus.FINISHED);

                // save bids in database and then save auction
                for (Bid bid : foundlot.getBids()) {
                    bid = bidDAO.save(bid);
                }

                lotDAO.save(foundlot);
                break;
            }
        }
        activeLots.remove(lotuuid);
    }

    @Override
    public Message onMessageReceived(Message message) {

        String auctionuuid = message.getAuctionuuid();
        String lotuuid = message.getLotuuid();
        String bidderuuid = message.getBidderuuid();
        Double value = message.getValue();
        String authKey = message.getAuthKey();

        Message reply = new Message();

        reply.setAuctionuuid(auctionuuid);
        reply.setBidderuuid(bidderuuid);
        reply.setLotuuid(lotuuid);
        reply.setValue(value);
        reply.setMessageType(MessageType.ERROR);

        if (message.getMessageType() == MessageType.BID) {
            try {
                bidForLot(bidderuuid, auctionuuid, authKey, lotuuid, value);
            } catch (Exception ex) {
                String debugMessage = ex.getMessage();
                reply.setDebugMessage(debugMessage);
                return reply;
            }
        } else {
            String debugMessage = "received messageType=" + message.getMessageType()
                    + "  but you are only allowed to send BID messages on this channel";
            reply.setDebugMessage(debugMessage);
            return reply;
        }

        return null;
    }

}
