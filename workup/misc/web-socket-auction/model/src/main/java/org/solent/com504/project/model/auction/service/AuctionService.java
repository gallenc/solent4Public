package org.solent.com504.project.model.auction.service;

import java.util.List;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.Bid;
import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.party.dto.Party;

public interface AuctionService {
    
    // returns authkey
    public String registerForAuction(String auctionuuid, String partyUuid);
    
    public List<Auction> getAuctionList();
    
    public Auction getAuctionDetails(String auctionuuid);
    
    public Lot getLotDetails(String lotuuid);
    
    public List<Lot> getAuctionLots(String auctionuuid);
    
    public Lot addLotToAuction(String auctionuuid, String selleruuid, Flower flowertype, Double reserveprice, Long quantity);
    
    public Message bidForLot(String bidderuuid, String auctionuuid, String authKey, String lotuuid, Double value);


}
