/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.auction.service.BankingService;

/**
 *
 * @author cgallen
 */
public class BankingServiceImpl implements BankingService {

    final static Logger LOG = LogManager.getLogger(BankingServiceImpl.class);

    @Override
    public boolean transferMoney(String fromSortCode, String fromAccount, String toSortCode, String toAccount, double amount) {
        LOG.info("transferMoney called fromSortCode=" + fromSortCode
                + " fromAccount=" + fromAccount
                + " toSortCode=" + toSortCode
                + " toAccount=" + toAccount
                + " amount=" + amount);
        
        return true;
    }

}
