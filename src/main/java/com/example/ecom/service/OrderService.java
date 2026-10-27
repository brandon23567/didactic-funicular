package com.example.ecom.service;

import com.example.ecom.model.Order;
import com.example.ecom.model.OrderItem;
import com.example.ecom.model.Product;
import com.example.ecom.model.User;
import com.example.ecom.repository.OrderDao;
import com.example.ecom.repository.ProductDao;
import com.example.ecom.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CartService cartService;

    /**
     * Convert the current session cart into a persisted Order.
     * Returns the created Order (with id assigned).
     *
     * Steps:
     * 1. Validate cart isn't empty
     * 2. Validate user exists
     * 3. For each cart line, snapshot the product's current price
     * 4. Compute total
     * 5. Persist order + items (cascade ALL)
     * 6. Clear the cart
     */
    public Order checkout(Long userId, String shippingAddress) {
        if (cartService.isEmpty()) {
            throw new IllegalStateException("Cannot checkout an empty cart");
        }
        User user = userDao.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Order order = new Order();
        order.setUser(user);
        order.setStatus("PENDING");
        order.setShippingAddress(shippingAddress);

        // Add each cart line as an OrderItem with a price snapshot
        for (var entry : cartService.getItems().entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = productDao.findById(productId)
                    .orElseThrow(() -> new IllegalStateException(
                            "Product in cart no longer exists: " + productId));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setUnitPrice(product.getPrice()); // snapshot
            order.addItem(item); // keeps both sides in sync
        }

        order.recalculateTotal();
        Order saved = orderDao.save(order);
        cartService.clear();
        return saved;
    }

    @Transactional(readOnly = true)
    public Optional<Order> findByIdWithItems(Long id) {
        return orderDao.findByIdWithItems(id);
    }

    @Transactional(readOnly = true)
    public List<Order> findByUser(Long userId) {
        return orderDao.findByUser(userId);
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Order> findAllByStatus(String status) {
        return orderDao.findAllByStatus(status);
    }

    public Order updateStatus(Long orderId, String newStatus) {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        order.setStatus(newStatus);
        return orderDao.update(order);
    }
}