/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.AuctionStatus;
import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.auction.dto.MessageType;
import org.solent.com504.project.model.auction.service.AuctionService;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.party.dto.Party;

/**
 *
 * @author cgallen
 */
public class AuctionServiceMockImpl implements AuctionService {

    final static Logger LOG = LogManager.getLogger(AuctionServiceMockImpl.class);

    // hashmap of key auctionuuid, Auction - would replace with dao
    private LinkedHashMap<String, Auction> auctionMap = new LinkedHashMap();
    private LinkedHashMap<String, Party> partyMap = new LinkedHashMap();

    // hashmap of lotuuid / active lots
    private LinkedHashMap<String, Lot> activeLots = new LinkedHashMap();

    // initialisation code 
    {
        // will parse 2009-12-31 23:59:59
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        List<Party> partyList = Arrays.asList(new Party("great flowers", "co"), new Party("great flowers2", "co"), new Party("great flowers3", "co"));
        for (Party party : partyList) {
            partyMap.put(party.getUuid(), party);
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
                auctionMap.put(auction.getAuctionuuid(), auction);
            } catch (Exception ex) {
                LOG.error("problem initialising :", ex);
            }
        }
    }

    @Override
    public synchronized String registerForAuction(String auctionuuid, String partyUuid) {
        LOG.debug("registerForAuction called auctionuuid," + auctionuuid + " partyUuid" + partyUuid);

        // would use DAO here
        Auction auction = auctionMap.get(auctionuuid);
        if (auction == null) {
            throw new IllegalArgumentException("cannot find auction with uuid=" + auctionuuid);
        }

        Party party = partyMap.get(partyUuid);
        if (party == null) {
            throw new IllegalArgumentException("cannot find party with uuid=" + partyUuid);
        }

        auction.getRegisteredPartys().add(party);

        // generate auth key
        String authkey = CheckAuth.createAuctionKey(auctionuuid, partyUuid);
        LOG.debug("registerForAuction authkey=" + authkey
                + " auctionuuid," + auctionuuid + " partyUuid" + partyUuid);

        return authkey;

    }

    @Override
    public synchronized List<Auction> getAuctionList() {
        LOG.debug("getAuctionList called");
        List<Auction> auctionList = new ArrayList();
        for (String key : auctionMap.keySet()) {
            Auction a = auctionMap.get(key);
            auctionList.add(a);
        }
        return auctionList;
    }

    @Override
    public synchronized Auction getAuctionDetails(String auctionuuid) {
        return auctionMap.get(auctionuuid);
    }

    @Override
    public synchronized Lot getLotDetails(String lotuuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized List<Lot> getAuctionLots(String auctionuuid) {
        LOG.debug("registerForAuction called auctionuuid," + auctionuuid);
        Auction auction = auctionMap.get(auctionuuid);
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

        Auction auction = auctionMap.get(auctionuuid);
        if (auction == null) {
            throw new IllegalArgumentException("cannot find auction with uuid=" + auctionuuid);
        }

        if (AuctionStatus.ACTIVE == auction.getAuctionStatus()) {
            throw new IllegalStateException("auction is ACTIVE. cannot add lot to auction=" + auctionuuid);
        }

        Party seller = partyMap.get(selleruuid);
        if (seller == null) {
            throw new IllegalArgumentException("cannot find seller party with uuid=" + selleruuid);
        }

        // add new lot to 
        Lot lot = new Lot();
        lot.setFlowerType(flowertype);
        lot.setReservePrice(reserveprice);
        lot.setQuantity(quantity);
        lot.setSeller(seller);
        auction.getLots().add(lot);

        return lot;
    }

    @Override
    public synchronized Message bidForLot(String bidderuuid, String auctionuuid, String authKey, String lotuuid, Double value) {
        Message message = new Message();
        message.setAuctionuuid(auctionuuid);
        message.setBidderuuid(bidderuuid);
        message.setLotuuid(lotuuid);

        if (!CheckAuth.checkAuctionKey(authKey, auctionuuid, lotuuid)) {
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

        if (lot.getCurrentPrice() < value) {
            lot.setCurrentPrice(value);
            message.setMessageType(MessageType.NEW_HIGHEST_BID);
            message.setValue(value);
            return message;
        }

        return message;
    }

}
