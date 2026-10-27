package com.example.ecom.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Allows direct navigation to /error/404, /error/500 for testing.
 * In production, Spring's error dispatcher forwards here automatically.
 */
@Controller
public class ErrorController {

    @GetMapping("/error/404")
    public String show404(HttpServletRequest request, Model model) {
        model.addAttribute("requestedPath", request.getHeader("Referer"));
        return "error-404";
    }

    @GetMapping("/error/500")
    public String show500() {
        return "error-500";
    }

    @GetMapping("/error")
    public String showError() {
        return "error-generic";
    }
}