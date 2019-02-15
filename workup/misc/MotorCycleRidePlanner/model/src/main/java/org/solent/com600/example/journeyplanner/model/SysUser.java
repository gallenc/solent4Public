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

    private ProcessInfo processInfo = new ProcessInfo();

    private UserInfo userInfo = new UserInfo();

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Embedded
    public ProcessInfo getProcessInfo() {
        return processInfo;
    }

    public void setProcessInfo(ProcessInfo processInfo) {
        this.processInfo = processInfo;
    }

    @Embedded
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * note toString does NOT print password related info *
     */
    @Override
    public String toString() {
        return "SysUser{" + "Id=" + Id
                + ", role=" + role
                + ", userName=" + userName
                + ", passWordHash=" + ((passWordHash == null) ? "null" : "***set but not shown***")
                + ", passwordSalt=" + ((passwordSalt == null) ? "null" : "***set but not shown***")
                + ", password=" + ((password == null) ? "null" : "***set but not shown***")
                + ", processInfo=" + processInfo
                + ", userInfo=" + userInfo + '}';
    }

}
