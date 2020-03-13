package org.solent.com504.project.impl.flower.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.solent.com504.project.model.flower.dao.FlowerDao;
import org.solent.com504.project.model.flower.dto.Flower;

import org.solent.com504.project.model.flower.service.ServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// note we give the bean this name so it is picked up later in application context
@Component("serviceFacade")
public class ServiceFacadeImpl implements ServiceFacade {

    @Autowired
    FlowerDao flowerDao;

    // used to concurently count heartbeat requests
    private static AtomicInteger heartbeatRequests = new AtomicInteger();

    // Service facade methods
    @Override
    public String getHeartbeat() {
        return "heartbeat number " + heartbeatRequests.getAndIncrement() + " " + new Date().toString();
    }

    @Override
    public List<Flower> findBySymbolOrSynonymSymbol(String symbol) {
        return flowerDao.findBySymbolOrSynonymSymbol(symbol);
    }

    @Override
    public List<Flower> findLikeScientificNamewithAuthor(String scientificNamewithAuthor) {
        return flowerDao.findLikeScientificNamewithAuthor(scientificNamewithAuthor);
    }

    @Override
    public List<Flower> findLikeCommonName(String commonName) {
        return flowerDao.findLikeCommonName(commonName);
    }

    @Override
    public List<Flower> findLikefamily(String family) {
        return flowerDao.findLikefamily(family);
    }

    @Override
    public List<Flower> findLike(Flower flower) {
        return flowerDao.findLike(flower);
    }

    @Override
    public List<String> getAllFamilies() {
        return flowerDao.getAllFamilies();
    }

}
