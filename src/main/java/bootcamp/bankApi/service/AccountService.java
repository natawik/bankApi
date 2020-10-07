package bootcamp.bankApi.service;

import bootcamp.bankApi.dao.AccountDao;
import bootcamp.bankApi.models.Account;

import java.util.List;

import static bootcamp.bankApi.service.CardService.generateNumber;

public class AccountService {
    private final AccountDao accountDao;

    public AccountService() {
        this.accountDao = new AccountDao();
    }

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public List<Account> findAllAccounts() {
        return accountDao.findAll();
    }

    public Account findAccountById(int accountId) {
        return accountDao.findById(accountId);
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
        accountDao.save(newAccount);
        return newAccount;
    }

    public Account deleteAccount(int accountId) {
        Account account = accountDao.findById(accountId);
        if (account != null) {
            accountDao.delete(account);
        }
        return account;
    }
}

