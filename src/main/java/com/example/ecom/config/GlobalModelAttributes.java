package com.example.ecom.config;

import com.example.ecom.service.CartService;
import com.example.ecom.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Adds session-derived attributes (username, role, isLoggedIn, cartCount)
 * to the model of every controller-handled request, so JSPs can reference
 * them via EL without each controller having to do it manually.
 *
 * CartService is session-scoped; Spring injects a proxy that resolves to
 * the correct per-session instance.
 */
@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private CartService cartService;

    @ModelAttribute
    public void addSessionAttributes(HttpSession session, Model model) {
        model.addAttribute("username", SessionUtil.getUsername(session));
        model.addAttribute("userRole", SessionUtil.getUserRole(session));
        model.addAttribute("isLoggedIn", SessionUtil.isLoggedIn(session));
        model.addAttribute("isAdmin", SessionUtil.isAdmin(session));

        // Only show cart count if logged in (cart is per-session, but we
        // don't want to surface it for anonymous users since the cart
        // page itself is protected)
        if (SessionUtil.isLoggedIn(session)) {
            model.addAttribute("cartCount", cartService.getItemCount());
        } else {
            model.addAttribute("cartCount", 0);
        }
    }
}