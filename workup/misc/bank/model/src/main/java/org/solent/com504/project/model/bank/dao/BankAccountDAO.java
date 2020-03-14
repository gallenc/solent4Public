package org.solent.com504.project.model.bank.dao;

import java.util.List;
import org.solent.com504.project.model.bank.dto.BankAccount;
import org.solent.com504.project.model.party.dto.Party;


public interface BankAccountDAO {

    public BankAccount findById(Long id);

    public BankAccount save(BankAccount bankAccount);

    public BankAccount findBySortCodeAndAccountNo(String sortcode, String accountNo);

    public List<BankAccount> findByOwner(Party owner);

    public void deleteAccount(Long id);

    public void deleteAllAccounts();
}
