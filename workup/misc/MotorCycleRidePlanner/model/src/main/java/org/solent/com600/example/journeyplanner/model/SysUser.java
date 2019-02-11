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

    private SysUser emergencyContact;

    private Insurance insurance;

    private String medicalMd;

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

    public SysUser getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(SysUser emergencyContact) {
        this.emergencyContact = emergencyContact;
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

    @Override
    public String toString() {
        return "SysUser{" + "Id=" + Id + ", role=" + role + ", userName=" + userName + ", passWordHash=" + passWordHash + ", passwordSalt=" + passwordSalt + ", password=" + password + ", surname=" + surname + ", firstname=" + firstname + ", address=" + address + ", emergencyContact=" + emergencyContact + ", insurance=" + insurance + ", medicalMd=" + medicalMd + '}';
    }



}
