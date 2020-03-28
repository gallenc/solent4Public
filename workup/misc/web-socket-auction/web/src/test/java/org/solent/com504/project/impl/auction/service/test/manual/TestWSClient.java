/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service.test.manual;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import javax.websocket.DeploymentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cgallen
 */
public class TestWSClient {

    final static Logger LOG = LogManager.getLogger(TestWSClient.class);

    private static CountDownLatch latch;

    @Test
    public void testClient() {

        latch = new CountDownLatch(1);

        ClientManager client = ClientManager.createClient();
        try {
            String partyuuid = "zzz";
            client.connectToServer(AuctionClientEndpoint.class, 
                    new URI("ws://localhost:8084/auction-events/auctionwebsocket/"+partyuuid));
            latch.await();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
