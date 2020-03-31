/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service.test.manual;

import javax.websocket.DecodeException;
import javax.websocket.EncodeException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.auction.dto.MessageType;
import solent.ac.uk.com600.examples.websocket.auction.client.MessageDecoder;
import solent.ac.uk.com600.examples.websocket.auction.client.MessageEncoder;

/**
 *
 * @author cgallen
 */
public class TestEncodeDecode {

    @Test
    public void testEncodeDecode() throws EncodeException, DecodeException {

        MessageDecoder decoder = new MessageDecoder();
        MessageEncoder encoder = new MessageEncoder();
        
        Message m = new Message();
        m.setAuctionuuid("auctionuuid");
        m.setAuthKey("authKey");
        m.setBidderuuid("bidderuuid");
        m.setLotuuid("lotuuid");
        m.setValue(0.0);
        m.setMessageType(MessageType.TEST);
        
        String json = encoder.encode(m);
        
        System.out.println("encoded message:"+json);
        
        Message receivedMessage = decoder.decode(json);

        assertTrue(m.toString().equals(receivedMessage.toString()));

    }
}
