package com.example.ecom.service;

import com.example.ecom.model.Product;
import com.example.ecom.model.Review;
import com.example.ecom.model.User;
import com.example.ecom.repository.ProductDao;
import com.example.ecom.repository.ReviewDao;
import com.example.ecom.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    public List<Review> findByProduct(Long productId) {
        return reviewDao.findByProduct(productId);
    }

    @Transactional(readOnly = true)
    public List<Review> findByUser(Long userId) {
        return reviewDao.findByUser(userId);
    }

    @Transactional(readOnly = true)
    public Double averageRating(Long productId) {
        return reviewDao.averageRatingForProduct(productId);
    }

    /**
     * Create a review. Enforces:
     * - rating between 1 and 5
     * - one review per (user, product)
     * - user and product must exist
     */
    public Review create(Long userId, Long productId, Integer rating, String comment) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        if (comment != null && comment.length() > 1000) {
            throw new IllegalArgumentException("Comment too long (max 1000 chars)");
        }

        User user = userDao.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        if (reviewDao.findByUserAndProduct(userId, productId).isPresent()) {
            throw new IllegalArgumentException("You have already reviewed this product");
        }

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setComment(comment);
        return reviewDao.save(review);
    }

    public void delete(Long reviewId, Long requestingUserId) {
        Review review = reviewDao.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found: " + reviewId));

        // Only the review's author or an admin can delete
        boolean isOwner = review.getUser().getId().equals(requestingUserId);
        User requester = userDao.findById(requestingUserId).orElse(null);
        boolean isAdmin = requester != null && "ADMIN".equals(requester.getRole());

        if (!isOwner && !isAdmin) {
            throw new IllegalStateException("Not authorized to delete this review");
        }
        reviewDao.delete(review);
    }
}