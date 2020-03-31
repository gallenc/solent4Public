/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service.test.manual;

import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.solent.com504.project.impl.auction.client.rest.AuctionRestClient;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.auction.service.AuctionService;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.party.dto.Party;

/**
 *
 * @author cgallen
 */
public class AuctionRestClientTest {

    final static Logger LOG = LogManager.getLogger(AuctionRestClientTest.class);

    String baseUrl = "http://localhost:8084/auction-events/rest/auction/";

    @Test
    public void testregisterForAuction() {
        AuctionRestClient auctionRestClient = new AuctionRestClient(baseUrl);
        List<Party> partyList = auctionRestClient.getPartys();
        assertFalse(partyList.isEmpty());
        List<Auction> auctionList = auctionRestClient.getAuctionList();
        assertFalse(auctionList.isEmpty());

        String authkey = auctionRestClient.registerForAuction(auctionList.get(0).getAuctionuuid(), partyList.get(0).getUuid());
        assertNotNull(authkey);
    }
    
        @Test
    public void testfailregisterForAuction() {
        AuctionRestClient auctionRestClient = new AuctionRestClient(baseUrl);
        

        String authkey = auctionRestClient.registerForAuction("aaaa", "bbbb");
        
    }

    @Test
    public void testgetAuctionList() {
        AuctionRestClient auctionRestClient = new AuctionRestClient(baseUrl);
        List<Auction> auctionList = auctionRestClient.getAuctionList();
        assertFalse(auctionList.isEmpty());

    }

    @Test
    public void testgetAuctionDetails() {

    }

    @Test
    public void testgetLotDetails() {
    }

    @Test
    public void testgetAuctionLots() {

    }

    @Test
    public void testaddLotToAuction() {

    }

    @Test
    public void testgetPartys() {
        AuctionRestClient auctionRestClient = new AuctionRestClient(baseUrl);
        List<Party> partyList = auctionRestClient.getPartys();
        assertFalse(partyList.isEmpty());
    }

}
