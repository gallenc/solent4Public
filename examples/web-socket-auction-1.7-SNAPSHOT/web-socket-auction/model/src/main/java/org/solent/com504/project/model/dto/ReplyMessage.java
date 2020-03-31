package org.solent.com504.project.model.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.Bid;
import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.party.dto.Party;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReplyMessage {

    private Integer code = null;

    private String debugMessage = null;

    @XmlElementWrapper(name = "stringlist")
    @XmlElement(name = "string")
    private List<String> stringList = null;

    @XmlElementWrapper(name = "partylist")
    @XmlElement(name = "party")
    private List<Party> partyList = null;

    @XmlElementWrapper(name = "auctionlist")
    @XmlElement(name = "auction")
    private List<Auction> auctionList = null;

    @XmlElementWrapper(name = "lotlist")
    @XmlElement(name = "lot")
    private List<Lot> lotList = null;

    @XmlElementWrapper(name = "bidlist")
    @XmlElement(name = "bid")
    private List<Bid> bidList = null;
    
    private String authKey = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public List<Party> getPartyList() {
        return partyList;
    }

    public void setPartyList(List<Party> partyList) {
        this.partyList = partyList;
    }

    public List<Auction> getAuctionList() {
        return auctionList;
    }

    public void setAuctionList(List<Auction> auctionList) {
        this.auctionList = auctionList;
    }

    public List<Lot> getLotList() {
        return lotList;
    }

    public void setLotList(List<Lot> lotList) {
        this.lotList = lotList;
    }

    public List<Bid> getBidList() {
        return bidList;
    }

    public void setBidList(List<Bid> bidList) {
        this.bidList = bidList;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
    
    

}
