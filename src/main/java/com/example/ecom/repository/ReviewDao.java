package com.example.ecom.repository;

import com.example.ecom.model.Review;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReviewDao extends GenericDao<Review, Long> {

    public ReviewDao() {
        super(Review.class);
    }

    public List<Review> findByProduct(Long productId) {
        return session()
                .createQuery(
                        "FROM Review r WHERE r.product.id = :productId " +
                                "ORDER BY r.createdAt DESC",
                        Review.class)
                .setParameter("productId", productId)
                .getResultList();
    }

    public List<Review> findByUser(Long userId) {
        return session()
                .createQuery(
                        "FROM Review r WHERE r.user.id = :userId " +
                                "ORDER BY r.createdAt DESC",
                        Review.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    /** Used to enforce "one review per user per product". */
    public Optional<Review> findByUserAndProduct(Long userId, Long productId) {
        List<Review> results = session()
                .createQuery(
                        "FROM Review r WHERE r.user.id = :userId AND r.product.id = :productId",
                        Review.class)
                .setParameter("userId", userId)
                .setParameter("productId", productId)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    /** Average rating across all reviews of a product — null if no reviews. */
    public Double averageRatingForProduct(Long productId) {
        return session()
                .createQuery(
                        "SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId",
                        Double.class)
                .setParameter("productId", productId)
                .uniqueResult();
    }
}