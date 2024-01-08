package com.example.bankaccountmanager.web;

import com.example.bankaccountmanager.model.Bank;
import com.example.bankaccountmanager.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/banks")
@Slf4j
public class BankController {
    private BankService bankService;
    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }
    @GetMapping
    public String getAllBanksForm(Model model) {
        Collection<Bank> banks =  bankService.getAllBanks();
        model.addAttribute("banks", banks);
        return "banks";
    }
}