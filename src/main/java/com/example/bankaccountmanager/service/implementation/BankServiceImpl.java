package com.example.bankaccountmanager.service.implementation;

import com.example.bankaccountmanager.dao.BankRepository;
import com.example.bankaccountmanager.exception.EntityNotFoundException;
import com.example.bankaccountmanager.exception.InvalidEntityException;
import com.example.bankaccountmanager.model.Bank;
import com.example.bankaccountmanager.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class BankServiceImpl implements BankService {
    @Autowired
    private BankRepository bankRepo;

    @Override
    public Bank createBank(Bank bank) {
        return bankRepo.saveAndFlush(bank);
    }

    @Override
    public Bank updateBank(Bank bank) throws InvalidEntityException {
        if(bankRepo.existsById(bank.getBankID())) {
            return bankRepo.saveAndFlush(bank);
        } else {
            throw new InvalidEntityException("Bank cannot be updated.");
        }
    }

    @Override
    public Bank deleteBank(Bank bank) throws EntityNotFoundException {
        Bank toBeDeleted = bankRepo.findById(bank.getBankID()).orElse(null);
        if(toBeDeleted != null) {
            bankRepo.delete(toBeDeleted);
            return toBeDeleted;
        } else {
            throw new EntityNotFoundException("Bank deleted yet.");
        }
    }

    @Override
    public Bank getBankById(Long bankID) throws NoSuchElementException {
        return bankRepo.findById(bankID).orElseThrow();
    }

    @Override
    public Collection<Bank> getAllBanks() {
        return bankRepo.findAll();
    }
}
