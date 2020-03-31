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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.websocket.DeploymentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.solent.com504.project.impl.auction.client.rest.AuctionRestClient;
import static org.solent.com504.project.impl.auction.service.test.manual.TestAuctionWebSocketClient.LOG;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.auction.dto.MessageType;
import org.solent.com504.project.model.auction.message.MessageListener;
import org.solent.com504.project.model.party.dto.Party;

/**
 * This test first registers for all of the auctions and then listens for new lots starting. 
 * The test then bids for each new lot.
 * @author cgallen
 */
public class TestBidForAuctions implements MessageListener {

    final static Logger LOG = LogManager.getLogger("TestBidForAuctions");

    String baseUrl = "http://localhost:8084/auction-events/rest/auction/";

    String webSocketbaseuri = "ws://localhost:8084/auction-events/auctionwebsocket/";

    // map of auctionuuid,authKey
    Map<String, String> authKeyMap = new HashMap();

    AuctionRestClient auctionRestClient = null;

    AuctionClientEndpoint clientEndPoint = null;

    private ScheduledExecutorService scheduler = null;

    String mypartyuuid = null;

    private AtomicReference<String> auctionuuidAtomicRef = new AtomicReference<String>("xxx");
    private AtomicReference<String> lotuuidAtomicRef = new AtomicReference<String>("yyy");
    private AtomicReference<Double> highestBidAtomicRef = new AtomicReference<Double>(0.0);

    @Test
    public void testBidForAuctions() {

        auctionRestClient = new AuctionRestClient(baseUrl);

        // get list of parties from reset interface and set our partyuuid to be the last party in the list
        List<Party> partyList = auctionRestClient.getPartys();
        assertFalse(partyList.isEmpty());
        mypartyuuid = partyList.get(partyList.size() - 1).getUuid();

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

            bidSchedule();

            // wait 10 minutes for messages from websocket
            Thread.sleep(1000 * 60 * 10);

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        } finally {
            if (scheduler != null) {
                scheduler.shutdownNow();
            }
            if (clientEndPoint != null) {
                clientEndPoint.close();
            }
        }

    }

    public void bidSchedule() {
        LOG.info("starting bid scheduler");
        scheduler = Executors.newSingleThreadScheduledExecutor();
        //scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String auctionuuid = auctionuuidAtomicRef.get();
                String lotuuid = lotuuidAtomicRef.get();
                Double value = highestBidAtomicRef.get() + 100.00;

                String authKey = authKeyMap.get(auctionuuid);
                
                Message bidMessage = new Message();
                bidMessage.setMessageType(MessageType.BID);
                bidMessage.setAuctionuuid(auctionuuid);
                bidMessage.setAuthKey(authKey);
                bidMessage.setLotuuid(lotuuid);
                bidMessage.setBidderuuid(mypartyuuid);
                bidMessage.setValue(value);
                LOG.debug("sending bid to auction: "+ bidMessage);
                try {
                    clientEndPoint.sendMessage(bidMessage);
                } catch (Exception ex) {
                    LOG.error("problem schedulling send message", ex);
                }

            }

        }, 10, 5, TimeUnit.SECONDS);
    }

    @Override
    public Message onMessageReceived(Message message) {
        LOG.debug("message client received message: " + message);

        if (MessageType.START_OF_LOT == message.getMessageType()) {
            String auctionuuid = message.getAuctionuuid();
            String lotuuid = message.getLotuuid();
            Double reservePrice = message.getValue();
            // note strictly this should be one object in atomic ref so that all values are changed together
            lotuuidAtomicRef.set(lotuuid);
            auctionuuidAtomicRef.set(auctionuuid);
            highestBidAtomicRef.set(reservePrice);
        } else if (MessageType.NEW_HIGHEST_BID == message.getMessageType()) {
            Double value = message.getValue();
            // don't bid against yourself
            if (!message.getBidderuuid().equals(mypartyuuid)) {
                highestBidAtomicRef.set(value);
            }

        }

        return null;
    }
}
