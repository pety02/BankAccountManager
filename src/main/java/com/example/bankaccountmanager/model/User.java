package com.example.bankaccountmanager.model;


import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 200, nullable = false)
    private String surname;
    @Column(length = 50, nullable = false)
    private String telephone;
    @Column(nullable = false)
    private Date birthDate;
    @Column(nullable = false, length = 300)
    private String email;
    @Column(nullable = false, length = 150, unique = true)
    private String username;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "owner")
    private Set<Password> passwords;
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "holder")
    private Set<BankAccount> bankAccounts;

    public User() {
    }
    public User(final User other) {
        this(other.getName(), other.getSurname(), other.getTelephone(),
                other.getBirthDate(), other.getEmail(), other.getUsername(),
                other.getPasswords(), other.getBankAccounts());
    }
    public User(final String name, final String surname, final String telephone,
                final Date birthDate, final String email, final String username,
                final Set<Password> passwords, final Set<BankAccount> bankAccounts) {
        setName(name);
        setSurname(surname);
        setTelephone(telephone);
        setBirthDate(birthDate);
        setEmail(email);
        setUsername(username);
        setPasswords(passwords);
        setBankAccounts(bankAccounts);
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(final Long userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Set<Password> getPasswords() {
        return new HashSet<>(passwords);
    }

    public void setPasswords(final Set<Password> passwords) {
        this.passwords = new HashSet<>(passwords);
    }

    public Set<BankAccount> getBankAccounts() {
        return new HashSet<>(bankAccounts);
    }

    public void setBankAccounts(final Set<BankAccount> bankAccounts) {
        this.bankAccounts = new HashSet<>(bankAccounts);
    }
}