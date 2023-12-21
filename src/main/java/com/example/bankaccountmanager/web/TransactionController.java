package com.example.bankaccountmanager.web;

import com.example.bankaccountmanager.model.BankAccount;
import com.example.bankaccountmanager.model.Transaction;
import com.example.bankaccountmanager.model.User;
import com.example.bankaccountmanager.service.BankAccountService;
import com.example.bankaccountmanager.service.TransactionService;
import com.example.utils.IBANsGenerator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

@Controller
@RequestMapping("/transactions")
@Slf4j
public class TransactionController {
    private BankAccountService bankAccountService;
    private TransactionService transactionService;

    public TransactionController(BankAccountService bankAccountService, TransactionService transactionService) {
        this.bankAccountService = bankAccountService;
        this.transactionService = transactionService;
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

    @GetMapping("/last-ten/{bankAccountId}")
    public String getLastTenTransactionsByBankAccountID(@PathVariable("bankAccountId") Long id, Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if(user != null) {
            Collection<Transaction> transactions = transactionService.findLast10TransactionsByUser(id);
            model.addAttribute("bankAccountTransactions", transactions);
            return "transactions";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/last-twenty/{bankAccountId}")
    public String getLastTwentyTransactionsByBankAccountID(@PathVariable("bankAccountId") Long id, Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if(user != null) {
            Collection<Transaction> transactions = transactionService.findLast20TransactionsByUser(id);
            model.addAttribute("bankAccountTransactions", transactions);
            return "transactions";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/last-fifty/{bankAccountId}")
    public String getLastFiftyTransactionsByBankAccountID(@PathVariable("bankAccountId") Long id, Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if(user != null) {
            Collection<Transaction> transactions = transactionService.findLast50TransactionsByUser(id);
            model.addAttribute("bankAccountTransactions", transactions);
            return "transactions";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping
    public String executeTransaction(@Valid @ModelAttribute("newTr") Transaction transaction,
                                  final BindingResult binding,
                                  RedirectAttributes redirectAttributes,
                                  HttpSession httpSession) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if(loggedUser != null) {

        }
        if (binding.hasErrors()) {
            log.error("Error opening bankAccount: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("transaction", transaction);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "bankAccount",
                    binding);
            return "bank-accounts";
        }

        transactionService.makeTransaction(transaction);
        return "redirect:/bank-accounts";
    }
}