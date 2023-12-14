package com.example.bankaccountmanager.web;

import com.example.bankaccountmanager.dto.RegisterUserDTO;
import com.example.bankaccountmanager.service.IRegisterUserService;
import com.example.bankaccountmanager.service.impl.RegisterUserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/")
public class RegisterUserRESTController {
    private final IRegisterUserService regService = new RegisterUserServiceImpl();
    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<RegisterUserDTO> signup(@Valid @RequestBody RegisterUserDTO registeredDTO) {
        RegisterUserDTO created = regService.create(registeredDTO);
        URI location = null;
        if(created != null) {
            location = ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                    .buildAndExpand(created.getUserID())
                    .toUri();
        } else {
            location = ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("")
                    .build()
                    .toUri();
        }

        return ResponseEntity.created(location).body(created);
    }
}