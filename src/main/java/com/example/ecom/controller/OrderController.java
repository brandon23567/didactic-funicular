package com.example.ecom.controller;

import com.example.ecom.model.Order;
import com.example.ecom.service.OrderService;
import com.example.ecom.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /** GET /orders — current user's order history. */
    @GetMapping
    public String listOrders(HttpSession session, Model model) {
        Long userId = SessionUtil.getUserId(session);
        List<Order> orders = orderService.findByUser(userId);
        model.addAttribute("orders", orders);
        return "orders";
    }

    /** GET /orders/{id} — single order detail (items + totals). */
    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        Long userId = SessionUtil.getUserId(session);

        // Fetch with items pre-loaded (single query)
        Order order = orderService.findByIdWithItems(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));

        // Security: a user can only view their own orders (admins can view any)
        if (!order.getUser().getId().equals(userId) && !SessionUtil.isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error",
                    "You don't have permission to view this order");
            return "redirect:/orders";
        }

        model.addAttribute("order", order);
        return "order-detail";
    }

    /** GET /checkout — show the checkout form. */
    @GetMapping("/checkout")
    public String checkoutForm(HttpSession session, Model model,
            RedirectAttributes redirectAttributes) {
        if (session == null) {
            return "redirect:/auth/login";
        }

        // Use the cart service to display what's about to be ordered
        // Reuse the cart page layout, but we'll do a simpler version here
        return "checkout";
    }

    /** POST /orders/checkout — finalize the order from the cart. */
    @PostMapping("/checkout")
    public String placeOrder(@RequestParam(required = false) String shippingAddress,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Long userId = SessionUtil.getUserId(session);
        if (userId == null) {
            return "redirect:/auth/login";
        }

        if (shippingAddress == null || shippingAddress.isBlank()) {
            shippingAddress = "To be confirmed";
        }

        try {
            Order order = orderService.checkout(userId, shippingAddress);
            redirectAttributes.addFlashAttribute("success",
                    "Order #" + order.getId() + " placed successfully!");
            return "redirect:/orders/" + order.getId();
        } catch (IllegalStateException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cart";
        }
    }
}