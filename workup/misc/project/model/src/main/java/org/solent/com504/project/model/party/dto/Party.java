package org.solent.com504.project.model.party.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.solent.com504.project.model.user.dto.User;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Entity
public class Party {

    private Long id;

    private String firstName;

    private String secondName;

    private PartyRole partyRole = PartyRole.UNDEFINED;

    private Address address = new Address(); // need not null initial value

    private PartyStatus partyStatus = PartyStatus.ACTIVE;

    // unique UUID created for every Party
    private String uuid = Long.toHexString(new Date().getTime()) ;

    private Boolean enabled = true;

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    private Set<User> users;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public PartyRole getPartyRole() {
        return partyRole;
    }

    public void setPartyRole(PartyRole partyRole) {
        this.partyRole = partyRole;
    }

    @Embedded
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PartyStatus getPartyStatus() {
        return partyStatus;
    }

    public void setPartyStatus(PartyStatus partystatus) {
        this.partyStatus = partystatus;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @ManyToMany(mappedBy = "parties", fetch = FetchType.LAZY,
            cascade={CascadeType.PERSIST, CascadeType.MERGE} )
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Party{" + "id=" + id + ", firstName=" + firstName + ", secondName=" + secondName + ", partyRole=" + partyRole + ", address=" + address + ", partyStatus=" + partyStatus + ", uuid=" + uuid + ", enabled=" + enabled + '}';
    }

}
