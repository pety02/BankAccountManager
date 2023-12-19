package com.example.bankaccountmanager.service;

import com.example.bankaccountmanager.exception.EntityNotFoundException;
import com.example.bankaccountmanager.exception.InvalidEntityException;
import com.example.bankaccountmanager.model.Bank;

import java.util.Collection;
import java.util.NoSuchElementException;

public interface BankService {
    Bank createBank(Bank bank);
    Bank updateBank(Bank bank) throws InvalidEntityException;

    Bank deleteBank(Bank bank) throws EntityNotFoundException;
    Bank getBankById(Long bankID) throws NoSuchElementException;
    Collection<Bank> getAllBanks();
}