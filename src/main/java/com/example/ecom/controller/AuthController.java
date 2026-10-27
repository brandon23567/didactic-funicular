package com.example.ecom.controller;

import com.example.ecom.model.User;
import com.example.ecom.service.UserService;
import com.example.ecom.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // ===== GET /auth/login =====
    @GetMapping("/login")
    public String showLoginForm(HttpSession session, Model model) {
        // Already logged in? Skip login form, go home.
        if (SessionUtil.isLoggedIn(session)) {
            return "redirect:/";
        }
        return "login";
    }

    // ===== POST /auth/login =====
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            User user = userService.login(username, password)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

            SessionUtil.loginUser(session, user);

            // If we stashed an original URL, return there; otherwise go home
            String redirectUrl = SessionUtil.consumeRedirectAfterLogin(session);
            return "redirect:" + (redirectUrl != null ? redirectUrl : "/");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("username", username);
            return "redirect:/auth/login";
        }
    }

    // ===== GET /auth/signup =====
    @GetMapping("/signup")
    public String showSignupForm(HttpSession session) {
        if (SessionUtil.isLoggedIn(session)) {
            return "redirect:/";
        }
        return "signup";
    }

    // ===== POST /auth/signup =====
    @PostMapping("/signup")
    public String processSignup(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam(required = false) String fullName,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            User user = userService.register(username, password, email, fullName);
            SessionUtil.loginUser(session, user); // auto-login after signup
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("fullName", fullName);
            return "redirect:/auth/signup";
        }
    }

    // ===== GET /logout =====
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionUtil.logout(session);
        session.invalidate();
        return "redirect:/auth/login?logout";
    }
}