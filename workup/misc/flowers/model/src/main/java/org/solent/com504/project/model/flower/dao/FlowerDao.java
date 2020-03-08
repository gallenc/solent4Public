/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.flower.dao;

import java.util.List;
import org.solent.com504.project.model.flower.dto.Flower;

/**
 *
 * @author cgallen
 */
public interface FlowerDao {

    public List<Flower> findBySymboOrSynonymSymbol(String symbol);

    public List<Flower> findLikeScientificNamewithAuthor(String scientificNamewithAuthor);

    public List<Flower> findLikeCommonName(String commonName);

    public List<Flower> findLikefamily(String family);
    
    public List<Flower> findLike(Flower flower);
    
    public List<String> getAllFamilies();

}
