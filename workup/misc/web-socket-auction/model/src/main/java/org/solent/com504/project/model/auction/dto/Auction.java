package org.solent.com504.project.model.auction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.solent.com504.project.model.party.dto.Party;

@Schema(description = "Auction object describes an auction")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Auction {

    private Long id;

    private Date startTime;

    @XmlTransient
    private Long lotDuration = 1000*60*1L; // default 1 minute

    @XmlElementWrapper(name = "lots")
    @XmlElement(name = "lot")
    private List<Lot> lots = new ArrayList();

    private String description;

    @XmlElementWrapper(name = "registeredPartys")
    @XmlElement(name = "party")
    private List<Party> registeredPartys = new ArrayList();

    // unique UUID created for every Auction
    private String auctionuuid = UUID.randomUUID().toString(); 

    AuctionType auctionType = AuctionType.NORMAL;

    private AuctionOrLotStatus auctionStatus = AuctionOrLotStatus.PLANNING;

    // used  to index lots as auction proceeds
    @XmlTransient
    private int currentLotIndex = 0;

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

    public AuctionOrLotStatus getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(AuctionOrLotStatus auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

    public AuctionType getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(AuctionType auctionType) {
        this.auctionType = auctionType;
    }

    public int getCurrentLotIndex() {
        return currentLotIndex;
    }

    public void setCurrentLotIndex(int currentLotIndex) {
        this.currentLotIndex = currentLotIndex;
    }

    public Long getLotDuration() {
        return lotDuration;
    }

    public void setLotDuration(Long lotDuration) {
        this.lotDuration = lotDuration;
    }

    @Override
    public String toString() {
        return "Auction{" + "id=" + id + ", startTime=" + startTime + ", lotDuration=" + lotDuration + ", lots=" + lots + ", description=" + description + ", registeredPartys=" + registeredPartys + ", auctionuuid=" + auctionuuid + ", auctionType=" + auctionType + ", auctionStatus=" + auctionStatus + ", currentLotIndex=" + currentLotIndex + '}';
    }



}
