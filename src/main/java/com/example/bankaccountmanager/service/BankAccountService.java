package com.example.bankaccountmanager.service;

import com.example.bankaccountmanager.exception.EntityNotFoundException;
import com.example.bankaccountmanager.exception.InvalidEntityException;
import com.example.bankaccountmanager.model.BankAccount;
import com.example.bankaccountmanager.model.Transaction;

import java.util.Collection;
import java.util.NoSuchElementException;

public interface BankAccountService {
    BankAccount openAccount(BankAccount account);
    BankAccount findByIBAN(String iban);
    BankAccount updateAccount(BankAccount account) throws EntityNotFoundException;
    BankAccount closeAccount(BankAccount account) throws EntityNotFoundException;

    Collection<Transaction> findAllTransactionsByBankAccount(Long bankAccountID);
    Double getBankAccountBalance(Long accountID) throws InvalidEntityException;
    BankAccount findById(Long accountID) throws NoSuchElementException;
    Collection<BankAccount> findByUser(Long userID);
    Collection<BankAccount> findAllByUsername(String username);
    Double calculateAllMoney(String username);
}