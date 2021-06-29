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
import java.util.logging.Level;
import javax.naming.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.com600.example.journeyplanner.model.ServiceFacade;
import org.solent.com600.example.journeyplanner.service.ServiceFacadeImpl;

// possibly replace with https://stackoverflow.com/questions/35301409/migrating-from-sun-misc-base64-to-java-8-java-util-base64
//import sun.misc.BASE64Decoder;
import java.util.Base64;
import java.util.Base64.Decoder;

public class AuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);

    public boolean authenticate(String credential) {
        LOG.debug("authenticationService.authenticate(credential) called");

        if (null == credential) {
            LOG.debug("credentail is null returning false ");
            return false;
        }
        // header value format will be "Basic encodedstring" for Basic
        // authentication. Example "Basic YWRtaW46YWRtaW4="
        final String encodedUserPassword = credential.replaceFirst("Basic" + " ", "");
        String usernameAndPassword = null;
        try {
            //byte[] decodedBytes = new BASE64Decoder().decodeBuffer(encodedUserPassword);
            byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword);
            usernameAndPassword = new String(decodedBytes, "UTF-8");
        } catch (IOException e) {
            LOG.error("exception decoding basic authentication: ", e);
        }
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();
        LOG.debug("trying to authenticate for username=" + username);

        // we have fixed the userid and password as admin
        // call some UserService/LDAP here
        ServiceFacade serviceFacade = WebObjectFactory.getServiceFactory().getServiceFacade();

        boolean authenticationStatus = false;
        try {
            authenticationStatus = serviceFacade.checkPasswordByUserName(password, username, ServiceFacadeImpl.SUPERADMIN_SYSUSERNAME);
        } catch (AuthenticationException ex) {
            java.util.logging.Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
        }

        // authenticationStatus = "admin".equals(username) && "admin".equals(password);
        LOG.debug("authenticated=" + authenticationStatus);
        return authenticationStatus;
    }
}
