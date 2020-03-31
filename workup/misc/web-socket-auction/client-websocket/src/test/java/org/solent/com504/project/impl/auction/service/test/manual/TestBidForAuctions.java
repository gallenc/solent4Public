/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service.test.manual;

import solent.ac.uk.com600.examples.websocket.auction.client.AuctionClientEndpoint;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javax.websocket.DeploymentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.solent.com504.project.impl.auction.client.rest.AuctionRestClient;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.auction.dto.MessageType;
import org.solent.com504.project.model.auction.message.MessageListener;
import org.solent.com504.project.model.party.dto.Party;

/**
 *
 * @author cgallen
 */
public class TestBidForAuctions implements MessageListener {

    final static Logger LOG = LogManager.getLogger(TestBidForAuctions.class);

    String baseUrl = "http://localhost:8084/auction-events/rest/auction/";

    String webSocketbaseuri = "ws://localhost:8084/auction-events/auctionwebsocket/";

    // map of auctionuuid,authKey
    Map<String, String> authKeyMap = new HashMap();

    AuctionRestClient auctionRestClient = null;

    AuctionClientEndpoint clientEndPoint = null;

    @Test
    public void testBidForAuctions() {

        auctionRestClient = new AuctionRestClient(baseUrl);

        // get list of parties from reset interface and set our partyuuid to be the last party in the list
        List<Party> partyList = auctionRestClient.getPartys();
        assertFalse(partyList.isEmpty());
        String mypartyuuid = partyList.get(partyList.size()-1).getUuid();

        // register for all auctions in auction list
        List<Auction> auctionList = auctionRestClient.getAuctionList();
        assertFalse(auctionList.isEmpty());

        for (Auction auction : auctionList) {
            String auctionuuid = auction.getAuctionuuid();
            String authKey = auctionRestClient.registerForAuction(auctionuuid, mypartyuuid);
            assertNotNull(authKey);
            authKeyMap.put(auctionuuid, authKey);
        }

        try {
            // open websocket
            clientEndPoint = new AuctionClientEndpoint(
                    new URI(webSocketbaseuri + mypartyuuid),
                    this);

            // send message to websocket
            Message message = new Message();
            message.setMessageType(MessageType.TEST);
            clientEndPoint.sendMessage(message);

            // wait 60 seconds for messages from websocket
            Thread.sleep(1000 * 60 * 10);

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        } finally {
            if (clientEndPoint != null) {
                clientEndPoint.close();
            }
        }

    }

    @Override
    public Message onMessageReceived(Message message) {
        LOG.debug("message client received message: " + message);

        if (MessageType.START_OF_LOT == message.getMessageType()) {
            String auctionuuid = message.getAuctionuuid();
            String lotuuid = message.getLotuuid();
            String authKey = authKeyMap.get(auctionuuid);
            Message bidMessage = new Message();
            bidMessage.setAuctionuuid(auctionuuid);
            bidMessage.setAuthKey(authKey);
            bidMessage.setLotuuid(lotuuid);
            bidMessage.setValue(10000.00);
            clientEndPoint.sendMessage(bidMessage);
        }

        return null;
    }
}
