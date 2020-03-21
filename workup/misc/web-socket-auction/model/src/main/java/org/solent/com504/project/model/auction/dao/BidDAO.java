package org.solent.com504.project.model.auction.dao;

import java.util.List;
import org.solent.com504.project.model.auction.dto.Bid;


public interface BidDAO {
    
    public Bid findById(Long id);

    public Bid save(Bid bid);

    public List<Bid> findAll();

    public void deleteById(long id);

    public void delete(Bid bid);

    public void deleteAll();
    
    public Bid findByBiduuid(String biduuid);
    
}
