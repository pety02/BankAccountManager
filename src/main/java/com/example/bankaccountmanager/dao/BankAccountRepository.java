package com.example.bankaccountmanager.dao;

import com.example.bankaccountmanager.model.BankAccount;
import com.example.bankaccountmanager.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    @Query("select t from Transaction t where t.recipient.bankAccountID = :bankAccountID or t.counterparty.bankAccountID = :bankAccountID")
    Collection<Transaction> findAllByBankAccount(Long bankAccountID);
}