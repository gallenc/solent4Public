/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.auction.dao.AuctionDAO;
import org.solent.com504.project.model.auction.dao.LotDAO;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.Lot;

/**
 *
 * @author cgallen
 */
public class LotMockDAO implements LotDAO {
      final static Logger LOG = LogManager.getLogger(LotMockDAO.class);

    private AuctionDAO auctionDAO;

    public LotMockDAO(AuctionDAO auctionDAO) {
        this.auctionDAO = auctionDAO;
    }

    @Override
    public Lot findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Lot save(Lot lot) {
        // CANNOT SAVE LOT BECAUSE DONT KNOW WHICH AUCTION 
        return lot;
    }

    @Override
    public List<Lot> findAll() {
        List<Lot> lotList= new ArrayList();
        for (Auction auction : auctionDAO.findAll()) {
            for (Lot lot : auction.getLots()) {
                lotList.add(lot);
            }
        }
        return lotList;
    }

    @Override
    public void deleteById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Lot lot) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Lot findByLotuuid(String lotuuid) {
        for (Auction auction : auctionDAO.findAll()) {
            for (Lot lot : auction.getLots()) {
                if (lot.getLotuuid().equals(lotuuid)) {
                    return lot;
                }
            }
        }
        return null;
    }

}
