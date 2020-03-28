package org.solent.com504.project.model.auction.dao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.Lot;

public interface AuctionDAO {

    public Auction findById(Long id);

    public Auction save(Auction auction);

    public List<Auction> findAll();

    public void deleteById(long id);

    public void delete(Auction auction);

    public void deleteAll();

    public Auction findByAuctionuuid(String auctionuuid);
    
    public List<Auction> findActiveOrScheduledBefore(Date time);

}
