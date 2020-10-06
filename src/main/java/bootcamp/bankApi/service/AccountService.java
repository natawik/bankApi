package bootcamp.bankApi.service;

import bootcamp.bankApi.dao.AccountDao;
import bootcamp.bankApi.models.Account;
import bootcamp.bankApi.models.Customer;

import java.util.List;

import static bootcamp.bankApi.service.CardService.generateNumber;

public class AccountService {
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public List<Account> findAllAccounts() {
        return accountDao.findAll();
    }

    public List<Account> findAccountsForCustomer(int customerId) {
        return accountDao.getListAccountForCustomer(customerId);
    }

    public float checkBalance(int accountId) {
        Account acc = accountDao.findById(accountId);
        return acc.getBalance();
    }

    public void deposit(int accountId, float amount) {
        Account acc = accountDao.findById(accountId);
        if (amount > 0) {
            acc.setBalance(acc.getBalance() + amount);
            accountDao.update(acc);
        }
    }

    public void addNewAccount(int customerId) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customerId);
        newAccount.setAccountNumber(generateNumber(20));
        newAccount.setBalance(0F);
        accountDao.save(newAccount);
    }

    public void deleteAccount(int accountId) {
        Account acc = accountDao.findById(accountId);
        accountDao.delete(acc);
    }
}

