/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.auction.dao.AuctionDAO;
import org.solent.com504.project.model.auction.dto.Auction;

/**
 *
 * @author cgallen
 */
public class AuctionMockDAO implements AuctionDAO {

    final static Logger LOG = LogManager.getLogger(AuctionMockDAO.class);

    // hashmap of key auctionuuid, Auction - would replace with dao
    private LinkedHashMap<String, Auction> auctionMap = new LinkedHashMap();

    @Override
    public Auction findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Auction save(Auction auction) {
        LOG.debug("save(auction)=" + auction);

        auctionMap.put(auction.getAuctionuuid(), auction);
        return auction;
    }

    @Override
    public List<Auction> findAll() {
        List<Auction> auctionList = new ArrayList();
        for (String key : auctionMap.keySet()) {
            Auction a = auctionMap.get(key);
            auctionList.add(a);
        }
        return auctionList;
    }

    @Override
    public void deleteById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Auction auction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Auction findByAuctionuuid(String auctionuuid) {
        return auctionMap.get(auctionuuid);
    }

}
