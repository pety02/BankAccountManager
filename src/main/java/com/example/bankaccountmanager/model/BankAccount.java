package com.example.bankaccountmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "BANK_ACCOUNTS", schema = "public")
@Entity
public class BankAccount {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankAccountID;
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;
    @NonNull
    //@NotBlank
    @Length(min = 0, max = 200)
    @Pattern(regexp = "^[A-Z]{3}[0-9]{10}$")
    @Column(nullable = false, unique = true, length = 200)
    private String iban;
    @NonNull
    @Column(nullable = false)
    private Double balance;
    @NonNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate discoveryDate;
    @NonNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate expiryDate;
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "holder_id", nullable = false)
    private User holder;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "counterparty", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transaction> counterpartyTransactions;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transaction> recipientTransactions;
}