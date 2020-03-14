package org.solent.com504.project.model.bank.service;

import java.util.Date;
import java.util.List;
import org.solent.com504.project.model.bank.dto.BankAccount;
import org.solent.com504.project.model.bank.dto.Transaction;
import org.solent.com504.project.model.bank.dto.TransactionStatus;
import org.solent.com504.project.model.party.dto.Party;


public interface BankServiceFacade {

    public String getHeartbeat();

    public Transaction transferMoney(BankAccount fromAccount, BankAccount toAccount, Double amount);

    public BankAccount createAccount(Party owner, String sortCode, String accountNo, Double initialBalance);

    public Transaction findByTransactionId(String transactionId);

    public List<Transaction> findByDetails(BankAccount from, BankAccount to,TransactionStatus transactionStatus, Date fromDate, Date toDate);

    public BankAccount findBySortCodeAndAccountNo(String sortcode, String accountNo);

    public List<BankAccount> findByOwner(Party owner);
}
