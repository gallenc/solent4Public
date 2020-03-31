/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.client.rest;

import java.util.Date;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.auction.service.AuctionService;
import org.solent.com504.project.model.dto.ReplyMessage;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.party.dto.Party;

/**
 * only some of this is implemented
 *
 * @author cgallen
 */
public class AuctionRestClient {

    final static Logger LOG = LogManager.getLogger(AuctionRestClient.class);

    String baseUrl = "http://localhost:8084/auction-events/rest/auction/";

    public AuctionRestClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

//
//
//    @Override
//    public Animal addAnimal(String animalType, String animalName) {
//        LOG.debug("client addAnimal Called animalType=" + animalType + " animalName=" + animalName);
//
//        Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
//        WebTarget webTarget = client.target(baseUrl).path("addAnimal");
//
//        // this is how we construct html FORM variables
//        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
//        formData.add("animalType", animalType);
//        formData.add("animalName", animalName);
//
//        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
//        Response response = invocationBuilder.post(Entity.form(formData));
//
//        ReplyMessage replyMessage = response.readEntity(ReplyMessage.class);
//        LOG.debug("Response status=" + response.getStatus() + " ReplyMessage: " + replyMessage);
//
//        if (!replyMessage.getAnimalList().getAnimals().isEmpty()) {
//            return replyMessage.getAnimalList().getAnimals().get(0);
//        }
//        return null;
//    }
    
    
    public String registerForAuction(String auctionuuid, String partyuuid) {
                LOG.debug("registerForAuction Called auctionuuid="+auctionuuid
                        + " partyuuid="+partyuuid);

        Client client = ClientBuilder
                .newBuilder()
                .property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT, LoggingFeature.Verbosity.PAYLOAD_ANY)
                .property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, "WARNING")
                .build();

        WebTarget webTarget = client.target(baseUrl).path("registerforauction");
             // this is how we construct html FORM variables
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("auctionuuid", auctionuuid);
        formData.add("partyuuid", partyuuid);

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
        Response response = invocationBuilder.post(Entity.form(formData));
        ReplyMessage replyMessage = response.readEntity(ReplyMessage.class);
        LOG.debug("Response status=" + response.getStatus() + " ReplyMessage: " + replyMessage);
        return replyMessage.getAuthKey();
        
    }

    public List<Auction> getAuctionList() {
        LOG.debug("getAuctionList Called");
        List<Auction> auctionList = null;

        Client client = ClientBuilder
                .newBuilder()
                .property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT, LoggingFeature.Verbosity.PAYLOAD_ANY)
                .property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, "WARNING")
                .build();

        WebTarget webTarget = client.target(baseUrl).path("auctionlist");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
        Response response = invocationBuilder.get();

        ReplyMessage replyMessage = response.readEntity(ReplyMessage.class);
        LOG.debug("Response status=" + response.getStatus() + " ReplyMessage: " + replyMessage);

        auctionList = replyMessage.getAuctionList();

        return auctionList;
    }

    public Auction getAuctionDetails(String auctionuuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Lot getLotDetails(String lotuuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Lot> getAuctionLots(String auctionuuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Lot addLotToAuction(String auctionuuid, String selleruuid, Flower flowertype, double reserveprice, long quantity) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Party> getPartys() {
        LOG.debug("getPartys() Called");
        List<Party> partyList = null;

        Client client = ClientBuilder
                .newBuilder()
                .property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT, LoggingFeature.Verbosity.PAYLOAD_ANY)
                .property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, "WARNING")
                .build();

        WebTarget webTarget = client.target(baseUrl).path("partys");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
        Response response = invocationBuilder.get();

        ReplyMessage replyMessage = response.readEntity(ReplyMessage.class);
        LOG.debug("Response status=" + response.getStatus() + " ReplyMessage: " + replyMessage);

        partyList = replyMessage.getPartyList();

        return partyList;
    }

}
