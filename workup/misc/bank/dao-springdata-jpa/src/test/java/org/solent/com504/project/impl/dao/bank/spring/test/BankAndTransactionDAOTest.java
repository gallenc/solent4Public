/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.bank.spring.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import static java.util.Date.from;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.solent.com504.project.model.bank.dao.BankAccountDAO;
import org.solent.com504.project.model.bank.dao.TransactionDAO;
import org.solent.com504.project.model.bank.dto.BankAccount;
import org.solent.com504.project.model.bank.dto.Transaction;
import org.solent.com504.project.model.bank.dto.TransactionStatus;
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

    private List<Date> constantDateList = null;

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

        // get date constants
        constantDateList = getConstantDateList();

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

    private List<Date> getConstantDateList() {
        List<Date> constantDateList = new ArrayList();

        // will parse 2009-12-31 23:59:59.999
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        List<String> constantDateStrings = Arrays.asList("2020-01-1 12:59:59.999",
                "2020-01-5 12:59:59.999",
                "2020-01-10 12:59:59.999",
                "2020-01-20 12:59:59.999",
                "2020-01-30 12:59:59.999",
                "2020-02-5 12:59:59.999");

        for (String dateStr : constantDateStrings) {
            try {
                Date date = format.parse(dateStr);
                constantDateList.add(date);
            } catch (ParseException ex) {
                throw new RuntimeException("could not parse constant date: ", ex);
            }
        }
        return constantDateList;
    }

    @Test
    public void testTransactions() {
        LOG.debug("start of testTransactions");
        init();

        // find four bank accounts for 2 owners
        List<Party> partyList = partyDao.findAll();

        Party owner1 = partyList.get(0);
        LOG.debug("finding accounts for owner1 =" + owner1);
        List<BankAccount> bankAccounts1 = bankAccountDao.findByOwner(owner1);
        assertEquals(2, bankAccounts1.size());

        Party owner2 = partyList.get(1);
        LOG.debug("finding accounts for owner2 =" + owner2);
        List<BankAccount> bankAccounts2 = bankAccountDao.findByOwner(owner2);
        assertEquals(2, bankAccounts2.size());

        for (Date transactionDate : constantDateList) {
            Transaction transaction1 = new Transaction();
            transaction1.setAmount(10.00);
            transaction1.setInitiator(owner1);
            BankAccount fromAccount1 = bankAccounts1.get(0);
            BankAccount toAccount1 = bankAccounts2.get(0);
            transaction1.setFromAccount(fromAccount1);
            transaction1.setToAccount(toAccount1);
            transaction1.setDate(transactionDate);
            transaction1.setStatus(TransactionStatus.COMPLETE);

            // creates unique transactionId but only in scope of date list
            String transactionId = Long.toHexString(transactionDate.getTime());
            transaction1.setTransactionId(transactionId);

            transactionDao.save(transaction1);

            // check can find by transacionId
            Transaction tx = transactionDao.findByTransactionId(transactionId);
            assertNotNull(tx);
            assertTrue(tx.toString().equals(transaction1.toString()));

        }

        // transaction test 1
        {
            TransactionStatus transactionStatus = TransactionStatus.COMPLETE;
            BankAccount fromAccount = null;
            BankAccount toAccount = null;
            Date fromDate = null;
            Date toDate = null;

            List<Transaction> transactionList = transactionDao.findByDetails(fromAccount, toAccount, transactionStatus, fromDate, toDate);
            assertFalse(transactionList.isEmpty());

            transactionStatus = TransactionStatus.DECLINED;
            transactionList = transactionDao.findByDetails(fromAccount, toAccount, transactionStatus, fromDate, toDate);
            assertTrue(transactionList.isEmpty());
        }

        // transaction test 2
        {
            TransactionStatus transactionStatus = null;
            BankAccount fromAccount = null;
            BankAccount toAccount = null;
            Date fromDate = constantDateList.get(0);
            Date toDate = constantDateList.get(3);

            List<Transaction> transactionList = transactionDao.findByDetails(fromAccount, toAccount, transactionStatus, fromDate, toDate);

            LOG.debug("found " + transactionList.size() + " transactions between fromDate=" + fromDate + " and toDate=" + toDate);
            assertFalse(transactionList.isEmpty());

        }

        // transaction test 3
        {
            TransactionStatus transactionStatus = null;
            BankAccount fromAccount = null;
            BankAccount toAccount = bankAccounts2.get(0);
            Date fromDate = null;
            Date toDate = null;

            List<Transaction> transactionList = transactionDao.findByDetails(fromAccount, toAccount, transactionStatus, fromDate, toDate);

            LOG.debug("found " + transactionList.size() + " transactions for toAccount=" + toAccount);
            assertFalse(transactionList.isEmpty());

        }

        // transaction test 4
        {
            TransactionStatus transactionStatus = null;
            BankAccount fromAccount = bankAccounts1.get(0);
            BankAccount toAccount = null;
            Date fromDate = null;
            Date toDate = null;

            List<Transaction> transactionList = transactionDao.findByDetails(fromAccount, toAccount, transactionStatus, fromDate, toDate);

            LOG.debug("found " + transactionList.size() + " transactions for fromAccount=" + fromAccount);
            assertFalse(transactionList.isEmpty());

        }

        LOG.debug("end of testTransactions");
    }

}
