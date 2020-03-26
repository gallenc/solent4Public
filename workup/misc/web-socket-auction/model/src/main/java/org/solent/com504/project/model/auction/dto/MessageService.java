/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.auction.dto;

/**
 *
 * @author cgallen
 */
public interface MessageService {

    public void broadcastMessage(Message message);

    public void registerForMessages(MessageListener messageListener);

    public void unRegisterForMessages(MessageListener messageListener);

    public void removeAllListeners();

}
