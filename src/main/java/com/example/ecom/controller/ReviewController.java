package com.example.ecom.controller;

import com.example.ecom.model.Product;
import com.example.ecom.service.ProductService;
import com.example.ecom.service.ReviewService;
import com.example.ecom.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products/{productId}/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;

    /** POST /products/{productId}/reviews — submit a new review. */
    @PostMapping
    public String submitReview(@PathVariable Long productId,
            @RequestParam Integer rating,
            @RequestParam(required = false) String comment,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Long userId = SessionUtil.getUserId(session);
        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "Please sign in to leave a review");
            return "redirect:/auth/login";
        }

        try {
            reviewService.create(userId, productId, rating, comment);
            redirectAttributes.addFlashAttribute("success", "Review posted!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/products/" + productId;
    }

    /** POST /products/{productId}/reviews/{reviewId}/delete — delete a review. */
    @PostMapping("/{reviewId}/delete")
    public String deleteReview(@PathVariable Long productId,
            @PathVariable Long reviewId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Long userId = SessionUtil.getUserId(session);
        if (userId == null) {
            return "redirect:/auth/login";
        }

        try {
            reviewService.delete(reviewId, userId);
            redirectAttributes.addFlashAttribute("success", "Review deleted");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/products/" + productId;
    }
}