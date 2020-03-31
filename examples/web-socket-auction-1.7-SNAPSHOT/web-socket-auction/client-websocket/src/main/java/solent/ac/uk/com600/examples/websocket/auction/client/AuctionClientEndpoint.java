/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solent.ac.uk.com600.examples.websocket.auction.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.auction.message.MessageListener;

import solent.ac.uk.com600.examples.websocket.auction.client.MessageDecoder;
import solent.ac.uk.com600.examples.websocket.auction.client.MessageEncoder;

@ClientEndpoint(decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class AuctionClientEndpoint {

    final static Logger LOG = LogManager.getLogger(AuctionClientEndpoint.class);

    private Session userSession = null;
    private MessageListener messageListener = null;

    public AuctionClientEndpoint(URI endpointURI, MessageListener messageListener) {
        try {
            this.messageListener = messageListener;
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session session) {
        LOG.info("Connected ... " + session.getId());
        userSession = session;

    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        LOG.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
        userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client sends a message.
     *
     * @param message The text message
     */
    @OnMessage
    public Message onMessage(Message message, Session session) {
        LOG.info("Received ...." + message);

        if (messageListener != null) {
            try {
                return messageListener.onMessageReceived(message);
            } catch (Exception ex) {
                LOG.error("exception caught when sending message to message listener:", ex);
            }
        }
        return null;

    }

    /**
     * Send a message.
     *
     * @param message
     */
    public void sendMessage(Message message) {
        try {
            RemoteEndpoint.Async asyncRemote = userSession.getAsyncRemote();
            if (asyncRemote == null) {
                throw new RuntimeException("asyncRemote is null");
            }
            asyncRemote.sendObject(message);
        } catch (Exception ex) {
            LOG.error("problem sending message ", ex);
        }
    }

    public void close() {
        LOG.info("closing session");
        if (userSession != null) {
            try {
                userSession.close();
            } catch (IOException ex) {
            }

        }
    }

}
