package org.solent.com504.project.model.auction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.solent.com504.project.model.party.dto.Party;

@Schema(description = "Flower Object which contains details of flowers")

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bid {

    private Date time;
    
    private Long id;

    private Party bidder;
    
   private String bidderuuid;

    private Lot lot;

    private Double amount;
    
    // should be unique at time of creation but not best solution
    private String biduuid =UUID.randomUUID().toString(); 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Party getBidder() {
        return bidder;
    }

    public void setBidder(Party bidder) {
        this.bidder = bidder;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getBiduuid() {
        return biduuid;
    }

    public void setBiduuid(String biduuid) {
        this.biduuid = biduuid;
    }

    public String getBidderuuid() {
        return bidderuuid;
    }

    public void setBidderuuid(String bidderuuid) {
        this.bidderuuid = bidderuuid;
    }

    @Override
    public String toString() {
        return "Bid{" + "time=" + time + ", id=" + id + ", bidder=" + bidder + ", bidderuuid=" + bidderuuid + ", lot=" + lot + ", amount=" + amount + ", biduuid=" + biduuid + '}';
    }




    
    
}
