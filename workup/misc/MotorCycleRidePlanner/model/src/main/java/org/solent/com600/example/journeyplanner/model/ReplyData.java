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

    public List<SysUser> getSysUserList() {
        return sysUserList;
    }

    public void setSysUserList(List<SysUser> sysUserList) {
        this.sysUserList = sysUserList;
    }

    @Override
    public String toString() {
        return "ReplyData{" + "sysUserList=" + sysUserList + '}';
    }
    
}
