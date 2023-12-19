package com.example.bankaccountmanager.service.implementation;

import com.example.bankaccountmanager.exception.EntityNotFoundException;
import com.example.bankaccountmanager.exception.InvalidEntityException;
import com.example.bankaccountmanager.model.Password;
import com.example.bankaccountmanager.model.User;
import com.example.bankaccountmanager.model.UserRole;
import com.example.bankaccountmanager.security.PasswordHasher;
import com.example.bankaccountmanager.service.AuthService;
import com.example.bankaccountmanager.service.PasswordService;
import com.example.bankaccountmanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordService passwordService;

    @Override
    public User register(User user) throws InvalidEntityException{
        if(user.getRole() == UserRole.ADMIN) {
            throw new InvalidEntityException("Admins cannot self-register.");
        }

        Password pass = user.getPassword();
        pass.setOwner(user);
        user.setPassword(null);
        userService.createUser(user);
        passwordService.createPassword(pass);

        return user;
    }

    @Override
    public User login(String username, String password) throws EntityNotFoundException {
        User user = userService.getUserByUsername(username);

        try {
            Password p = passwordService.findByUserId(user.getUserID());
            if(p.getPassword().equals(PasswordHasher.hash(password))) {
                System.out.println("Successfully logged in! Hello, " + user.getSurname() + "!");
                return user;
            } else {
                throw new EntityNotFoundException("Not registered user.");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
