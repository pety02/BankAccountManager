package com.example.bankaccountmanager.service;

import com.example.bankaccountmanager.exception.EntityNotFoundException;
import com.example.bankaccountmanager.exception.InvalidEntityException;
import com.example.bankaccountmanager.model.User;

import java.util.Collection;
import java.util.NoSuchElementException;

public interface UserService {
    Collection<User> getUsers();
    User getUserById(Long id) throws NoSuchElementException;
    User getUserByUsername(String username) throws NoSuchElementException;
    User createUser(User user);
    User updateUser(User user) throws InvalidEntityException;
    User deleteUser(Long userID) throws EntityNotFoundException;
    Long getUsersCount();
}