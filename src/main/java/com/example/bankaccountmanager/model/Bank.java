package com.example.bankaccountmanager.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Table
@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankID;
    @Column(nullable = false, unique = true, length = 200)
    private String name;
    @Column(nullable = false, unique = true, length = 400)
    private String address;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bank")
    private Set<BankAccount> bankAccounts;

    public Bank() {
    }
    public Bank(final Long bankID, final String name, final String address,
                final Set<BankAccount> bankAccounts) {
        setBankID(bankID);
        setName(name);
        setAddress(address);
        setBankAccounts(bankAccounts);
    }

    public Long getBankID() {
        return bankID;
    }

    public void setBankID(final Long bankID) {
        this.bankID = bankID;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public Set<BankAccount> getBankAccounts() {
        return new HashSet<>(bankAccounts);
    }

    public void setBankAccounts(final Set<BankAccount> bankAccounts) {
        this.bankAccounts = new HashSet<>(bankAccounts);
    }
}