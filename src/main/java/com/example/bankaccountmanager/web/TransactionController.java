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

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;

import static com.example.bankaccountmanager.model.TransactionType.DEPOSIT;
import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

@Controller
@RequestMapping("/bank-accounts")
@Slf4j
public class TransactionController {
    private BankAccountService bankAccountService;
    private TransactionService transactionService;
    private UserService userService;

    private String getLastXTransactions(User user, Long baId, RedirectAttributes redirect, Collection<Transaction> transactions) {
        if(user != null) {
            redirect.addFlashAttribute("currentBankAccountBalance", bankAccountService.getBankAccountBalance(baId));
            redirect.addFlashAttribute("currentBankAccountIBAN", bankAccountService.findById(baId).getIban());
            redirect.addFlashAttribute("bankAccountTransactions", transactions);
            redirect.addFlashAttribute("newTr", new Transaction());
            redirect.addFlashAttribute("errorMessage", "");
            return "redirect:/bank-accounts/{id}/transactions";
        } else {
            return "redirect:/auth/login";
        }
    }

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
        User user = (User) httpSession.getAttribute("user");
        if(user == null) {
            return "redirect:/auth/login";
        }
        if(!model.containsAttribute("bankAccountTransactions")) {
            if(!model.containsAttribute("currentBankAccountBalance")) {
                model.addAttribute("currentBankAccountBalance", bankAccountService.getBankAccountBalance(id));
                model.addAttribute("currentBankAccountIBAN", bankAccountService.findById(id).getIban());
            }
            Collection<Transaction> transactions = bankAccountService.findAllTransactionsByBankAccount(id);
            model.addAttribute("bankAccountTransactions", transactions);
        }
        if(!model.containsAttribute("newTr")) {
            model.addAttribute("newTr", new Transaction());
        }
        if(!model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", "");
        }
        if(!model.containsAttribute("possibleRecipientBankAccounts")) {
            model.addAttribute("possibleRecipientBankAccounts", new LinkedList<>());
        }
        if(!model.containsAttribute("rUsername")) {
            httpSession.setAttribute("rUsername", "");
        }
        return "transactions";
    }

    @GetMapping("/{id}/last-ten/transactions")
    public String getLastTenTransactionsByBankAccountID(@PathVariable("id") Long id,
                                                        HttpSession httpSession,
                                                        RedirectAttributes redirect) {
        User user = (User) httpSession.getAttribute("user");
        if(user == null) {
            return "redirect:/auth/login";
        }
        httpSession.setAttribute("rUsername", "");
        return getLastXTransactions(user, id, redirect, transactionService.findLast10TransactionsByUser(id));
    }

    @GetMapping("/{id}/last-twenty/transactions")
    public String getLastTwentyTransactionsByBankAccountID(@PathVariable("id") Long id,
                                                           RedirectAttributes redirect,
                                                           HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if(user == null) {
            return "redirect:/auth/login";
        }
        httpSession.setAttribute("rUsername", "");
        return getLastXTransactions(user, id, redirect, transactionService.findLast20TransactionsByUser(id));
    }

    @GetMapping("/{id}/last-fifty/transactions")
    public String getLastFiftyTransactionsByBankAccountID(@PathVariable("id") Long id,
                                                          RedirectAttributes redirect,
                                                          HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if(user == null) {
            return "redirect:/auth/login";
        }
        httpSession.setAttribute("rUsername", "");
        return getLastXTransactions(user, id, redirect, transactionService.findLast50TransactionsByUser(id));
    }

    @GetMapping("/{id}/transactions/recipient-ibans")
    public String getAllBankAccounts(@PathVariable("id") Long baId,
                                     @RequestParam("rUsername") String unm,
                                     @ModelAttribute("newTr") Transaction transaction,
                                     Model model,
                                     final BindingResult binding,
                                     RedirectAttributes redirectAttributes,
                                     HttpSession httpSession) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser == null) {
            return "redirect:/auth/login";
        }
        redirectAttributes.addFlashAttribute("transactions", bankAccountService.findAllTransactionsByBankAccount(baId));
        redirectAttributes.addFlashAttribute("newTr", transaction);
        if(!model.containsAttribute("possibleRecipientBankAccounts")) {
            Collection<BankAccount> possibleIBANs = bankAccountService.findAllByUsername(unm);
            redirectAttributes.addFlashAttribute("possibleRecipientBankAccounts", possibleIBANs);
        }
        if (binding.hasErrors()) {
            log.error("Error opening bankAccount: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "bankAccount",
                    binding);
            return "redirect:/bank-accounts/{id}/transactions";
        }
        return "redirect:/bank-accounts/{id}/transactions";
    }
    @PostMapping("/{id}/transactions")
    public String executeTransaction(@PathVariable("id") Long baId,
                                     @Valid @ModelAttribute("newTr") Transaction transaction,
                                     final BindingResult binding,
                                     RedirectAttributes redirectAttributes,
                                     HttpSession httpSession) {
        User loggedUser = (User) httpSession.getAttribute("user");
        httpSession.setAttribute("rUsername", "");
        if (loggedUser != null) {
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
                String recipientIBAN = transaction.getRecipient().getIban();
                BankAccount recipient = bankAccountService.findByIBAN(recipientIBAN);
                if(recipient != null) {
                    transaction.setRecipient(recipient);
                } else {
                    return "redirect:/bank-accounts/{id}/transactions";
                }

                Double counterpartyBalance = transaction.getCounterparty().getBalance();
                Double transactionMoney = transaction.getMoney();
                switch (transaction.getType()) {
                    case CASH_WITHDRAW -> {
                        try {
                            BankAccount counterpartyUpdatedBA = transaction.getCounterparty();
                            if (counterpartyBalance < transactionMoney) {
                                redirectAttributes.addFlashAttribute("errorMessage",
                                        "No enough money. The transaction cannot be executed.");

                                return "redirect:/bank-accounts/{id}/transactions";
                            }
                            counterpartyUpdatedBA.setBalance(counterpartyBalance - transactionMoney);
                            bankAccountService.updateAccount(counterpartyUpdatedBA);
                            transaction.setRecipient(counterpartyUpdatedBA);
                            transaction.setDateTime(LocalDate.now());
                            transactionService.makeTransaction(transaction);

                            return "redirect:/bank-accounts/{id}/transactions";
                        } catch (Exception ex) {
                            redirectAttributes.addFlashAttribute("errorMessage",
                                    "No enough money. The transaction cannot be executed.");

                            return "redirect:/bank-accounts/{id}/transactions";
                        }
                    }
                    case ONLINE_PAYMENT, DEBIT_CARD_CHARGE -> {
                        try {
                            BankAccount counterpartyUpdatedBA = transaction.getCounterparty();
                            if (counterpartyBalance < transactionMoney) {
                                redirectAttributes.addFlashAttribute("errorMessage",
                                        "No enough money. The transaction cannot be executed.");

                                return "redirect:/bank-accounts/{id}/transactions";
                            }
                            counterpartyUpdatedBA.setBalance(counterpartyBalance - transactionMoney);
                            bankAccountService.updateAccount(counterpartyUpdatedBA);
                            Double recipientCurrentBalance = recipient.getBalance();
                            recipient.setBalance(recipientCurrentBalance + transactionMoney);
                            bankAccountService.updateAccount(recipient);
                            transaction.setDateTime(LocalDate.now());
                            transactionService.makeTransaction(transaction);

                            return "redirect:/bank-accounts/{id}/transactions";
                        } catch (Exception ex) {
                            redirectAttributes.addFlashAttribute("errorMessage",
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
                            transaction.setDateTime(LocalDate.now());
                            transactionService.makeTransaction(transaction);

                            return "redirect:/bank-accounts/{id}/transactions";
                        } catch (Exception ex) {
                            redirectAttributes.addFlashAttribute("errorMessage",
                                    "The transaction cannot be executed.");

                            return "redirect:/bank-accounts/{id}/transactions";
                        }
                    }
                }
            }
            return "redirect:/bank-accounts/{id}/transactions";
        }

        return "redirect:/auth/login";
    }
}