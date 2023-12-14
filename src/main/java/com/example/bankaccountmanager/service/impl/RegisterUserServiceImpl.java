package com.example.bankaccountmanager.service.impl;

import com.example.bankaccountmanager.dao.impl.RegisterUserDAOImpl;
import com.example.bankaccountmanager.dto.RegisterUserDTO;
import com.example.bankaccountmanager.model.User;
import com.example.bankaccountmanager.repository.RegisterUserRepository;
import com.example.bankaccountmanager.service.IRegisterUserService;
import org.hibernate.mapping.Fetchable;
import org.springframework.data.aot.RegisteredBeanAotContribution;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RegisterUserServiceImpl implements IRegisterUserService {
    private RegisterUserRepository userRepository;
    private final RegisterUserDAOImpl userDAOImpl = new RegisterUserDAOImpl();
    @Override
    public RegisterUserDTO create(RegisterUserDTO model) {
        User created = userRepository.save(userDAOImpl.fromDtoToEntity(model));
        return userDAOImpl.fromEntityToDTO(created);
    }

    @Override
    public RegisterUserDTO findByUsername(RegisterUserDTO model) {
        User created = new User();
        if(userRepository.findByUsername(model.getUsername()) == null) {
            created = userDAOImpl.fromDtoToEntity(model);
            userRepository.save(created);
        }

        return userDAOImpl.fromEntityToDTO(created);
    }

    @Override
    public RegisterUserDTO findByUsernameAndPassword(String username, String password) {
        return null;
    }
}