package com.example.bankaccountmanager.dao;

import com.example.bankaccountmanager.model.BankAccount;
import com.example.bankaccountmanager.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findByIban(String iban);
    @Query(value = "select t from Transaction t where t.recipient.bankAccountID = :bankAccountID or t.counterparty.bankAccountID = :bankAccountID")
    Collection<Transaction> findAllByBankAccount(Long bankAccountID);

    @Query(value = "select ba from BankAccount ba where ba.holder.username like %:username%")
    Collection<BankAccount> findAllByUsername(@Param("username") String username);
}