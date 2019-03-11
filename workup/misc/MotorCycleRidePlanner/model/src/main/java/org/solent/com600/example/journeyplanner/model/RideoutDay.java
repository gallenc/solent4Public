package org.solent.com600.example.journeyplanner.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Entity
public class RideoutDay {

    private Long id;

    @XmlElementWrapper(name = "itinearyItems")
    @XmlElement(name = "itinearyItem")
    private List<ItinearyItem> itinearyItems = new ArrayList<ItinearyItem>();

    private String descriptionMd;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    public List<ItinearyItem> getItinearyItems() {
        return itinearyItems;
    }

    public void setItinearyItems(List<ItinearyItem> itinearyItems) {
        this.itinearyItems = itinearyItems;
    }

    public String getDescriptionMd() {
        return descriptionMd;
    }

    public void setDescriptionMd(String descriptionMd) {
        this.descriptionMd = descriptionMd;
    }

    @Override
    public String toString() {
        return "RideoutDay{" + "id=" + id + ", itinearyItems=" + itinearyItems + ", descriptionMd=" + descriptionMd + '}';
    }

}
