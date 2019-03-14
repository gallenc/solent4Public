package org.solent.com600.example.journeyplanner.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Entity
public class ItineraryItem {

    private Long id;

    private String startTime;

    private String endTime;

    private String descriptionMd;

    private String bookingReference;

    private Address address;

    private Double distance;

    private String gisRoute;

    private ItineraryItemType itineraryItemType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescriptionMd() {
        return descriptionMd;
    }

    public void setDescriptionMd(String descriptionMd) {
        this.descriptionMd = descriptionMd;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    @Embedded
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getGisRoute() {
        return gisRoute;
    }

    public void setGisRoute(String gisRoute) {
        this.gisRoute = gisRoute;
    }

    public ItineraryItemType getItineraryItemType() {
        return itineraryItemType;
    }

    public void setItineraryItemType(ItineraryItemType itineraryItemType) {
        this.itineraryItemType = itineraryItemType;
    }

    @Override
    public String toString() {
        return "ItineraryItem{" + "id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", descriptionMd=" + descriptionMd + ", bookingReference=" + bookingReference + ", address=" + address + ", distance=" + distance + ", gisRoute=" + gisRoute + ", itineraryItemType=" + itineraryItemType + '}';
    }

}
