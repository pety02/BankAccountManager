package com.example.bankaccountmanager.web;

import com.example.bankaccountmanager.model.BankAccount;
import com.example.bankaccountmanager.model.User;
import com.example.bankaccountmanager.service.BankAccountService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/bank-accounts")
@Slf4j
public class BankAccountController {
    private BankAccountService bankAccountService;
    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public String getAllUserBankAccountsForm(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if(user != null) {
            Collection<BankAccount> accounts =  bankAccountService.findByUser(user.getUserID());
            model.addAttribute("bankAccounts", accounts);
            return "bank-accounts";
        } else {
            return "redirect:/auth-login";
        }
    }

    @GetMapping("/{id}")
    public String getUserBankAccountForm(Model model, Long bankAccountID, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if(user != null) {
            Collection<BankAccount> accounts = bankAccountService.findByUser(user.getUserID());
            for(BankAccount ba : accounts) {
                if(ba.getBankAccountID().equals(bankAccountID)) {
                  break;
                } else {
                    // TODO: throw exception
                }
            }
            model.addAttribute("bank-accounts", bankAccountService.findById(bankAccountID));
            return "bank-accounts";
        } else {
            return "redirect:/auth-login";
        }
    }

}