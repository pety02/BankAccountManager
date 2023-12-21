package com.example.bankaccountmanager.web;

import com.example.bankaccountmanager.model.Transaction;
import com.example.bankaccountmanager.model.User;
import com.example.bankaccountmanager.service.BankAccountService;
import com.example.bankaccountmanager.service.TransactionService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
@RequestMapping("/transactions")
@Slf4j
public class TransactionController {
    private BankAccountService bankAccountService;

    public TransactionController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/{id}")
    public String getBankAccountTransactions(@PathVariable("id") Long id, Model model, HttpSession httpSession) {
        System.out.println("int TransactionController");
        User user = (User) httpSession.getAttribute("user");
        System.out.println(id);
        if(user != null) {
            Collection<Transaction> transactions = bankAccountService.findAllTransactionsByBankAccount(id);
            model.addAttribute("bankAccountTransactions", transactions);
            return "transactions";
        } else {
            return "redirect:/";
        }
    }
}