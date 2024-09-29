package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Fetch all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Fetch user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Save or update user
    public User saveUser(User user) {
        validateUser(user);
        return userRepository.save(user);
    }

    // Delete user by ID
    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("User with ID " + id + " not found", e);
        }
    }

    // Validate user data
    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new ValidationException("Name is mandatory");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username is mandatory");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new ValidationException("Password is mandatory");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email is mandatory");
        }
        // Email format validation
        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Invalid email format");
        }

        // Check if email or username already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ValidationException("Email is already taken");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ValidationException("Username is already taken");
        }
    }
}
