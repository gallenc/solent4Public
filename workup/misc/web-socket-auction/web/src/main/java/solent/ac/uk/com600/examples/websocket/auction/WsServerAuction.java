/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solent.ac.uk.com600.examples.websocket.auction;

/**
 *
 * @author cgallen see https://tomcat.apache.org/tomcat-7.0-doc/web-socket-howto.html see
 * https://examples.javacodegeeks.com/enterprise-java/tomcat/apache-tomcat-websocket-tutorial/
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.EncodeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.solent.com504.project.model.auction.service.AuctionService;
import org.solent.com504.project.model.auction.dto.Message;
import org.solent.com504.project.model.auction.dto.MessageType;
import solent.ac.uk.com600.examples.websocket.auction.MessageDecoder;
import solent.ac.uk.com600.examples.websocket.auction.MessageEncoder;

@ServerEndpoint(value = "/auctionwebsocket/{partyuuid}",
        decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class WsServerAuction {

    final static Logger LOG = LogManager.getLogger(WsServerAuction.class);
    
    private AuctionService auctionService;

    private Session session;
    private static final Set<WsServerAuction> auctionEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> partys = new HashMap<>();

    @OnOpen
    public void onOpen(Session session,
            @PathParam("partyuuid") String partyuuid
            ) throws IOException, EncodeException {

        LOG.debug("onOpen new session partyuuid=" + partyuuid );

        this.session = session;
        auctionEndpoints.add(this);
        partys.put(session.getId(), partyuuid);

        Message message = new Message();
        message.setBidderuuid(partyuuid);
        message.setMessageType(MessageType.NEW_PARTICIPANT);
        
        broadcast(message);
    }

    @OnMessage
    public Message onMessage(Session session, Message message) throws IOException, EncodeException {
        LOG.debug("onMessage message=" + message);
        String bidderuuid = partys.get(session.getId());
        message.setBidderuuid(bidderuuid);
        
        Message reply = auctionService.onMessageReceived(message);
        
        return reply;
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        LOG.debug("onClose called");
        auctionEndpoints.remove(this);
        Message message = new Message();
       String bidderuuid = partys.get(session.getId());
        message.setBidderuuid(bidderuuid);
        message.setMessageType(MessageType.PARTICIPANT_LEFT);
        
        broadcast(message);

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOG.debug("onError called ", throwable);
        // Do error handling here
    }

    private static void broadcast(Message message) throws IOException, EncodeException {
        auctionEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException e) {
                    LOG.debug("broadcast exception ", e);
                }
            }
        });
    }
    
    
    public void setAuctionService(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

}
