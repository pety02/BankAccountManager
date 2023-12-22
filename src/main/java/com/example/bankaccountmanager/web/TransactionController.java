package com.example.bankaccountmanager.web;

import com.example.bankaccountmanager.exception.InvalidEntityException;
import com.example.bankaccountmanager.model.BankAccount;
import com.example.bankaccountmanager.model.Transaction;
import com.example.bankaccountmanager.model.TransactionType;
import com.example.bankaccountmanager.model.User;
import com.example.bankaccountmanager.service.BankAccountService;
import com.example.bankaccountmanager.service.TransactionService;
import com.example.bankaccountmanager.service.UserService;
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

import java.util.Collection;

import static com.example.bankaccountmanager.model.TransactionType.DEPOSIT;
import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

@Controller
@RequestMapping("/bank-accounts")
@Slf4j
public class TransactionController {
    private BankAccountService bankAccountService;
    private TransactionService transactionService;
    private UserService userService;

    @Autowired
    public TransactionController(BankAccountService bankAccountService,
                                 TransactionService transactionService,
                                 UserService userService) {
        this.bankAccountService = bankAccountService;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping("/{id}/transactions")
    public String getBankAccountTransactions(@PathVariable("id") Long id,
                                             Model model,
                                             HttpSession httpSession) {
        System.out.println("int TransactionController");
        User user = (User) httpSession.getAttribute("user");
        System.out.println(id);
        if(user != null) {
            Collection<Transaction> transactions = bankAccountService.findAllTransactionsByBankAccount(id);
            model.addAttribute("bankAccountTransactions", transactions);
            model.addAttribute("newTr", new Transaction());
            return "transactions";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/{id}/last-ten/transactions")
    public String getLastTenTransactionsByBankAccountID(@PathVariable("id") Long id,
                                                        Model model,
                                                        HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if(user != null) {
            Collection<Transaction> transactions = transactionService.findLast10TransactionsByUser(id);
            model.addAttribute("bankAccountTransactions", transactions);
            return "transactions";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/{id}/last-twenty/transactions")
    public String getLastTwentyTransactionsByBankAccountID(@PathVariable("id") Long id, Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if(user != null) {
            Collection<Transaction> transactions = transactionService.findLast20TransactionsByUser(id);
            model.addAttribute("bankAccountTransactions", transactions);
            return "transactions";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/{id}/last-fifty/transactions")
    public String getLastFiftyTransactionsByBankAccountID(@PathVariable("id") Long id,
                                                          Model model,
                                                          HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if(user != null) {
            Collection<Transaction> transactions = transactionService.findLast50TransactionsByUser(id);
            model.addAttribute("bankAccountTransactions", transactions);
            return "transactions";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/{id}/transactions")
    public String executeTransaction(@PathVariable("id") Long baId,
                                     @Valid @ModelAttribute("newTr") Transaction transaction,
                                     final BindingResult binding,
                                     RedirectAttributes redirectAttributes,
                                     HttpSession httpSession) {
        if (transaction == null) {
            System.out.println("No transaction");
        }
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser != null) {

        }
        if (binding.hasErrors()) {
            log.error("Error opening bankAccount: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("transaction", transaction);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "bankAccount",
                    binding);
            return "bank-accounts";
        }

        BankAccount counterpartyBA = bankAccountService.findById(baId);
        if (counterpartyBA != null) {
            transaction.setCounterparty(counterpartyBA);
            String recipientUsername = transaction.getRecipient().getHolder().getUsername();
            User recipient = userService.getUserByUsername(recipientUsername);
            bankAccountService.findByUser(recipient.getUserID())
                    .stream()
                    .findFirst().ifPresent(transaction::setRecipient);

            Double counterpartyBalance = transaction.getCounterparty().getBalance();
            Double transactionMoney = transaction.getMoney();
            switch (transaction.getType()) {
                case CASH_WITHDRAW -> {
                    try {
                        BankAccount counterpartyUpdatedBA = transaction.getCounterparty();
                        if (counterpartyBalance < transactionMoney) {
                            throw new InvalidEntityException("No enough money.");
                        }
                        counterpartyUpdatedBA.setBalance(counterpartyBalance - transactionMoney);
                        bankAccountService.updateAccount(counterpartyUpdatedBA);
                        transaction.setRecipient(counterpartyUpdatedBA);
                        transactionService.makeTransaction(transaction);

                        return "redirect:/bank-accounts";
                    } catch (Exception ex) {
                        httpSession.setAttribute("errorMessage",
                                "No enough money. The transaction cannot be executed.");

                        return "redirect:/bank-accounts/{id}/transactions";
                    }
                }
                case ONLINE_PAYMENT, DEBIT_CARD_CHARGE -> {
                    try {
                        BankAccount counterpartyUpdatedBA = transaction.getCounterparty();
                        counterpartyUpdatedBA.setBalance(counterpartyBalance - transactionMoney);
                        bankAccountService.updateAccount(counterpartyUpdatedBA);
                        transactionService.makeTransaction(transaction);

                        return "redirect:/bank-accounts";
                    } catch (Exception ex) {
                        httpSession.setAttribute("errorMessage",
                                "The transaction cannot be executed.");

                        return "redirect:/bank-accounts/{id}/transactions";
                    }
                }
                case DEPOSIT -> {
                    try {
                        BankAccount counterpartyUpdatedBA = transaction.getCounterparty();
                        counterpartyUpdatedBA.setBalance(counterpartyBalance + transactionMoney);
                        bankAccountService.updateAccount(counterpartyUpdatedBA);
                        transaction.setRecipient(counterpartyUpdatedBA);
                        transactionService.makeTransaction(transaction);

                        return "redirect:/bank-accounts";
                    } catch (Exception ex) {
                        httpSession.setAttribute("errorMessage",
                                "The transaction cannot be executed.");

                        return "redirect:/bank-accounts/{id}/transactions";
                    }
                }
            }
        }
        return "redirect:/bank-accounts/{id}/transactions";
    }
}