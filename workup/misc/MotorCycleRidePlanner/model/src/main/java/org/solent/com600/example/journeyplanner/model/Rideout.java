package org.solent.com600.example.journeyplanner.model;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Entity
public class Rideout {

    private Long id;

    @XmlElementWrapper(name = "riders")
    @XmlElement(name = "sysUser")
    private List<SysUser> riders = new ArrayList<SysUser>();

    private SysUser rideLeader=null;

    private String descriptionMd="";

    private Integer maxRiders=15;

    @XmlElementWrapper(name = "waitList")
    @XmlElement(name = "sysUser")
    private List<SysUser> waitlist = new ArrayList<SysUser>();

    private RideoutState rideoutstate = RideoutState.PLANNING;

    @XmlElementWrapper(name = "rideoutDays")
    @XmlElement(name = "rideoutDay")
    private List<RideoutDay> rideoutDays = new ArrayList<RideoutDay>();

    private String title="";

    private Date startDate = new Date();

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
    @JoinTable(name = "riders")
    @OrderBy("userName ASC")
    public List<SysUser> getRiders() {
        return riders;
    }

    public void setRiders(List<SysUser> riders) {
        this.riders = riders;
    }

    public SysUser getRideLeader() {
        return rideLeader;
    }

    public void setRideLeader(SysUser rideLeader) {
        this.rideLeader = rideLeader;
    }

    public String getDescriptionMd() {
        return descriptionMd;
    }

    public void setDescriptionMd(String descriptionMd) {
        this.descriptionMd = descriptionMd;
    }

    public Integer getMaxRiders() {
        return maxRiders;
    }

    public void setMaxRiders(Integer maxRiders) {
        this.maxRiders = maxRiders;
    }

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinTable(name = "waitlist")
    @OrderBy("userName ASC")
    public List<SysUser> getWaitlist() {
        return waitlist;
    }

    public void setWaitlist(List<SysUser> waitlist) {
        this.waitlist = waitlist;
    }

    public RideoutState getRideoutstate() {
        return rideoutstate;
    }

    public void setRideoutstate(RideoutState rideoutstate) {
        this.rideoutstate = rideoutstate;
    }

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderColumn(name = "dayindex")
    public List<RideoutDay> getRideoutDays() {
        return rideoutDays;
    }

    public void setRideoutDays(List<RideoutDay> rideoutDays) {
        this.rideoutDays = rideoutDays;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Rideout{" + "id=" + id + ", riders=" + riders + ", rideLeader=" + rideLeader + ", descriptionMd=" + descriptionMd + ", maxRiders=" + maxRiders + ", waitlist=" + waitlist + ", rideoutstate=" + rideoutstate + ", rideoutDays=" + rideoutDays + ", title=" + title + ", startDate=" + startDate + '}';
    }

}
