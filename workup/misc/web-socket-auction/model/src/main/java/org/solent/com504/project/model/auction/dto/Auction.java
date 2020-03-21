package org.solent.com504.project.model.auction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.solent.com504.project.model.party.dto.Party;

@Schema(description = "Auction object describes an auction")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Auction {

    private Date startTime;

    private Long id;

    @XmlElementWrapper(name = "lots")
    @XmlElement(name = "lot")
    private List<Lot> lots = new ArrayList();

    private String description;

    @XmlElementWrapper(name = "registeredPartys")
    @XmlElement(name = "party")
    private List<Party> registeredPartys = new ArrayList();

    // unique UUID created for every Auction
    private String auctionuuid = Long.toHexString(new Date().getTime());

    AuctionType auctionType = AuctionType.NORMAL;

    private AuctionStatus auctionStatus = AuctionStatus.PLANNING;

    public Auction() {
    }

    public Auction(Date startTime, String description) {
        this.startTime = startTime;
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Party> getRegisteredPartys() {
        return registeredPartys;
    }

    public void setRegisteredPartys(List<Party> registeredPartys) {
        this.registeredPartys = registeredPartys;
    }

    public String getAuctionuuid() {
        return auctionuuid;
    }

    public void setAuctionuuid(String auctionuuid) {
        this.auctionuuid = auctionuuid;
    }

    public AuctionStatus getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(AuctionStatus auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

    public AuctionType getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(AuctionType auctionType) {
        this.auctionType = auctionType;
    }

    @Override
    public String toString() {
        return "Auction{" + "startTime=" + startTime + ", id=" + id + ", lots=" + lots + ", description=" + description + ", registeredPartys=" + registeredPartys + ", auctionuuid=" + auctionuuid + ", auctionType=" + auctionType + ", auctionStatus=" + auctionStatus + '}';
    }



}
