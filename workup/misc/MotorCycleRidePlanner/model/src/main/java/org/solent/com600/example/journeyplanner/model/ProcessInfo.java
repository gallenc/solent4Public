package org.solent.com600.example.journeyplanner.model;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Embeddable
public class ProcessInfo {

    private Boolean insuranceVerified;

    public Boolean getInsuranceVerified() {
        return insuranceVerified;
    }

    public void setInsuranceVerified(Boolean insuranceVerified) {
        this.insuranceVerified = insuranceVerified;
    }

    @Override
    public String toString() {
        return "ProcessInfo{" + "insuranceVerified=" + insuranceVerified + '}';
    }
    
    
}
