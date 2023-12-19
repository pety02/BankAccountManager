package com.example.bankaccountmanager.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "USERS", schema = "public")
@Entity
public class User {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    @NonNull
    @NotBlank
    @Length(min = 1, max = 100)
    @Pattern(regexp = "^[A-Z]+[a-z]{1,100}$")
    @Column(nullable = false, length = 100)
    private String name;
    @NonNull
    @NotBlank
    @Length(min = 1, max = 200)
    @Pattern(regexp = "^[A-Z]+[a-z]{1,200}$")
    @Column(length = 200, nullable = false)
    private String surname;
    @NonNull
    @NotBlank
    @Length(min = 1, max = 10)
    @Pattern(regexp = "^0[7|8]{1}[0-9]{8}$")
    @Column(length = 10, nullable = false)
    private String telephone;
    @NonNull
    private LocalDate birthDate;
    @NonNull
    @NotBlank
    @Length(min = 3, max = 300)
    @Email
    @Column(nullable = false, length = 300)
    private String email;
    @NonNull
    @NotBlank
    @Length(min = 8, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$")
    @Column(nullable = false, length = 150, unique = true)
    private String username;
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Password password;
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "holder")
    private Set<BankAccount> bankAccounts;
}