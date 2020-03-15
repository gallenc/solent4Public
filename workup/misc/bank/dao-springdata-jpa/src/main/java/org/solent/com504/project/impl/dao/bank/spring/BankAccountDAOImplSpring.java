/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.bank.spring;

import java.util.List;
import java.util.Optional;
import org.solent.com504.project.impl.dao.bank.springdata.BankAccountRepository;
import org.solent.com504.project.model.bank.dao.BankAccountDAO;
import org.solent.com504.project.model.bank.dto.BankAccount;
import org.solent.com504.project.model.party.dto.Party;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author cgallen
 */
@Component
public class BankAccountDAOImplSpring implements BankAccountDAO {

    @Autowired
    private BankAccountRepository bankAccountRepository = null;

    @Override
    public BankAccount findById(Long id) {
        Optional<BankAccount> i = bankAccountRepository.findById(id);
        if (i.isPresent()) {
            return i.get();
        }
        return null;
    }

    @Override
    public BankAccount save(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount findBySortCodeAndAccountNo(String sortCode, String accountNo) {
        List<BankAccount> accountList = bankAccountRepository.findBySortCodeAndAccountNo(sortCode, accountNo);
        if (accountList.isEmpty())  {
            return null;
        } else {
            return accountList.get(0);
        }
    }

    @Override
    public List<BankAccount> findByOwner(Party owner) {
        return bankAccountRepository.findByOwner(owner);
    }

    @Override
    public void deleteAccount(Long id) {
        bankAccountRepository.deleteById(id);
    }

    @Override
    public void deleteAllAccounts() {
        bankAccountRepository.deleteAll();
    }

}
