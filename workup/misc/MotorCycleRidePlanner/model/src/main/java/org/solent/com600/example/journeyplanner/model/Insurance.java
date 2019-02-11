package org.solent.com600.example.journeyplanner.model;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Embeddable
public class Insurance {

    private Date expirydate;

    private String insuranceNo;

    private boolean seenByStaff;

    public Date getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Date expirydate) {
        this.expirydate = expirydate;
    }

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    public boolean isSeenByStaff() {
        return seenByStaff;
    }

    public void setSeenByStaff(boolean seenByStaff) {
        this.seenByStaff = seenByStaff;
    }

    @Override
    public String toString() {
        return "Insurance{" + "expirydate=" + expirydate + ", insuranceNo=" + insuranceNo + ", seenByStaff=" + seenByStaff + '}';
    }
    
}
