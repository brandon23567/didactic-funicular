package com.example.ecom.service;

import com.example.ecom.model.User;
import com.example.ecom.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * Register a new user.
     * Returns the persisted User on success.
     * Throws IllegalArgumentException if username or email already exists.
     */
    public User register(String username, String password, String email, String fullName) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        if (userDao.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userDao.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // plain-text — replace with BCrypt later
        user.setEmail(email);
        user.setFullName(fullName);
        user.setRole("USER");
        return userDao.save(user);
    }

    /**
     * Validate login credentials.
     * Returns the User if credentials match, empty Optional otherwise.
     */
    public Optional<User> login(String username, String password) {
        return userDao.findByUsername(username)
                .filter(u -> u.getPassword().equals(password));
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User updateProfile(Long id, String email, String fullName) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));

        if (email != null && !email.isBlank() && !email.equals(user.getEmail())) {
            if (userDao.existsByEmail(email)) {
                throw new IllegalArgumentException("Email already in use");
            }
            user.setEmail(email);
        }
        if (fullName != null) {
            user.setFullName(fullName);
        }
        return userDao.update(user);
    }

    @Transactional(readOnly = true)
    public boolean isAdmin(User user) {
        return user != null && "ADMIN".equals(user.getRole());
    }
}