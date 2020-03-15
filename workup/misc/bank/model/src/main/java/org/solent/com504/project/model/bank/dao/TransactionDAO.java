package org.solent.com504.project.model.bank.dao;

import java.util.Date;
import java.util.List;
import org.solent.com504.project.model.bank.dto.BankAccount;
import org.solent.com504.project.model.bank.dto.Transaction;
import org.solent.com504.project.model.bank.dto.TransactionStatus;

public interface TransactionDAO {

    public Transaction findById(Long id);

    public Transaction findByTransactionId(String transactionIid);

    public List<Transaction> findByDetails(BankAccount fromAccount, BankAccount toAccount, TransactionStatus transactionStatus, Date fromDate, Date toDate);

    public Transaction save(Transaction transaction);

    public void deleteAllTransactions();

    public void deleteTransaction(Long transactionId);
    
}
