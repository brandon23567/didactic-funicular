package com.example.ecom.controller;

import com.example.ecom.model.Product;
import com.example.ecom.service.ProductService;
import com.example.ecom.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    /** GET /products — list all active products, with optional search. */
    @GetMapping
    public String listProducts(@RequestParam(required = false) String q, Model model) {
        List<Product> products = productService.search(q);
        model.addAttribute("products", products);
        model.addAttribute("searchQuery", q);
        return "products";
    }

    /** GET /products/{id} — product detail page with reviews. */
    @GetMapping("/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.findByIdWithReviews(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        Double avgRating = reviewService.averageRating(id);
        model.addAttribute("product", product);
        model.addAttribute("avgRating", avgRating);
        model.addAttribute("reviewCount", product.getReviews() != null ? product.getReviews().size() : 0);
        return "product-detail";
    }
}