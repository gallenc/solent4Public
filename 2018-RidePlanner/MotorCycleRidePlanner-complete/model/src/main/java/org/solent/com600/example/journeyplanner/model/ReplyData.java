package org.solent.com600.example.journeyplanner.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReplyData {

    @XmlElementWrapper(name = "sysUsers")
    @XmlElement(name = "sysUser")
    private List<SysUser> sysUserList = new ArrayList<SysUser>();

    @XmlElementWrapper(name = "itineraryItems")
    @XmlElement(name = "itineraryItem")
    private List<ItineraryItem> itineraryItemList= new ArrayList<ItineraryItem>();

    @XmlElementWrapper(name = "ridouts")
    @XmlElement(name = "ridout")
    private List<Rideout> ridoutList= new ArrayList<Rideout>();

    @XmlElementWrapper(name = "ridoutDays")
    @XmlElement(name = "ridoutDay")
    private List<RideoutDay> ridoutDayList= new ArrayList<RideoutDay>();

    public List<SysUser> getSysUserList() {
        return sysUserList;
    }

    public void setSysUserList(List<SysUser> sysUserList) {
        this.sysUserList = sysUserList;
    }

    public List<ItineraryItem> getItineraryItemList() {
        return itineraryItemList;
    }

    public void setItineraryItemList(List<ItineraryItem> itineraryItemList) {
        this.itineraryItemList = itineraryItemList;
    }

    public List<Rideout> getRidoutList() {
        return ridoutList;
    }

    public void setRidoutList(List<Rideout> ridoutList) {
        this.ridoutList = ridoutList;
    }

    public List<RideoutDay> getRidoutDayList() {
        return ridoutDayList;
    }

    public void setRidoutDayList(List<RideoutDay> ridoutDayList) {
        this.ridoutDayList = ridoutDayList;
    }

    @Override
    public String toString() {
        return "ReplyData{" + "sysUserList=" + sysUserList + ", itineraryItemList=" + itineraryItemList + ", ridoutList=" + ridoutList + ", ridoutDayList=" + ridoutDayList + '}';
    }

}
