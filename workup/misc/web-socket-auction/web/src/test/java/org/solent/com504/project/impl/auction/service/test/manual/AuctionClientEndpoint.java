/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service.test.manual;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
 
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;
import org.solent.com504.project.model.auction.dto.Message;
 

import solent.ac.uk.com600.examples.websocket.auction.MessageDecoder;
import solent.ac.uk.com600.examples.websocket.auction.MessageEncoder;


 
@ClientEndpoint(decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class AuctionClientEndpoint {
 
    final static Logger LOG = LogManager.getLogger(AuctionClientEndpoint.class);
    
 
    @OnOpen
    public void onOpen(Session session) {
        LOG.info("Connected ... " + session.getId());
        try {
            session.getBasicRemote().sendText("start");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 
    @OnMessage
    public Message onMessage(Message message, Session session) {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        
            LOG .info("Received ...." + message);
            
            return message;
       
    }
 
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        LOG.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }
    
    
   

}