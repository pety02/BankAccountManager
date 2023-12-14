package com.example.bankaccountmanager.model;

import jakarta.persistence.*;

@Table
@Entity
public class Password {
    @Id
    private Long passwordID;
    @Column(nullable = false, length = 400)
    private String hash;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id", nullable=false)
    private User owner;

    public Password() {
    }
    public Password(final Password other) {
        this(other.getHash(), other.getOwner());
    }
    public Password(final String hash, final User owner) {
        setHash(hash);
        setOwner(owner);
    }

    public Long getPasswordID() {
        return passwordID;
    }

    public void setPasswordID(final Long passwordID) {
        this.passwordID = passwordID;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(final String hash) {
        this.hash = hash;
    }

    public User getOwner() {
        return new User(owner);
    }

    public void setOwner(final User owner) {
        this.owner = new User(owner);
    }
}