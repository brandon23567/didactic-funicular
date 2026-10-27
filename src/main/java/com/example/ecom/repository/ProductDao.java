package com.example.ecom.repository;

import com.example.ecom.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductDao extends GenericDao<Product, Long> {

    public ProductDao() {
        super(Product.class);
    }

    public List<Product> findActive() {
        return session()
                .createQuery("FROM Product p WHERE p.active = true ORDER BY p.createdAt DESC", Product.class)
                .getResultList();
    }

    public List<Product> findActive(int maxResults) {
        return session()
                .createQuery("FROM Product p WHERE p.active = true ORDER BY p.createdAt DESC", Product.class)
                .setMaxResults(maxResults)
                .getResultList();
    }

    public List<Product> searchByName(String query) {
        return session()
                .createQuery("FROM Product p WHERE p.active = true AND LOWER(p.name) LIKE :q ORDER BY p.name",
                        Product.class)
                .setParameter("q", "%" + query.toLowerCase() + "%")
                .getResultList();
    }

    /**
     * Fetch a product along with its reviews (and the user who wrote each
     * review). Returns an Optional that's empty if the product doesn't exist.
     *
     * Uses LEFT JOIN FETCH to avoid N+1 queries when displaying reviews.
     */
    public Optional<Product> findByIdWithReviews(Long id) {
        List<Product> results = session()
                .createQuery(
                        "SELECT DISTINCT p FROM Product p " +
                                "LEFT JOIN FETCH p.reviews r " +
                                "LEFT JOIN FETCH r.user " +
                                "WHERE p.id = :id",
                        Product.class)
                .setParameter("id", id)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}