package com.example.bankaccountmanager.web;

import com.example.bankaccountmanager.model.User;
import com.example.bankaccountmanager.model.UserRole;
import com.example.bankaccountmanager.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
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

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model, HttpServletRequest request) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid @ModelAttribute("user") User user,
                                  final BindingResult binding,
                                  RedirectAttributes redirectAttributes) {
        if (binding.hasErrors()) {
            log.error("Error registering user: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "user", binding);
            return "redirect:register";
        }
        try {
            LocalDate now = LocalDate.now();
            int year = now.getYear() - 18;
            LocalDate dateEighteenYearsAgo = LocalDate.of(year, now.getMonth(), now.getDayOfMonth());
            if(user.getBirthDate().isBefore(dateEighteenYearsAgo)) {
                authService.register(user);
            } else {
                return "redirect:register";
            }
        } catch (Exception ex) {
            log.error("Error registering user", ex);
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "user", binding);
            return "redirect:register";
        }

        return "redirect:login";
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        if (!model.containsAttribute("username")) {
            model.addAttribute("username", "");
        }
        if (!model.containsAttribute("password")) {
            model.addAttribute("password", "");
        }

        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes,
                        HttpSession session) {
        User loggedUser = null;
        try {
            loggedUser = authService.login(username, password);
            if (loggedUser == null) {
                String errors = "Invalid username or password.";
                redirectAttributes.addFlashAttribute("username", username);
                redirectAttributes.addFlashAttribute("errors", errors);
                redirectAttributes.addAttribute("redirectUrl", "home");
                return "redirect:login";
            } else {
                session.setAttribute("user", loggedUser);
                return "redirect:/home";
            }
        } catch (Exception ex) {
            log.error("Error login user", ex);
            redirectAttributes.addFlashAttribute("user", loggedUser);
            return "redirect:login";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) { // OR SessionStatus status
        session.invalidate();
        return "redirect:/";
    }
}