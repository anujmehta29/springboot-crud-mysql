package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Fetch all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content if no users found
        }
        return ResponseEntity.ok(users); // 200 OK with user list
    }

    // Fetch user by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get()); // Return User if found
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse); // 404 if not found
        }
    }

    // Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser); // 201 Created
    }

    // Update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            user.setId(id); // Ensure the correct ID is set
            User updatedUser = userService.saveUser(user);
            return ResponseEntity.ok(updatedUser); // 200 OK with updated user
        }
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "User not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse); // 404 if user doesn't exist
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build(); // 204 No Content on successful deletion
        }
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "User not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse); // 404 if user doesn't exist
    }
}
