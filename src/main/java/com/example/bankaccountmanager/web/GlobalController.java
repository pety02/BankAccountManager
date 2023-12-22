package com.example.bankaccountmanager.web;

import com.example.bankaccountmanager.model.Bank;
import com.example.bankaccountmanager.model.BankAccount;
import com.example.bankaccountmanager.model.Transaction;
import com.example.bankaccountmanager.model.User;
import com.example.bankaccountmanager.service.BankService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

@ControllerAdvice
public class GlobalController {
    private BankService bankService;
    @Autowired
    public GlobalController(BankService bankService) {
        this.bankService = bankService;
    }
    @ModelAttribute("unm")
    public String populateUser(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if(currentUser != null) {
            return currentUser.getUsername();
        }

        return "";
    }

    @ModelAttribute("newBA")
    public BankAccount addEmptyBankAccount(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if(currentUser != null) {
            return new BankAccount();
        }

        return null;
    }

    @ModelAttribute("newTr")
    public Transaction addEmptyTransaction(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if(currentUser != null) {
            return new Transaction();
        }

        return null;
    }

    @ModelAttribute("roles")
    public LinkedList<String> addAllUserRoles() {

        LinkedList<String> roles = new LinkedList<>();
        roles.add("CLIENT");

        return roles;
    }

    @ModelAttribute("transactionTypes")
    public LinkedList<String> addTransactionTypes() {

        LinkedList<String> transactionTypes = new LinkedList<>();
        transactionTypes.add("CASH_WITHDRAW");
        transactionTypes.add("DEPOSIT");
        transactionTypes.add("ONLINE_PAYMENT");
        transactionTypes.add("DEBIT_CARD_CHARGE");

        return transactionTypes;
    }

    @ModelAttribute("banks")
    public Collection<Bank> addAllBanks(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if(currentUser != null) {
            return bankService.getAllBanks();
        }

        return new ArrayList<>();
    }
}
