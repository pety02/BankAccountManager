package com.example.bankaccountmanager.service;

import com.example.bankaccountmanager.exception.EntityNotFoundException;
import com.example.bankaccountmanager.exception.InvalidEntityException;
import com.example.bankaccountmanager.model.User;

public interface AuthService {
    User register(User user) throws InvalidEntityException;
    User login(String username, String password) throws EntityNotFoundException;
}