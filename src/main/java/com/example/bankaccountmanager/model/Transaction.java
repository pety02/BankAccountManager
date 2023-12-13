package com.example.bankaccountmanager.model;

import jakarta.persistence.*;

import java.util.Date;

@Table
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionID;
    @Column
    private Date date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bankAccound_id", nullable = false)
    private BankAccount bankAccount;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Column(nullable = false)
    private Double money;
    @Column(nullable = false, length = 500)
    private String reason;

    public Transaction() {
    }
    public Transaction(final Transaction other) {
        this(other.getDate(), other.getBankAccount(),
                other.getType(), other.getMoney(),
                other.getReason());
    }
    public Transaction(final Date date, final BankAccount bankAccount,
                       final TransactionType type, final Double money,
                       final String reason) {
        setDate(date);
        setBankAccount(bankAccount);
        setType(type);
        setMoney(money);
        setReason(reason);
    }

    public Long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(final Long transactionID) {
        this.transactionID = transactionID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public BankAccount getBankAccount() {
        return new BankAccount(bankAccount);
    }

    public void setBankAccount(final BankAccount bankAccount) {
        this.bankAccount = new BankAccount(bankAccount);
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(final TransactionType type) {
        this.type = type;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(final Double money) {
        this.money = money;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }
}