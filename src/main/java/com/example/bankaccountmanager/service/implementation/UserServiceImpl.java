package com.example.bankaccountmanager.service.implementation;

import com.example.bankaccountmanager.dao.UserRepository;
import com.example.bankaccountmanager.exception.EntityNotFoundException;
import com.example.bankaccountmanager.exception.InvalidEntityException;
import com.example.bankaccountmanager.model.User;
import com.example.bankaccountmanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public Collection<User> getUsers() {

        return userRepo.findAll();
    }

    @Override
    public User getUserById(Long id) throws NoSuchElementException {

        return userRepo.findById(id).orElseThrow();
    }

    @Override
    public User getUserByUsername(String username) throws NoSuchElementException{

        return userRepo.findByUsername(username).orElseThrow();
    }

    @Override
    public User createUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User updateUser(User user) throws InvalidEntityException {

        if(userRepo.existsById(user.getUserID())) {
            return userRepo.save(user);
        } else {
            throw new InvalidEntityException("User cannot be updated.");
        }
    }

    @Override
    public User deleteUser(Long userID) throws EntityNotFoundException {
        User toBeDeleted = userRepo.findById(userID).orElse(null);
        if(toBeDeleted != null) {
            userRepo.delete(toBeDeleted);
            return toBeDeleted;
        } else {
            throw new EntityNotFoundException("User deleted yet.");
        }
    }

    @Override
    public Long getUsersCount() {
        return userRepo.count();
    }
}
