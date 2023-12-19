package com.example.bankaccountmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "BANKS", schema = "public")
@Entity
public class Bank {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankID;
    @NonNull
    @NotBlank
    @Length(min = 1, max = 200)
    @Pattern(regexp = "^[A-Z]+[a-z](1, 200)$")
    @Column(nullable = false, unique = true, length = 200)
    private String name;
    @NonNull
    @NotBlank
    @Length(min = 1, max = 400)
    @Pattern(regexp = "^[A-Z]+[a-z](1, 400)$")
    @Column(nullable = false, unique = true, length = 400)
    private String address;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bank")
    private Set<BankAccount> bankAccounts;
}