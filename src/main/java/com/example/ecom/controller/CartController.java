package com.example.ecom.controller;

import com.example.ecom.model.Product;
import com.example.ecom.service.CartService;
import com.example.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    /** GET /cart — show the cart page. */
    @GetMapping
    public String viewCart(Model model) {
        // Build a list of {product, quantity} pairs for the JSP
        List<CartItemView> viewItems = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : cartService.getItems().entrySet()) {
            productService.findById(entry.getKey()).ifPresent(product -> {
                viewItems.add(new CartItemView(product, entry.getValue()));
            });
        }

        model.addAttribute("cartItems", viewItems);
        model.addAttribute("subtotal", cartService.getSubtotal());
        model.addAttribute("itemCount", cartService.getItemCount());
        return "cart";
    }

    /** POST /cart — add a product to the cart. */
    @PostMapping
    public String addToCart(@RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity,
            RedirectAttributes redirectAttributes) {
        try {
            cartService.add(productId, quantity);
            redirectAttributes.addFlashAttribute("success",
                    "Added " + quantity + " item(s) to your cart");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cart";
    }

    /** POST /cart/update — change quantity of a cart line. */
    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long productId,
            @RequestParam Integer quantity,
            RedirectAttributes redirectAttributes) {
        try {
            cartService.updateQuantity(productId, quantity);
            redirectAttributes.addFlashAttribute("success", "Cart updated");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cart";
    }

    /** POST /cart/remove — remove a product from the cart entirely. */
    @PostMapping("/remove")
    public String removeItem(@RequestParam Long productId,
            RedirectAttributes redirectAttributes) {
        cartService.remove(productId);
        redirectAttributes.addFlashAttribute("success", "Item removed");
        return "redirect:/cart";
    }

    /** POST /cart/clear — empty the entire cart. */
    @PostMapping("/clear")
    public String clearCart(RedirectAttributes redirectAttributes) {
        cartService.clear();
        redirectAttributes.addFlashAttribute("success", "Cart cleared");
        return "redirect:/cart";
    }

    /** Lightweight view-model DTO for the cart JSP. */
    public static class CartItemView {
        private final Product product;
        private final Integer quantity;

        public CartItemView(Product product, Integer quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public Integer getQuantity() {
            return quantity;
        }
    }
}