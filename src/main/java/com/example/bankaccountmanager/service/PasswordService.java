package com.example.bankaccountmanager.service;

import com.example.bankaccountmanager.exception.EntityNotFoundException;
import com.example.bankaccountmanager.exception.InvalidEntityException;
import com.example.bankaccountmanager.model.Password;

import java.util.NoSuchElementException;

public interface PasswordService {
    Password createPassword(Password password);
    Password updatePassword(Password password) throws InvalidEntityException;
    Password deletePassword(Password password) throws EntityNotFoundException;
    Password findById(Long passwordID) throws NoSuchElementException;

    Password findByUserId(Long userID) throws NoSuchElementException;
}