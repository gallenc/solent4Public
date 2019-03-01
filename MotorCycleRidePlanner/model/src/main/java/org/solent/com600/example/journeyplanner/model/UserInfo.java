package org.solent.com600.example.journeyplanner.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Embeddable
public class UserInfo {

    private String surname;

    private String firstname;

    private Address address = new Address();

    private String emergencyContactFirstName;

    private Insurance insurance = new Insurance();

    private String medicalMd;

    private String emergencyContactSurname;

    private Address emergencyContactAddress = new Address();

    private String emergencyContactRelationship;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Embedded
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Embedded
    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public String getMedicalMd() {
        return medicalMd;
    }

    public void setMedicalMd(String medicalMd) {
        this.medicalMd = medicalMd;
    }

    public String getEmergencyContactFirstName() {
        return emergencyContactFirstName;
    }

    public void setEmergencyContactFirstName(String emergencyContactFirstName) {
        this.emergencyContactFirstName = emergencyContactFirstName;
    }

    public String getEmergencyContactSurname() {
        return emergencyContactSurname;
    }

    public void setEmergencyContactSurname(String emergencyContactSurname) {
        this.emergencyContactSurname = emergencyContactSurname;
    }

    public String getEmergencyContactRelationship() {
        return emergencyContactRelationship;
    }

    public void setEmergencyContactRelationship(String emergencyContactRelationship) {
        this.emergencyContactRelationship = emergencyContactRelationship;
    }

    // note this is done because we need table column names to be unique
    // if we had an address table this would be avoided
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "number", column = @Column(name = "emgcyNumber"))
        ,
        @AttributeOverride(name = "addressLine1", column = @Column(name = "emgcyAddressLine1"))
        ,
        @AttributeOverride(name = "addressLine2", column = @Column(name = "emgcyAddressLine2"))
        ,
        @AttributeOverride(name = "County", column = @Column(name = "emgcyCounty"))
        ,
        @AttributeOverride(name = "Country", column = @Column(name = "emgcyCountry"))
        ,
        @AttributeOverride(name = "postcode", column = @Column(name = "emgcyPostcode"))
        ,
        @AttributeOverride(name = "latitude", column = @Column(name = "emgcyLatitude"))
        ,
        @AttributeOverride(name = "longitude", column = @Column(name = "emgcyLongditude"))
        ,
        @AttributeOverride(name = "telephone", column = @Column(name = "emgcyTelephone"))
        ,
        @AttributeOverride(name = "mobile", column = @Column(name = "emgcyMobile"))
    })
    public Address getEmergencyContactAddress() {
        return emergencyContactAddress;
    }

    public void setEmergencyContactAddress(Address emergencyContactAddress) {
        this.emergencyContactAddress = emergencyContactAddress;
    }

    @Override
    public String toString() {
        return "UserInfo{" + "surname=" + surname + ", firstname=" + firstname + ", address=" + address + ", emergencyContactFirstName=" + emergencyContactFirstName + ", insurance=" + insurance + ", medicalMd=" + medicalMd + ", emergencyContactSurname=" + emergencyContactSurname + ", emergencyContactAddress=" + emergencyContactAddress + ", emergencyContactRelationship=" + emergencyContactRelationship + '}';
    }
    
    
}
