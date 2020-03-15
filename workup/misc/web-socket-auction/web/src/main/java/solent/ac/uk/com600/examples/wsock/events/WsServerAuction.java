/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solent.ac.uk.com600.examples.wsock.events;

/**
 *
 * @author cgallen
 * see https://tomcat.apache.org/tomcat-7.0-doc/web-socket-howto.html
 * see https://examples.javacodegeeks.com/enterprise-java/tomcat/apache-tomcat-websocket-tutorial/
 */


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
 
@ServerEndpoint("/auctionwebsocket")
public class WsServerAuction {
    final static Logger LOG = LogManager.getLogger(WsServerAuction.class);
     
    @OnOpen
    public void onOpen(){
        LOG.debug("Open Connection ...");
    }
     
    @OnClose
    public void onClose(){
        LOG.debug("Close Connection ...");
    }
     
    @OnMessage
    public String onMessage(String message){
       LOG.debug("Message from the client: " + message);
        String echoMsg = "Echo from the server : " + message;
        return echoMsg;
    }
 
    @OnError
    public void onError(Throwable e){
        LOG.error("problem with websocket connection ",e);
    }
 
}