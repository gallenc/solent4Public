/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.auction.message;

import org.solent.com504.project.model.auction.dto.Message;

/**
 *
 * @author cgallen
 */
public interface MessageListener {
    
    public Message onMessageReceived(Message message);
    
}
