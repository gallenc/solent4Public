package org.solent.com600.example.journeyplanner.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
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
public class SysUser {

    private Long Id;

    private Role role;

    private String userName;

    private String passWordHash;

    private String passwordSalt;

    private String password;

    private String surname;

    private String firstname;

    private Address address;

    private Insurance insurance;

    private String medicalMd;

    private String emergencyContactFirstName;

    private String emergencyContactSurname;

    private String emergencyContactRelationship;

    private Address emergencyContactAddress;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // all usernames are unique
    @Column(unique = true)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWordHash() {
        return passWordHash;
    }

    public void setPassWordHash(String passWordHash) {
        this.passWordHash = passWordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        @AttributeOverride(name = "number", column = @Column(name = "emgcyNumber")),
        @AttributeOverride(name = "addressLine1", column = @Column(name = "emgcyAddressLine1")),
        @AttributeOverride(name = "addressLine2", column = @Column(name = "emgcyAddressLine2")),
        @AttributeOverride(name = "County", column = @Column(name = "emgcyCounty")),
        @AttributeOverride(name = "Country", column = @Column(name = "emgcyCountry")),
        @AttributeOverride(name = "postcode", column = @Column(name = "emgcyPostcode")),
        @AttributeOverride(name = "latitude", column = @Column(name = "emgcyLatitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "emgcyLongditude")),
        @AttributeOverride(name = "telephone", column = @Column(name = "emgcyTelephone")),
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
        return "SysUser{" + "Id=" + Id + ", role=" + role + ", userName=" + userName + ", passWordHash=" + passWordHash + ", passwordSalt=" + passwordSalt + ", password=" + password + ", surname=" + surname + ", firstname=" + firstname + ", address=" + address + ", insurance=" + insurance + ", medicalMd=" + medicalMd + ", emergencyContactFirstName=" + emergencyContactFirstName + ", emergencyContactSurname=" + emergencyContactSurname + ", emergencyContactRelationship=" + emergencyContactRelationship + ", emergencyContactAddress=" + emergencyContactAddress + '}';
    }

}
