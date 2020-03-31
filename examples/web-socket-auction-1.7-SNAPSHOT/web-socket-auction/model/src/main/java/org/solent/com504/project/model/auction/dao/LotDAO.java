package org.solent.com504.project.model.auction.dao;

import java.util.List;
import org.solent.com504.project.model.auction.dto.Lot;

public interface LotDAO {

    public Lot findById(Long id);

    public Lot save(Lot lot);

    public List<Lot> findAll();

    public void deleteById(long id);

    public void delete(Lot lot);

    public void deleteAll();
    
    public Lot findByLotuuid(String lotuuid);

}
