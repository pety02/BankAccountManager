package com.example.bankaccountmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "TRANSACTIONS", schema = "public")
@Entity
public class Transaction {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionID;
    @NonNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate dateTime;
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "counterparty_id", nullable = false)
    private BankAccount counterparty;
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id", nullable = false)
    private BankAccount recipient;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;
    @NonNull
    @Column(nullable = false)
    private Double money;
    @NonNull
    @NotBlank
    @Length(min = 1, max = 500)
    @Column(nullable = false, length = 500)
    private String reason;
}