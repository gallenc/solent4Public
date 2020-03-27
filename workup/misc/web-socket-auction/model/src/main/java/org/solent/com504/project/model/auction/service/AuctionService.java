package org.solent.com504.project.model.auction.service;

import java.util.Date;
import java.util.List;
import org.solent.com504.project.model.auction.dto.Auction;

import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.flower.dto.Flower;


public interface AuctionService {
    
    // returns authkey
    public String registerForAuction(String auctionuuid, String partyUuid);
    
    public List<Auction> getAuctionList();
    
    public Auction getAuctionDetails(String auctionuuid);
    
    public Lot getLotDetails(String lotuuid);
    
    public List<Lot> getAuctionLots(String auctionuuid);
    
    public Lot addLotToAuction(String auctionuuid, String selleruuid, Flower flowertype, double reserveprice, long quantity);
    
    public void bidForLot(String bidderuuid, String auctionuuid, String authKey, String lotuuid, double amount);
    
    public void runAuctionSchedule();
    
    public void runAuctionSchedule(Date currentTime);


}
