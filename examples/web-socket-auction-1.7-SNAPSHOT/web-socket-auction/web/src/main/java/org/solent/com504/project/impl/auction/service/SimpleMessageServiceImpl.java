/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.auction.message.MessageListener;
import org.solent.com504.project.model.auction.message.MessageService;

/**
 *
 * @author cgallen
 */
public class SimpleMessageServiceImpl implements MessageService {

    final static Logger LOG = LogManager.getLogger(SimpleMessageServiceImpl.class);

    Set<MessageListener> messageListeners = new CopyOnWriteArraySet<>();

    @Override
    public void broadcastMessage(Message message) {
        LOG.info("broadcasting message: \n" + message);
        for (MessageListener listener : messageListeners) {
            listener.onMessageReceived(message);
        }
    }

    @Override
    public void registerForMessages(MessageListener messageListener) {
        LOG.info("adding message listener: \n" + messageListener);
        messageListeners.add(messageListener);
    }

    @Override
    public void unRegisterForMessages(MessageListener messageListener) {
        LOG.info("removing message listener: \n" + messageListener);
        messageListeners.remove(messageListener);
    }

    @Override
    public void removeAllListeners() {
        messageListeners.clear();
    }

}
