package com.example.bankaccountmanager.service;

import com.example.bankaccountmanager.model.Transaction;

import java.util.Collection;
import java.util.NoSuchElementException;

public interface TransactionService {
    Transaction makeTransaction(Transaction transaction);
    Transaction findById(Long transactionID) throws NoSuchElementException;
    Collection<Transaction> findLast10TransactionsByUser(Long userID);
    Collection<Transaction> findLast20TransactionsByUser(Long userID);
    Collection<Transaction> findLast50TransactionsByUser(Long userID);
}