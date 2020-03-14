package org.solent.com504.project.model.bank.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.solent.com504.project.model.party.dto.Party;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BankAccount {

    private String sortCode;

    private String accountNo;

    private Double balance;

    private Party owner;

    private Long id;

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Party getOwner() {
        return owner;
    }

    public void setOwner(Party owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BankAccount{" + "sortCode=" + sortCode + ", accountNo=" + accountNo + ", balance=" + balance + ", owner=" + owner + ", id=" + id + '}';
    }
    
    
}
