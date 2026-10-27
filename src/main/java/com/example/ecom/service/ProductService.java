package com.example.ecom.service;

import com.example.ecom.model.Product;
import com.example.ecom.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Transactional(readOnly = true)
    public List<Product> findAllActive() {
        return productDao.findActive();
    }

    @Transactional(readOnly = true)
    public List<Product> findRecent(int limit) {
        return productDao.findActive(limit);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productDao.findById(id);
    }

    /**
     * Fetch a product together with its reviews and review authors.
     * Use this for the product detail page to avoid N+1 queries.
     */
    @Transactional(readOnly = true)
    public Optional<Product> findByIdWithReviews(Long id) {
        return productDao.findByIdWithReviews(id);
    }

    @Transactional(readOnly = true)
    public List<Product> search(String query) {
        if (query == null || query.isBlank()) {
            return productDao.findActive();
        }
        return productDao.searchByName(query);
    }

    public Product create(String name, String description, BigDecimal price,
            String imageUrl, Integer stock) {
        validateProductFields(name, price, stock);
        Product p = new Product();
        p.setName(name);
        p.setDescription(description);
        p.setPrice(price);
        p.setImageUrl(imageUrl);
        p.setStock(stock != null ? stock : 0);
        p.setActive(true);
        return productDao.save(p);
    }

    public Product update(Long id, String name, String description, BigDecimal price,
            String imageUrl, Integer stock, Boolean active) {
        Product p = productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        if (name != null && !name.isBlank())
            p.setName(name);
        if (description != null)
            p.setDescription(description);
        if (price != null && price.compareTo(BigDecimal.ZERO) >= 0)
            p.setPrice(price);
        if (imageUrl != null)
            p.setImageUrl(imageUrl);
        if (stock != null && stock >= 0)
            p.setStock(stock);
        if (active != null)
            p.setActive(active);
        return productDao.update(p);
    }

    /**
     * Soft-delete: marks the product inactive so historical orders remain intact.
     */
    public void deactivate(Long id) {
        productDao.findById(id).ifPresent(p -> {
            p.setActive(false);
            productDao.update(p);
        });
    }

    /** Hard-delete. Use only when you really want to remove the row. */
    public void delete(Long id) {
        productDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    public void validateProductFields(String name, BigDecimal price, Integer stock) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be >= 0");
        }
        if (stock != null && stock < 0) {
            throw new IllegalArgumentException("Stock must be >= 0");
        }
    }
}