package com.example.bankaccountmanager.service.implementation;

import com.example.bankaccountmanager.dao.BankAccountRepository;
import com.example.bankaccountmanager.dao.UserRepository;
import com.example.bankaccountmanager.exception.EntityNotFoundException;
import com.example.bankaccountmanager.exception.InvalidEntityException;
import com.example.bankaccountmanager.model.BankAccount;
import com.example.bankaccountmanager.model.Transaction;
import com.example.bankaccountmanager.service.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public BankAccount openAccount(BankAccount account) {
        return bankAccountRepo.save(account);
    }

    @Override
    public BankAccount findByIBAN(String iban) {
        return bankAccountRepo.findByIban(iban);
    }

    @Override
    public BankAccount updateAccount(BankAccount account) throws EntityNotFoundException {
        if(bankAccountRepo.existsById(account.getBankAccountID())) {
            return bankAccountRepo.saveAndFlush(account);
        } else {
            throw new EntityNotFoundException("Bank account does not exist.");
        }
    }

    @Override
    public BankAccount closeAccount(BankAccount account) throws EntityNotFoundException {
        BankAccount toBeDeleted = bankAccountRepo.findById(account.getBankAccountID())
                .orElse(null);
        if(toBeDeleted != null) {
            bankAccountRepo.delete(toBeDeleted);
            return toBeDeleted;
        } else {
            throw new EntityNotFoundException("Bank account deleted yet.");
        }
    }

    @Override
    public Collection<Transaction> findAllTransactionsByBankAccount(Long bankAccountID) {
        return bankAccountRepo.findAllByBankAccount(bankAccountID);
    }

    @Override
    public Double getBankAccountBalance(Long accountID) throws InvalidEntityException {
        BankAccount currentAccount = bankAccountRepo.findById(accountID).orElse(null);
        if(currentAccount != null) {
            return currentAccount.getBalance();
        } else {
            throw new InvalidEntityException("Bank account has no balance.");
        }
    }

    @Override
    public BankAccount findById(Long accountID) throws NoSuchElementException {
        return bankAccountRepo.findById(accountID).orElseThrow();
    }

    @Override
    public Collection<BankAccount> findByUser(Long userID) {
        return userRepo.findAllBankAccountsByUserID(userID);
    }

    @Override
    public Collection<BankAccount> findAllByUsername(String username) {
        return bankAccountRepo.findAllByUsername(username);
    }

    @Override
    public Double calculateAllMoney(String username) {
        Collection<BankAccount> bankAccounts = bankAccountRepo.findAllByUsername(username);
        double money = 0.0;
        for (BankAccount bankAccount : bankAccounts) {
            money += bankAccount.getBalance();
        }

        return money;
    }
}