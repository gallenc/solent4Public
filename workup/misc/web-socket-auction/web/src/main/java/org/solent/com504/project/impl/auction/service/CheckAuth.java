/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 * very insecure auth key generator and checker simply hashed the details with md5
 *
 * @author cgallen
 */
public class CheckAuth {

    public static String createAuctionKey(String auctionuuid, String partyUuid) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String input = "thePasswordIs" + auctionuuid + partyUuid;
            md.update(input.getBytes());
            byte[] digest = md.digest();
            String authkey = DatatypeConverter.printHexBinary(digest).toUpperCase();
            return authkey;
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean checkAuctionKey(String authkey, String auctionuuid, String partyUuid) {
        String inputkey = createAuctionKey(auctionuuid, partyUuid);
        return inputkey.equals(authkey);
    }

}
