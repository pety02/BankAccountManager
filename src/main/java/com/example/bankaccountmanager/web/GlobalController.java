package com.example.bankaccountmanager.web;

import com.example.bankaccountmanager.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {
    @ModelAttribute("unm")
    public String populateUser(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if(currentUser != null) {
            return currentUser.getUsername();
        }

        return "";
    }
}
