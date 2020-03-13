package org.solent.com504.project.model.flower.service;

import java.util.List;
import org.solent.com504.project.model.flower.dto.Flower;

public interface ServiceFacade {

    public String getHeartbeat();

    // inherited from flowersDAO
    public List<Flower> findBySymbolOrSynonymSymbol(String symbol);

    public List<Flower> findLikeScientificNamewithAuthor(String scientificNamewithAuthor);

    public List<Flower> findLikeCommonName(String commonName);

    public List<Flower> findLikefamily(String family);

    public List<Flower> findLike(Flower flower);

    public List<String> getAllFamilies();

}
