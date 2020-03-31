/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service.test.manual;

import solent.ac.uk.com600.examples.websocket.auction.client.AuctionClientEndpoint;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import javax.websocket.DeploymentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.auction.dto.MessageType;
import org.solent.com504.project.model.auction.message.MessageListener;

/**
 *
 * @author cgallen
 */
public class TestAuctionWebSocketClient implements MessageListener {

    final static Logger LOG = LogManager.getLogger(TestAuctionWebSocketClient.class);

    @Test
    public void testClient() {

        AuctionClientEndpoint clientEndPoint = null;

        try {
            // open websocket
            String partyuuid = "xxxx";
            clientEndPoint = new AuctionClientEndpoint(
                    new URI("ws://localhost:8084/auction-events/auctionwebsocket/" + partyuuid),
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
        return null;
    }
}
