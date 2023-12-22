package com.example.bankaccountmanager.dao;

import com.example.bankaccountmanager.model.BankAccount;
import com.example.bankaccountmanager.model.Transaction;
import com.example.bankaccountmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query("select ba from BankAccount ba where ba.holder.userID = :userID")
    Collection<BankAccount> findAllBankAccountsByUserID(Long userID);

    @Query(value = "select t from Transaction t where " +
            "t.counterparty.holder.userID = :userID or " +
            "t.recipient.holder.userID = :userID"
            + " order by t.dateTime desc " +
            "limit 10")
    Collection<Transaction> findLast10TransactionsByUserID(Long userID);

    @Query(value = "select t from Transaction t where " +
            "t.counterparty.holder.userID = :userID or " +
            "t.recipient.holder.userID = :userID"
            + " order by t.dateTime desc " +
            "limit 20")
    Collection<Transaction> findLast20TransactionsByUserID(Long userID);

    @Query(value = "select t from Transaction t where " +
            "t.counterparty.holder.userID = :userID or " +
            "t.recipient.holder.userID = :userID"
            + " order by t.dateTime desc " +
            "limit 50")
    Collection<Transaction> findLast50TransactionsByUserID(Long userID);
}