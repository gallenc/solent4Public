/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.impl.auction.service.CheckAuth;
import org.junit.Test;
import static org.junit.Assert.*;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.party.dto.Party;

/**
 *
 * @author cgallen
 */
public class TestCheckAuth {
      final static Logger LOG = LogManager.getLogger(TestCheckAuth.class);

    @Test
    public void testAuthKey() {
        String auctionuuid = new Auction().getAuctionuuid();
        String partyUuid = new Party().getUuid();
        String authKey = CheckAuth.createAuctionKey(auctionuuid, partyUuid);
        LOG.debug("checking authkey for: authKey="+authKey
                + " auctionuuid="+auctionuuid
                + " partyUuid="+partyUuid);
        assertTrue(CheckAuth.checkAuctionKey(authKey, auctionuuid, partyUuid));

    }

}
