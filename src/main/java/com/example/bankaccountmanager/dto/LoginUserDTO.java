package com.example.bankaccountmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LoginUserDTO {
    @EqualsAndHashCode.Include
    @Positive
    private Long userID;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{8,150}$", message = "Username should contains lowercase, uppercase or digits.")
    @Length(min = 8, max = 150, message = "Username length should be between 8 and 150 chars.")
    private String username;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._!@#*]{8,150}$", message = "Password should contains lowercase, uppercase and digits. Also ., _, !, @, # and * are allowed.")
    @Length(min = 8, max = 150, message = "Password length should be between 8 and 150 chars.")
    private String password;
}