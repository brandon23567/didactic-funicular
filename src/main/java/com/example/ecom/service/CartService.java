package com.example.ecom.service;

import com.example.ecom.model.Product;
import com.example.ecom.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class CartService {

    @Autowired
    private ProductDao productDao;

    private final Map<Long, Integer> items = new LinkedHashMap<>();

    public void add(Long productId, int quantity) {
        if (quantity <= 0)
            return;
        Product p = productDao.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        if (!p.isActive()) {
            throw new IllegalStateException("Product is no longer available: " + productId);
        }
        items.merge(productId, quantity, Integer::sum);
    }

    public void updateQuantity(Long productId, int quantity) {
        if (quantity <= 0) {
            items.remove(productId);
            return;
        }
        if (productDao.findById(productId).isEmpty()) {
            throw new IllegalArgumentException("Product not found: " + productId);
        }
        items.put(productId, quantity);
    }

    public void remove(Long productId) {
        items.remove(productId);
    }

    public void clear() {
        items.clear();
    }

    public Map<Long, Integer> getItems() {
        return new LinkedHashMap<>(items);
    }

    public int getItemCount() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }

    @Transactional(readOnly = true)
    public BigDecimal getSubtotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Long, Integer> entry : items.entrySet()) {
            Product p = productDao.findById(entry.getKey()).orElse(null);
            if (p != null) {
                total = total.add(p.getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
            }
        }
        return total;
    }

    @Transactional(readOnly = true)
    public boolean isEmpty() {
        return items.isEmpty();
    }
}