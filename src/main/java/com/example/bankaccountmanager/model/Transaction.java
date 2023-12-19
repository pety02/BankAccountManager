package com.example.bankaccountmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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
    @NotBlank
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateTime;
    @NonNull
    @NotBlank
    @Size(min = 1)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "counterparty_id", nullable = false)
    private BankAccount counterparty;
    @NonNull
    @NotBlank
    @Size(min = 1)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id", nullable = false)
    private BankAccount recipient;
    @NonNull
    @NotBlank
    @Length(min = 1, max = 50)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TransactionType type;
    @NonNull
    @NotBlank
    @Size(min = 0)
    @Column(nullable = false)
    private Double money;
    @NonNull
    @NotBlank
    @Length(min = 1, max = 500)
    @Column(nullable = false, length = 500)
    private String reason;
}