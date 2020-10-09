package bootcamp.bankApi.dao;

import bootcamp.bankApi.models.Account;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AccountDaoTest {

    @Test
    public void saveAccount() {
        AccountDao accountDao = new AccountDao();
        Account account = new Account("9986544", 12345.67f, 1);
        accountDao.save(account);
        Account newAccount = accountDao.findById(account.getId());

        assertTrue(account.getBalance() == newAccount.getBalance());
    }

    @Test
    public void updateAccount() {
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.findById(2);
        account.setBalance(50000);
        accountDao.update(account);
        Account newAccount = accountDao.findById(account.getId());

        assertTrue(account.getBalance() == newAccount.getBalance());
    }

    @Test
    public void deleteAccount() {
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.findById(3);
        accountDao.delete(account);

        assertNull(accountDao.findById(account.getId()));
    }

    @Test
    public void findByIdNotNull() {
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.findById(1);

        assertNotNull(account);
    }

    @Test
    public void findAllNotNull() {
        AccountDao accountDao = new AccountDao();
        List<Account> accounts = accountDao.findAll();

        assertNotNull(accounts);
    }

    @Test
    public void findAll() {
        AccountDao accountDao = new AccountDao();
        List<Account> accounts = accountDao.findAll();

        assertTrue(accounts.size() > 0);
    }

    @Test
    public void getListAccountForCustomer() {
        AccountDao accountDao = new AccountDao();
        List<Account> accounts = accountDao.getListAccountForCustomer(1);

        assertTrue(accounts.size() > 0);
    }
}