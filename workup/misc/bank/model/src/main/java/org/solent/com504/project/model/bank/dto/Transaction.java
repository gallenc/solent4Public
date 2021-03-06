package org.solent.com504.project.model.bank.dto;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.solent.com504.project.model.party.dto.Party;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Transaction {

    private Long id;

    private String transactionId;

    private BankAccount fromAccount;

    private BankAccount toAccount;

    private Double amount;

    private Party initiator;

    private TransactionStatus status;

    private String reason;

    private String userMessage;

    private Date date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_ACCOUNT_ID")
    public BankAccount getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(BankAccount fromAccount) {
        this.fromAccount = fromAccount;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_ACCOUNT_ID")
    public BankAccount getToAccount() {
        return toAccount;
    }

    public void setToAccount(BankAccount toAccount) {
        this.toAccount = toAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INITIATOR_ID")
    public Party getInitiator() {
        return initiator;
    }

    public void setInitiator(Party initiator) {
        this.initiator = initiator;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", transactionId=" + transactionId + ", fromAccount=" + fromAccount + ", toAccount=" + toAccount + ", amount=" + amount + ", initiator=" + initiator + ", status=" + status + ", reason=" + reason + ", userMessage=" + userMessage + ", date=" + date + '}';
    }

}
