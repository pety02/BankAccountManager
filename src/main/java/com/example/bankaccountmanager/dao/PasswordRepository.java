package com.example.bankaccountmanager.dao;

import com.example.bankaccountmanager.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
    @Query("select p from Password p where p.owner.userID = :userID")
    Password findByUserId(Long userID);
}
