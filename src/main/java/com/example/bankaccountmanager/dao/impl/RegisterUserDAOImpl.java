package com.example.bankaccountmanager.dao.impl;

import com.example.bankaccountmanager.dao.IDAO;
import com.example.bankaccountmanager.dto.RegisterUserDTO;
import com.example.bankaccountmanager.model.Password;
import com.example.bankaccountmanager.model.User;
import com.example.bankaccountmanager.security.PasswordHasher;

import java.security.NoSuchAlgorithmException;

public class RegisterUserDAOImpl implements IDAO<User, RegisterUserDTO> {
    @Override
    public User fromDtoToEntity(RegisterUserDTO registerUserDTO) {
        User entity = new User();
        entity.setName(registerUserDTO.getName());
        entity.setSurname(registerUserDTO.getSurname());
        entity.setBirthDate(registerUserDTO.getBirthDate());
        entity.setTelephone(registerUserDTO.getTelephone());
        entity.setEmail(registerUserDTO.getEmail());
        entity.setUsername(registerUserDTO.getUsername());
        try {
            Password password = new Password(PasswordHasher.hash(registerUserDTO.getPassword()), entity);
            entity.setPassword(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    @Override
    public RegisterUserDTO fromEntityToDTO(User user) {
        RegisterUserDTO dto = new RegisterUserDTO();
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setBirthDate(user.getBirthDate());
        dto.setTelephone(user.getTelephone());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword().getHash());
        dto.setReEnterPassword(user.getPassword().getHash());

        return dto;
    }
}