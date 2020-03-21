/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.dao;

import java.util.List;
import org.solent.com504.project.model.auction.dao.AuctionDAO;
import org.solent.com504.project.model.auction.dao.LotDAO;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.Lot;

/**
 *
 * @author cgallen
 */
public class LotMockDAO implements LotDAO {

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Lot> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
