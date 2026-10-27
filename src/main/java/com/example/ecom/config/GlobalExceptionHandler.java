package com.example.ecom.config;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Centralized exception handling for all controllers.
 *
 * Catches:
 * - NoHandlerFoundException → 404 page
 * - IllegalArgumentException → 400 page (bad request)
 * - IllegalStateException → 400 page (bad state)
 * - Exception → 500 page (catch-all)
 *
 * Logs the error with the request URI for debugging.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(HttpServletRequest request, Model model) {
        log.warn("404 Not Found: {}", request.getRequestURI());
        model.addAttribute("requestedPath", request.getRequestURI());
        return "error-404";
    }

    @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
    public String handleBadRequest(Exception ex, HttpServletRequest request, Model model) {
        log.warn("Bad request on {}: {}", request.getRequestURI(), ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("requestedPath", request.getRequestURI());
        return "error-generic";
    }

    @ExceptionHandler(Exception.class)
    public String handleServerError(Exception ex, HttpServletRequest request, Model model) {
        log.error("Unhandled exception on {}", request.getRequestURI(), ex);
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("requestedPath", request.getRequestURI());
        return "error-500";
    }
}