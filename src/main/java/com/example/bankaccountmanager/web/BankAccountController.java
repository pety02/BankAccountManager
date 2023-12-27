package com.example.bankaccountmanager.web;

import com.example.bankaccountmanager.model.Bank;
import com.example.bankaccountmanager.model.BankAccount;
import com.example.bankaccountmanager.model.User;
import com.example.bankaccountmanager.service.BankAccountService;
import com.example.bankaccountmanager.service.BankService;
import com.example.utils.IBANsGenerator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Collection;

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

@Controller
@RequestMapping("/bank-accounts")
@Slf4j
public class BankAccountController {
    private BankAccountService bankAccountService;
    private BankService bankService;
    @Autowired
    public BankAccountController(BankAccountService bankAccountService, BankService bankService) {
        this.bankAccountService = bankAccountService;
        this.bankService = bankService;
    }

    @GetMapping
    public String getAllUserBankAccountsForm(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if(user != null) {
            Collection<BankAccount> accounts =  bankAccountService.findByUser(user.getUserID());
            model.addAttribute("bankAccounts", accounts);
            model.addAttribute("totalMoney", bankAccountService.calculateAllMoney(user.getUsername()));
            return "bank-accounts";
        } else {
            return "redirect:/auth-login";
        }
    }

    @GetMapping("/{id}")
    public String getUserBankAccountForm(Long bankAccountID,
                                         HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if(user != null) {
            Collection<BankAccount> accounts = bankAccountService.findByUser(user.getUserID());
            for(BankAccount ba : accounts) {
                if(ba.getBankAccountID().equals(bankAccountID)) {
                    httpSession.setAttribute("bankAccount", ba);
                    break;
                }
            }
            return "bank-accounts";
        } else {
            return "redirect:/auth-login";
        }
    }

    @PostMapping
    public String openBankAccount(@Valid @ModelAttribute("newBA") BankAccount bankAccount,
                                  final BindingResult binding,
                                  RedirectAttributes redirectAttributes,
                                  HttpSession httpSession) {
        if (binding.hasErrors()) {
            log.error("Error opening bankAccount: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("bankAccount", bankAccount);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "bankAccount",
                    binding);
            return "bank-accounts";
        }

        User loggedUser = (User) httpSession.getAttribute("user");
        if(loggedUser != null) {
            bankAccount.setHolder(loggedUser);
            bankAccount.setIban(IBANsGenerator.generateIBAN());
            LocalDate now = LocalDate.now();
            bankAccount.setDiscoveryDate(now);
            int after4Years = now.getYear() + 4;
            bankAccount.setExpiryDate(LocalDate.of(after4Years, now.getMonth(), now.getDayOfMonth()));
            bankAccountService.openAccount(bankAccount);
            return "redirect:/bank-accounts";
        }

        return "redirect:/auth/login";
    }

    @RequestMapping("/close/{id}")
    public String closeBankAccount(@PathVariable("id") Long id, Model model, HttpSession httpSession) {
        BankAccount toBeDeletedBA = bankAccountService.findById(id);
        if(toBeDeletedBA != null) {
            bankAccountService.closeAccount(toBeDeletedBA);
            return "redirect:/bank-accounts";
        }

        return "bank-accounts";
    }
}