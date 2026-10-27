package com.example.ecom.repository;

import com.example.ecom.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderDao extends GenericDao<Order, Long> {

    public OrderDao() {
        super(Order.class);
    }

    public List<Order> findByUser(Long userId) {
        return session()
                .createQuery(
                        "FROM Order o WHERE o.user.id = :userId " +
                                "ORDER BY o.createdAt DESC",
                        Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    /**
     * Fetch an order together with its items and the product referenced by
     * each item — so the order-detail page renders with a single query
     * instead of N+1 lazy lookups.
     */
    public Optional<Order> findByIdWithItems(Long id) {
        List<Order> results = session()
                .createQuery(
                        "SELECT DISTINCT o FROM Order o " +
                                "LEFT JOIN FETCH o.items i " +
                                "LEFT JOIN FETCH i.product " +
                                "WHERE o.id = :id",
                        Order.class)
                .setParameter("id", id)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public List<Order> findAllByStatus(String status) {
        return session()
                .createQuery(
                        "FROM Order o WHERE o.status = :status " +
                                "ORDER BY o.createdAt DESC",
                        Order.class)
                .setParameter("status", status)
                .getResultList();
    }
}