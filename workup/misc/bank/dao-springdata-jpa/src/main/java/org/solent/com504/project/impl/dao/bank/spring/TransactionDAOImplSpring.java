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
        Optional<Transaction> iii = transactionRepository.findById(id);
        if (iii.isPresent()) {
            return iii.get();
        }
        return null;
    }

    @Override
    public Transaction findByTransactionId(String transactionIid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Transaction> findByDetails(BankAccount from, BankAccount to, TransactionStatus transactionstatus, Date fromDate, Date toDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
