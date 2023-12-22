package com.example.bankaccountmanager.service.implementation;

import com.example.bankaccountmanager.dao.TransactionRepository;
import com.example.bankaccountmanager.dao.UserRepository;
import com.example.bankaccountmanager.model.Transaction;
import com.example.bankaccountmanager.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepo;
    @Autowired
    private UserRepository userRepo;

    @Override
    public Transaction makeTransaction(Transaction transaction) {
        return transactionRepo.save(transaction);
    }

    @Override
    public Transaction findById(Long transactionID) throws NoSuchElementException {
        return transactionRepo.findById(transactionID).orElseThrow();
    }

    @Override
    public Collection<Transaction> findLast10TransactionsByUser(Long userID) {
        return userRepo.findLast10TransactionsByUserID(userID);
    }

    @Override
    public Collection<Transaction> findLast20TransactionsByUser(Long userID) {
        return userRepo.findLast20TransactionsByUserID(userID);
    }

    @Override
    public Collection<Transaction> findLast50TransactionsByUser(Long userID) {
        return userRepo.findLast50TransactionsByUserID(userID);
    }
}
