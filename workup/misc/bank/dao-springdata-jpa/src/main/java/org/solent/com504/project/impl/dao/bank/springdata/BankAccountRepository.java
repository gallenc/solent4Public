/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.bank.springdata;

import java.util.List;
import org.solent.com504.project.model.bank.dto.BankAccount;
import org.solent.com504.project.model.party.dto.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cgallen
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    
    
    @Query("select b from BankAccount b where b.sortCode = :sortCode AND b.accountNo = :accountNo")
    List<BankAccount>  findBySortCodeAndAccountNo(@Param("sortCode")String sortcode,@Param("accountNo") String accountNo);
    
    @Query("select b from BankAccount b where b.owner = :owner")
    List<BankAccount> findByOwner(@Param("owner") Party owner);
    
}
