package bootcamp.bankApi.service;

import bootcamp.bankApi.dao.AccountDao;
import bootcamp.bankApi.models.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @InjectMocks
    AccountService subj;

    @Mock
    AccountDao accountDao;

    @Test
    public void testFindAllAccountsFound() {
        List<Account> accounts = new ArrayList<>();
        Account acc1 = new Account();
        acc1.setCustomerId(1);
        acc1.setAccountNumber("123456789");
        acc1.setBalance(23.4F);
        Account acc2 = new Account();
        acc2.setCustomerId(2);
        acc2.setAccountNumber("67386789");
        acc2.setBalance(3423.4F);
        accounts.add(acc1);
        accounts.add(acc2);
        when(accountDao.findAll()).thenReturn(accounts);

        List<Account> accountsForCheck = subj.findAll();

        assertNotNull(accountsForCheck);
        assertFalse(accountsForCheck.isEmpty());
        assertEquals(2, accountsForCheck.size());
        assertEquals(accounts, accountsForCheck);

        verify(accountDao).findAll();
    }

    @Test
    public void testFindAllAccountsNotFound() {
        List<Account> accounts = new ArrayList<>();
        when(accountDao.findAll()).thenReturn(accounts);

        List<Account> accountsForCheck = subj.findAll();

        assertNotNull(accountsForCheck);
        assertTrue(accountsForCheck.isEmpty());
        assertEquals(accounts, accountsForCheck);

        verify(accountDao).findAll();
    }

    @Test
    public void testFindAccountsForCustomerFound() {
        List<Account> accounts = new ArrayList<>();
        Account acc1 = new Account();
        acc1.setCustomerId(1);
        acc1.setAccountNumber("123456789");
        acc1.setBalance(23.4F);
        accounts.add(acc1);
        when(accountDao.getListAccountForCustomer(1)).thenReturn(accounts);

        List<Account> accountsForCheck = subj.findAccountsForCustomer(1);

        assertNotNull(accountsForCheck);
        assertFalse(accountsForCheck.isEmpty());
        assertEquals(1, accountsForCheck.size());
        assertEquals(accounts, accountsForCheck);

        verify(accountDao).getListAccountForCustomer(1);
    }

    @Test
    public void testFindAccountsForCustomerNotFound() {
        List<Account> accounts = new ArrayList<>();
        when(accountDao.getListAccountForCustomer(1)).thenReturn(accounts);

        List<Account> accountsForCheck = subj.findAccountsForCustomer(1);

        assertNotNull(accountsForCheck);
        assertTrue(accountsForCheck.isEmpty());
        assertEquals(accounts, accountsForCheck);

        verify(accountDao).getListAccountForCustomer(1);
    }

    @Test
    public void testCheckBalanceChecked() {
        Account acc = new Account();
        acc.setCustomerId(1);
        acc.setAccountNumber("123456789");
        acc.setBalance(23.4F);
        when(accountDao.findById(1)).thenReturn(acc);

        float balance = subj.checkBalance(1);

        assertEquals(balance, acc.getBalance(), 0);

        verify(accountDao).findById(1);
    }

    @Test
    public void testCheckBalanceAccountDoesntExist() {
        when(accountDao.findById(1)).thenReturn(null);

        Float balance = subj.checkBalance(1);

        assertNull(balance);

        verify(accountDao).findById(1);
    }

    @Test
    public void testDepositFailedAccountDoesntExist() {
        when(accountDao.findById(1)).thenReturn(null);

        subj.deposit(1, 23F);
        Float balance = subj.checkBalance(1);

        assertNull(balance);

        verify(accountDao, times(2)).findById(1);
    }

    @Test
    public void testDepositFailedNegativeAmount() {
        Account acc = new Account();
        acc.setCustomerId(1);
        acc.setAccountNumber("123456789");
        acc.setBalance(23.4F);
        when(accountDao.findById(1)).thenReturn(acc);

        subj.deposit(1, -100.0F);
        Float balance = subj.checkBalance(1);

        assertEquals(balance, 23.4F, 0);

        verify(accountDao, times(2)).findById(1);
    }

    @Test
    public void testAddNewAccountNotAdded() {

        verifyZeroInteractions(accountDao);
    }

    @Test
    public void testDeleteAccountNotDeleted() {
        when(accountDao.findById(1)).thenReturn(null);

        verifyZeroInteractions(accountDao);
    }
}