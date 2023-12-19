package com.example.bankaccountmanager.service.implementation;

import com.example.bankaccountmanager.dao.PasswordRepository;
import com.example.bankaccountmanager.exception.EntityNotFoundException;
import com.example.bankaccountmanager.exception.InvalidEntityException;
import com.example.bankaccountmanager.model.Password;
import com.example.bankaccountmanager.security.PasswordHasher;
import com.example.bankaccountmanager.service.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private PasswordRepository passwordRepo;

    @Override
    public Password createPassword(Password password) {
        try {
            String hash = PasswordHasher.hash(password.getPassword());
            Password hashedPassword = new Password(password.getPasswordID(),
                    hash, password.getOwner());
            passwordRepo.save(hashedPassword);
            return hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    @Override
    public Password updatePassword(Password password) throws InvalidEntityException {
        try {
            Password currentPassword = passwordRepo.findById(password.getPasswordID()).orElse(null);
            if(currentPassword != null) {
                String hash = PasswordHasher.hash(password.getPassword());
                currentPassword.setPassword(hash);
                passwordRepo.saveAndFlush(currentPassword);
                return currentPassword;
            } else {
                throw new InvalidEntityException("Password cannot be updated.");
            }
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    @Override
    public Password deletePassword(Password password) throws EntityNotFoundException {
        if(passwordRepo.exists(Example.of(password))) {
            passwordRepo.delete(password);
            return password;
        } else {
            throw new EntityNotFoundException("Password deleted yet.");
        }
    }

    @Override
    public Password findById(Long passwordID) throws NoSuchElementException {
        return passwordRepo.findById(passwordID).orElseThrow();
    }

    @Override
    public Password findByUserId(Long userID) throws NoSuchElementException {
        return passwordRepo.findByUserId(userID);
    }
}
