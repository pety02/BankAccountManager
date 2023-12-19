package com.example.bankaccountmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "PASSWORDS", schema = "public")
@Entity
public class Password {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passwordID;
    @NonNull
    @NotBlank
    @Length(min = 1, max = 400)
    @Column(nullable = false, length = 400)
    private String password;
    @OneToOne(fetch = FetchType.LAZY)
    private User owner;
}