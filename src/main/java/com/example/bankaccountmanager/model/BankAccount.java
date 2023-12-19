package com.example.bankaccountmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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
    @NotBlank
    @Size(min = 1)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;
    @NonNull
    @NotBlank
    @Length(min = 1, max = 200)
    @Pattern(regexp = "^[A-Z]+[a-z](1, 200)$")
    @Column(nullable = false, unique = true, length = 200)
    private String iban;
    @NonNull
    @NotBlank
    @Size(min = 0)
    @Column(nullable = false)
    private Double balance;
    @NonNull
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date discoveryDate;
    @NonNull
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date expiryDate;
    @NonNull
    @NotBlank
    @Size(min = 1)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "holder_id", nullable = false)
    private User holder;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "counterparty")
    private Set<Transaction> counterpartyTransactions;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipient")
    private Set<Transaction> recipientTransactions;
}