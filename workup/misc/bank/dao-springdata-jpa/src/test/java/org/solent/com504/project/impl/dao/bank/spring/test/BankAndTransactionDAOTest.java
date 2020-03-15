/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.bank.spring.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.solent.com504.project.model.bank.dao.BankAccountDAO;
import org.solent.com504.project.model.bank.dao.TransactionDAO;
import org.solent.com504.project.model.bank.dto.BankAccount;
import org.solent.com504.project.model.party.dto.Party;
import org.solent.com504.project.model.party.dto.PartyRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.solent.com504.project.model.party.dao.PartyDAO;
import org.solent.com504.project.model.party.dto.Address;
import org.solent.com504.project.model.user.dao.RoleDAO;
import org.solent.com504.project.model.user.dao.UserDAO;
import org.solent.com504.project.model.user.dto.User;

/**
 * NOTE tests cannot be @transactional if you are using the id of an entity which has not been saveAndFlush'ed this could be an eclipselink problem or a
 * database problem. In practice make tests non transactional and make sure the database is clean at start of tests.
 *
 * @author cgallen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
@Transactional
public class BankAndTransactionDAOTest {

    final static Logger LOG = LogManager.getLogger(BankAndTransactionDAOTest.class);

    @Autowired
    private TransactionDAO transactionDao = null;

    @Autowired
    private BankAccountDAO bankAccountDao = null;

    @Autowired
    private PartyDAO partyDao = null;

    @Autowired
    private UserDAO userDao = null;

    @Before
    public void before() {
        assertNotNull(partyDao);
        assertNotNull(bankAccountDao);
        assertNotNull(transactionDao);
    }

    // initialises database for each test
    private void init() {
        // delete all in database

        transactionDao.deleteAllTransactions();
        bankAccountDao.deleteAllAccounts();
        partyDao.deleteAll();
        userDao.deleteAll();

        for (int i = 1; i < 6; i++) {
            User user = new User();
            Address a = new Address();
            a.setAddressLine1("address_A_" + i);
            user.setAddress(a);
            user.setUsername("username_A_" + i);
            user.setFirstName("userfirstName_A_" + i);
            user.setSecondName("usersecondName_A_" + i);
            user = userDao.save(user);
            LOG.debug("created test user:" + user);
        }

        List<User> users = userDao.findAll();
        assertEquals(5, users.size());

        // add 5 buyer
        for (int i = 1; i < 6; i++) {
            Party party = new Party();
            Address a = new Address();
            a.setAddressLine1("address_A_" + i);
            party.setAddress(a);
            party.setFirstName("partyfirstName_A_" + i);
            party.setSecondName("partysecondName_A_" + i);
            party.setPartyRole(PartyRole.BUYER);

            Set<User> userset = new HashSet();
            userset.add(users.get(i - 1));
            party.setUsers(userset);

            party = partyDao.save(party);
            LOG.debug("created test party:" + party);
        }
        // add 5 undefined
        for (int i = 1; i < 6; i++) {
            Party party = new Party();
            Address a = new Address();
            a.setAddressLine1("address_B_" + i);
            party.setAddress(a);
            party.setFirstName("partyfirstName_B_" + i);
            party.setSecondName("partysecondName_B_" + i);
            party.setPartyRole(PartyRole.UNDEFINED);

            Set<User> userset = new HashSet();
            userset.add(users.get(i - 1));
            party.setUsers(userset);

            party = partyDao.save(party);
            LOG.debug("created test party:" + party);
        }

        // every party has 2 bank accounts
        List<Party> parties = partyDao.findAll();
        for (Party party : parties) {
            BankAccount bankAccount = createBankAccount(party);
            bankAccount = bankAccountDao.save(bankAccount);
            LOG.debug("created account " + bankAccount);

            bankAccount = createBankAccount(party);
            bankAccount = bankAccountDao.save(bankAccount);
            LOG.debug("created account " + bankAccount);
        }

    }

    private BankAccount createBankAccount(Party party) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setOwner(party);

        // generating a random account number
        Integer x = (int) (Math.random() * 10000000);

        // prints out leading zeros
        String xstr = String.format("%010d", x);
        String accountNo = xstr.substring(xstr.length() - 10);

        // String accountNo="12345678";
        bankAccount.setAccountNo(accountNo);
        String sortCode = "110854";
        bankAccount.setSortCode(sortCode);

        return bankAccount;
    }

    // see https://www.baeldung.com/spring-data-jpa-save-saveandflush 
    // https://www.marcobehler.com/2014/06/25/should-my-tests-be-transactional 
    //@Transactional
    @Test
    public void createDAOTest() {
        LOG.debug("start of createDAOTest");
        // this test simply runs the before method
        LOG.debug("end of createDAOTest");
    }

    @Test
    public void initTest() {
        LOG.debug("start of initTest");
        init();
        LOG.debug("end of initTest");
    }

    @Test
    public void testFindBySortCodeAndAccountNo() {
        LOG.debug("start of testFindBySortCodeAndAccountNo");
        init();

        // find a first bank account
        List<Party> partyList = partyDao.findAll();
        Party owner = partyList.get(0);
        LOG.debug("finding accounts for owner =" + owner);
        List<BankAccount> bankAccounts = bankAccountDao.findByOwner(owner);

        // get the sort code and account number
        BankAccount bankAccount1 = bankAccounts.get(0);
        String sortcode = bankAccount1.getSortCode();
        String accountNo = bankAccount1.getAccountNo();

        // try and find same account using a differnt search
        BankAccount bankAccount2 = bankAccountDao.findBySortCodeAndAccountNo(sortcode, accountNo);
        assertNotNull(bankAccount2);

        assertTrue(bankAccount2.toString().equals(bankAccount1.toString()));

        LOG.debug("end of testFindBySortCodeAndAccountNo");
    }

    @Test
    public void testFindByOwner() {
        LOG.debug("start of testFindByOwner");
        init();

        // find bank accounts by owners
        List<Party> partyList = partyDao.findAll();
        Party owner = partyList.get(0);
        LOG.debug("finding accounts for owner =" + owner);
        List<BankAccount> bankAccounts = bankAccountDao.findByOwner(owner);
        assertEquals(2, bankAccounts.size());
        for (BankAccount ba : bankAccounts) {
            LOG.debug("found bankaccount " + ba);
        }
        LOG.debug("end of testFindByOwner");

    }
    
        @Test
    public void testTransactions() {
        LOG.debug("start of testTransactions");
        init();
        
        LOG.debug("start of testTransactions");
    }

}
