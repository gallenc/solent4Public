/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service;

import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.impl.auction.dao.AuctionMockDAO;
import org.solent.com504.project.impl.auction.dao.BidMockDAO;
import org.solent.com504.project.impl.auction.dao.LotMockDAO;
import org.solent.com504.project.impl.auction.dao.PartyMockDAO;
import org.solent.com504.project.model.auction.dao.AuctionDAO;
import org.solent.com504.project.model.auction.dao.BidDAO;
import org.solent.com504.project.model.auction.dao.LotDAO;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.AuctionOrLotStatus;
import org.solent.com504.project.model.auction.dto.AuctionType;
import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.auction.dto.MessageType;
import org.solent.com504.project.model.auction.service.AuctionService;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.party.dao.PartyDAO;
import org.solent.com504.project.model.party.dto.Party;

/**
 *
 * @author cgallen
 */
public class AuctionServiceMockImpl implements AuctionService {

    final static Logger LOG = LogManager.getLogger(AuctionServiceMockImpl.class);

    private PartyDAO partyDAO = new PartyMockDAO();

    private AuctionDAO auctionDAO = new AuctionMockDAO();

    private LotDAO lotDao = new LotMockDAO(auctionDAO);
    
    private BidDAO bidDao = new BidMockDAO();

    // hashmap of lotuuid / active lots
    private LinkedHashMap<String, Lot> activeLots = new LinkedHashMap();

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
    
    

    // mock initialisation code 
    {
        // will parse 2009-12-31 23:59:59
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        List<Party> partyList = Arrays.asList(new Party("great flowers", "co"), new Party("great flowers2", "co"), new Party("great flowers3", "co"));
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
                auctionDAO.save(auction);
            } catch (Exception ex) {
                LOG.error("problem initialising :", ex);
            }
        }
    }

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
    public synchronized List<Auction> getAuctionList() {
        LOG.debug("getAuctionList called");
        List<Auction> auctionList = auctionDAO.findAll();
        return auctionList;
    }

    @Override
    public synchronized Auction getAuctionDetails(String auctionuuid) {
        return auctionDAO.findByAuctionuuid(auctionuuid);

    }

    @Override
    public synchronized Lot getLotDetails(String lotuuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized List<Lot> getAuctionLots(String auctionuuid) {
        LOG.debug("registerForAuction called auctionuuid," + auctionuuid);
        Auction auction = auctionDAO.findByAuctionuuid(auctionuuid);
        if (auction == null) {
            throw new IllegalArgumentException("cannot find auction with uuid=" + auctionuuid);
        }
        return auction.getLots();
    }

    @Override
    public synchronized Lot addLotToAuction(String auctionuuid, String selleruuid, Flower flowertype, Double reserveprice, Long quantity) {
        LOG.debug("addLotToAuction called auctionuuid=" + auctionuuid
                + " selleruuid=" + selleruuid
                + "flowertype=" + flowertype
                + " reserveprice=" + reserveprice);

        Auction auction = auctionDAO.findByAuctionuuid(auctionuuid);
        if (auction == null) {
            throw new IllegalArgumentException("cannot find auction with uuid=" + auctionuuid);
        }

        if (AuctionOrLotStatus.ACTIVE == auction.getAuctionStatus()) {
            throw new IllegalStateException("auction is ACTIVE. cannot add lot to auction=" + auctionuuid);
        }

        Party seller = partyDAO.findByUuid(selleruuid);
        if (seller == null) {
            throw new IllegalArgumentException("cannot find seller party with uuid=" + selleruuid);
        }

        // add new activeLot to 
        Lot lot = new Lot();
        lot.setLotDuraton(auction.getLotDuration());
        lot.setAuctionType(auction.getAuctionType());
        lot.setFlowerType(flowertype);
        lot.setReservePrice(reserveprice);
        lot.setQuantity(quantity);
        lot.setSeller(seller);
        auction.getLots().add(lot);

        return lot;
    }

    // ACTIVE AUCTION METHODS
    public synchronized void runAuction(String auctionuuid) {
        Auction auction = auctionDAO.findByAuctionuuid(auctionuuid);
        if (auction != null) {
            throw new IllegalArgumentException("runAuction called on unknown auction=" + auctionuuid);
        } else {
            if (AuctionOrLotStatus.SCHEDULED == auction.getAuctionStatus()) {
                auction.setAuctionStatus(AuctionOrLotStatus.ACTIVE);
                auctionDAO.save(auction);
                LOG.debug("runAuction made active auction=" + auctionuuid);
            } else if (AuctionOrLotStatus.ACTIVE == auction.getAuctionStatus()) {
                LOG.debug("runAuction auction already ACTIVE auction=" + auctionuuid);
            } else {
                throw new IllegalStateException("runAuction called on auction auctionuuid=" + auctionuuid
                        + " with status =" + auction.getAuctionStatus());
            }

        }
    }

    public synchronized void endAuction(String auctionuuid) {
        Auction auction = auctionDAO.findByAuctionuuid(auctionuuid);
        if (auction == null) {
            LOG.debug("endAuction called for unknown auctionuuid=" + auctionuuid);
        } else {
            LOG.debug("endAuction called for=" + auctionuuid);
            for (Lot lot : auction.getLots()) {
                String lotuuid = lot.getLotuuid();
                endLot(auctionuuid, lotuuid);
            }
            auction.setAuctionStatus(AuctionOrLotStatus.FINISHED);
            auctionDAO.save(auction);
        }
    }

    public synchronized void runLot(String auctionuuid, String lotuuid) {
        LOG.debug("runLot called for auctionid=" + auctionuuid + " lotuuid=" + lotuuid);
        if (activeLots.get(lotuuid) != null) {
            LOG.debug("runLot lot already active auctionid=" + auctionuuid + " lotuuid=" + lotuuid);
        } else {
            Auction auction = auctionDAO.findByAuctionuuid(auctionuuid);
            if (auction == null) {
                throw new IllegalArgumentException("runlot cannot find auction  for auctionuuid=" + auctionuuid);
            }
            // find activeLot
            for (Lot foundlot : auction.getLots()) {
                if (foundlot.getLotuuid().equals(lotuuid)) {
                    foundlot.setLotStatus(AuctionOrLotStatus.ACTIVE);
                    activeLots.put(lotuuid, foundlot);
                    auctionDAO.save(auction);
                    break;
                }
            }
        }

    }

    public synchronized void endLot(String auctionuuid, String lotuuid) {
        LOG.debug("endLot called for auctionid=" + auctionuuid + " lotuuid=" + lotuuid);

        Lot lot = activeLots.get(lotuuid);
        if (lot == null) {
            LOG.debug("endLot called for=" + lotuuid + " but lot already inactive");
        } else {
            activeLots.remove(lotuuid);

            lot.setLotStatus(AuctionOrLotStatus.FINISHED);
            Auction auction = auctionDAO.findByAuctionuuid(auctionuuid);
            if (auction == null) {
                throw new IllegalArgumentException("endlot cannot find auction  for auctionuuid=" + auctionuuid);
            }
            for (Lot foundlot : auction.getLots()) {
                if (foundlot.getLotuuid().equals(lotuuid)) {
                    foundlot.setLotStatus(AuctionOrLotStatus.FINISHED);
                    auctionDAO.save(auction);
                    break;
                }
            }

        }

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
                foundlot.setCurrentPrice(price);
                foundlot.setLotStatus(AuctionOrLotStatus.FINISHED);
                auctionDAO.save(auction);
                break;
            }
        }
        activeLots.remove(lotuuid);
    }

    @Override
    public synchronized Message bidForLot(String bidderuuid, String auctionuuid, String authKey, String lotuuid, Double value) {
        Message message = new Message();
        message.setAuctionuuid(auctionuuid);
        message.setBidderuuid(bidderuuid);
        message.setLotuuid(lotuuid);

        if (!CheckAuth.checkAuctionKey(authKey, auctionuuid, bidderuuid)) {
            message.setMessageType(MessageType.ERROR);
            message.setDebugMessage("unauthorised bid message");
            return message;
        }

        Lot lot = activeLots.get(lotuuid);
        if (lot == null) {
            message.setMessageType(MessageType.ERROR);
            message.setDebugMessage("lot lotuuid=" + lotuuid + " in auction auctionuuid=" + auctionuuid
                    + " is not currently active ");
            return message;
        }

        if (lot.getReservePrice() <= value) {
            message.setMessageType(MessageType.ERROR);
            message.setDebugMessage("lot lotuuid=" + lotuuid + " in auction auctionuuid=" + auctionuuid
                    + " bid price less than reserve price ");
            return message;
        }

        if (AuctionType.NORMAL == lot.getAuctionType()) {
            if (lot.getCurrentPrice() < value) {
                lot.setCurrentPrice(value);
                message.setMessageType(MessageType.NEW_HIGHEST_BID);
                message.setValue(value);
                return message;
            }
        } else if (AuctionType.DUTCH == lot.getAuctionType()) {
            if (value >= lot.getCurrentPrice()) {
                lot.setCurrentPrice(value);
                message.setMessageType(MessageType.LOT_SOLD);
                message.setValue(value);
                message.setBidderuuid(bidderuuid);
                this.lotSold(auctionuuid, lotuuid, value, bidderuuid);
                return message;
            }
        } else {
            throw new IllegalStateException("unknown auction type " + lot.getAuctionType()
                    + "in lot=" + lot);
        }

        return message;
    }

    @Override
    public void runAuctionSchedule() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void runAuctionSchedule(Date currentTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
