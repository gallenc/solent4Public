package org.solent.com504.project.model.auction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Schema(description = "Flower Object which contains details of flowers")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {

    private MessageType messageType;

    private String lotuuid;

    private String auctionuuid;

    private Double value;

    private String bidderuuid;

    private String debugMessage;

    private String authKey;

    public String getLotuuid() {
        return lotuuid;
    }

    public void setLotuuid(String lotuuid) {
        this.lotuuid = lotuuid;
    }

    public String getAuctionuuid() {
        return auctionuuid;
    }

    public void setAuctionuuid(String auctionuuid) {
        this.auctionuuid = auctionuuid;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getBidderuuid() {
        return bidderuuid;
    }

    public void setBidderuuid(String bidderuuid) {
        this.bidderuuid = bidderuuid;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    @Override
    public String toString() {
        return "Message{ messageType=" + messageType +" lotuuid=" + lotuuid + ", auctionuuid=" + auctionuuid + ", value=" + value + ", bidderuuid=" + bidderuuid + ", debugMessage=" + debugMessage +  ", authKey=" + authKey + '}';
    }

}
