package com.example.bankaccountmanager.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RegisterUserDTO {
    @EqualsAndHashCode.Include
    @Positive
    private Long userID;
    @NotBlank
    @Pattern(regexp = "^[A-Z]+[a-z]{1,100}$", message = "Name should starts with capital letter and should contains only latin letters.")
    @Length(min = 2, max = 100, message = "Name length should be between 2 and 100 chars.")
    private String name;
    @NotBlank
    @Pattern(regexp = "^[A-Z]+[a-z]{1,100}$", message = "Surname should starts with capital letter and should contains only latin letters.")
    @Length(min = 2, max = 200, message = "Surname length should be between 1 and 200 chars.")
    private String surname;
    @DateTimeFormat
    private Date birthDate;
    @NotBlank
    @Pattern(regexp = "^(((\\+|00)359[- ]?)|(0))(8[- ]?[789]([- ]?\\d){7})$", message = "Telephone number should be valid bulgarian telephone number.")
    @Length(min = 9, max = 50, message = "Telephone number length should be between 9 and 50 chars.")
    private String telephone;
    @NotBlank
    @Email(regexp = "Email should be valid.")
    @Length(min = 10, max = 300, message = "Email length should be between 10 and 300 chars.")
    private String email;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{8,150}$", message = "Username should contains lowercase, uppercase or digits.")
    @Length(min = 8, max = 150, message = "Username length should be between 8 and 150 chars.")
    private String username;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._!@#*]{8,150}$", message = "Password should contains lowercase, uppercase and digits. Also ., _, !, @, # and * are allowed.")
    @Length(min = 8, max = 150, message = "Password length should be between 8 and 150 chars.")
    private String password;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._!@#*]{8,150}$", message = "Re-entered password should contains lowercase, uppercase and digits. Also ., _, !, @, # and * are allowed.")
    @Length(min = 8, max = 150, message = "Re-Entered password length should be between 8 and 150 chars.")
    private String reEnterPassword;
}