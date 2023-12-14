package com.example.bankaccountmanager.repository;

import com.example.bankaccountmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterUserRepository extends JpaRepository<User, Long> {
    User findByUsername(final String username);
}