/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com600.example.journeyplanner.web;

/**
 *
 * @author gallenc
 */
import java.io.IOException;
import java.util.StringTokenizer;
// possibly replace with https://stackoverflow.com/questions/35301409/migrating-from-sun-misc-base64-to-java-8-java-util-base64
import sun.misc.BASE64Decoder;

public class AuthenticationService {

    public boolean authenticate(String credential) {
        if (null == credential) {
            return false;
        }
        // header value format will be "Basic encodedstring" for Basic
        // authentication. Example "Basic YWRtaW46YWRtaW4="
        final String encodedUserPassword = credential.replaceFirst("Basic" + " ", "");
        String usernameAndPassword = null;
        try {

            byte[] decodedBytes = new BASE64Decoder().decodeBuffer(encodedUserPassword);
            usernameAndPassword = new String(decodedBytes, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        // we have fixed the userid and password as admin
        // call some UserService/LDAP here
        boolean authenticationStatus = "admin".equals(username) && "admin".equals(password);
        return authenticationStatus;
    }
}
