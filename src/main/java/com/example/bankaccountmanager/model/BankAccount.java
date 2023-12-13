package com.example.bankaccountmanager.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Table
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankAccountID;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;
    @Column(length = 200, nullable = false, unique = true)
    private String iban;
    @Column(nullable = false)
    private Double availability;
    @Column(nullable = false)
    private Date discoveryDate;
    @Column(nullable = false)
    private Date expiryDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "holder_id", nullable = false)
    private User holder;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bankAccount")
    private Set<Transaction> transactions;

    public BankAccount() {
    }
    public BankAccount(final BankAccount other) {
        this(other.getBank(), other.getIban(), other.getAvailability(),
                other.getDiscoveryDate(), other.getExpiryDate(),
                other.getHolder(), other.getTransactions());
    }
    public BankAccount(final Bank bank, final String iban,
                       final Double availability, final Date discoveryDate,
                       final Date expiryDate, final User holder,
                       final Set<Transaction> transactions) {
        setBank(bank);
        setIban(iban);
        setAvailability(availability);
        setDiscoveryDate(discoveryDate);
        setExpiryDate(expiryDate);
        setHolder(holder);
        setTransactions(transactions);
    }

    public Long getBankAccountID() {
        return bankAccountID;
    }

    public void setBankAccountID(final Long bankAccountID) {
        this.bankAccountID = bankAccountID;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(final Bank bank) {
        this.bank = bank;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(final String iban) {
        this.iban = iban;
    }

    public Double getAvailability() {
        return availability;
    }

    public void setAvailability(final Double availability) {
        this.availability = availability;
    }

    public Date getDiscoveryDate() {
        return discoveryDate;
    }

    public void setDiscoveryDate(final Date discoveryDate) {
        this.discoveryDate = discoveryDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(final Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getHolder() {
        return holder;
    }

    public void setHolder(final User holder) {
        this.holder = holder;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(final Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}