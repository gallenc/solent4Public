package org.solent.com504.project.model.auction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.solent.com504.project.model.party.dto.Party;
import org.solent.com504.project.model.flower.dto.Flower;

@Schema(description = "Flower Object which contains details of flowers")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Lot {

    private Long id;

    @XmlElementWrapper(name = "bids")
    @XmlElement(name = "bid")
    private List<Bid> bids = new ArrayList();

    private Bid winningBid;

    private Flower flowerType;

    private Long quantity =0L;

    private Double reservePrice =0.0;

    private Double soldPrice =0.0;
    
    // transient = not marshalled or persisted
    @XmlTransient
    private Double currentPrice =0.0;

    private Party seller;

    private Party buyer;

    // unique UUID created for every Auction
    private String lotuuid = Long.toHexString(new Date().getTime());

    private Boolean active = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public Bid getWinningBid() {
        return winningBid;
    }

    public void setWinningBid(Bid winningBid) {
        this.winningBid = winningBid;
    }

    public Flower getFlowerType() {
        return flowerType;
    }

    public void setFlowerType(Flower flowerType) {
        this.flowerType = flowerType;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(Double reservePrice) {
        this.reservePrice = reservePrice;
    }

    public Double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(Double soldPrice) {
        this.soldPrice = soldPrice;
    }

    public Party getSeller() {
        return seller;
    }

    public void setSeller(Party seller) {
        this.seller = seller;
    }

    public Party getBuyer() {
        return buyer;
    }

    public void setBuyer(Party buyer) {
        this.buyer = buyer;
    }

    public String getLotuuid() {
        return lotuuid;
    }

    public void setLotuuid(String lotuuid) {
        this.lotuuid = lotuuid;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active= active;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Lot{" + "id=" + id + ", bids=" + bids + ", winningBid=" + winningBid + ", flowerType=" + flowerType + ", quantity=" + quantity + ", reservePrice=" + reservePrice + ", soldPrice=" + soldPrice + ", currentPrice=" + currentPrice + ", seller=" + seller + ", buyer=" + buyer + ", lotuuid=" + lotuuid + ", active=" + active + '}';
    }
    
    
 

  


}
