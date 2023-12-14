package com.example.bankaccountmanager.service;

import com.example.bankaccountmanager.dto.RegisterUserDTO;

public interface IRegisterUserService {
    RegisterUserDTO create(final RegisterUserDTO model);
    RegisterUserDTO findByUsername(final RegisterUserDTO model);
    RegisterUserDTO findByUsernameAndPassword(final String username, final String password);
}