/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.bank.spring;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.solent.com504.project.impl.dao.bank.springdata.TransactionRepository;
import org.solent.com504.project.model.bank.dao.TransactionDAO;
import org.solent.com504.project.model.bank.dto.BankAccount;
import org.solent.com504.project.model.bank.dto.Transaction;
import org.solent.com504.project.model.bank.dto.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author cgallen
 */
@Component
public class TransactionDAOImplSpring implements TransactionDAO {

    @Autowired
    private TransactionRepository transactionRepository = null;

    @Override
    public Transaction findById(Long id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            return optionalTransaction.get();
        }
        return null;
    }

    @Override
    public Transaction findByTransactionId(String transactionId) {
        List<Transaction> tlist = transactionRepository.findByTransactionId(transactionId);
        if (tlist.isEmpty()) {
            return null;
        } else {
            return tlist.get(0);
        }

    }

    @Override
    public List<Transaction> findByDetails(BankAccount fromAccount, BankAccount toAccount, TransactionStatus transactionStatus, Date fromDate, Date toDate) {
        return transactionRepository.findByDetails(fromAccount,  toAccount,  transactionStatus, fromDate, toDate);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteAllTransactions() {
        transactionRepository.deleteAll();
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

}
