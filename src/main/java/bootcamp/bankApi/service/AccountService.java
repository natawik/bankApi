package bootcamp.bankApi.service;

import bootcamp.bankApi.dao.AccountDao;
import bootcamp.bankApi.models.Account;

import java.util.List;

import lombok.*;

import static bootcamp.bankApi.service.CardService.generateNumber;

public class AccountService implements Service<Account> {
    private final AccountDao accountDao;

    public AccountService() {
        this.accountDao = new AccountDao();
    }

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account findById(int accountId) {
        return accountDao.findById(accountId);
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public void save(Account account) {
        accountDao.save(account);
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    @Override
    public void delete(Account account) {
        accountDao.delete(account);
    }


    public List<Account> findAccountsForCustomer(int customerId) {
        return accountDao.getListAccountForCustomer(customerId);
    }

    public Float checkBalance(int accountId) {
        Account acc = accountDao.findById(accountId);
        if (acc != null) {
            return acc.getBalance();
        } else {
            return null;
        }
    }

    public void deposit(int accountId, float amount) {
        Account acc = accountDao.findById(accountId);
        if (acc != null) {
            if (amount > 0) {
                acc.setBalance(acc.getBalance() + amount);

                accountDao.update(acc);
            }
        }
    }

    public Account addNewAccount(int customerId) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customerId);
        newAccount.setAccountNumber(generateNumber(20));
        newAccount.setBalance(0F);
        save(newAccount);
        return newAccount;
    }

    public Account deleteAccount(int accountId) {
        Account account = accountDao.findById(accountId);
        if (account != null) {
            delete(account);
        }
        return account;
    }
}

