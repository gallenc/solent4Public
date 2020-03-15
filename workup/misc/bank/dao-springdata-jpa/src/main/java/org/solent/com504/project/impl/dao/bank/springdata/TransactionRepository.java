/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * AND open the template in the editor.
 */
package org.solent.com504.project.impl.dao.bank.springdata;

import java.util.Date;
import java.util.List;
import org.solent.com504.project.model.bank.dto.BankAccount;
import org.solent.com504.project.model.bank.dto.Transaction;
import org.solent.com504.project.model.bank.dto.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cgallen
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.transactionId = :transactionId")
    List<Transaction> findByTransactionId(@Param("transactionId") String transactionId);

    @Query("SELECT t FROM Transaction t WHERE "
            + "(:fromAccount IS null OR t.fromAccount = :fromAccount)"
            + "AND (:toAccount IS null OR t.toAccount = :toAccount) "
            + "AND (:transactionStatus IS null OR t.status = :transactionStatus) "
            + "AND (:fromDate IS null OR :toDate IS null ) or (t.date BETWEEN :fromDate AND :toDate) ")
    List<Transaction> findByDetails(@Param("fromAccount") BankAccount fromAccount,
            @Param("toAccount") BankAccount toAccount,
            @Param("transactionStatus") TransactionStatus transactionStatus,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
