package com.example.bankaccountmanager.web;

import com.example.bankaccountmanager.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String getHomeForm(HttpSession httpSession) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if(loggedUser != null) {
            return "home";
        }

        return "redirect:/auth/login";
    }
}