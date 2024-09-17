package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Fetch all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content if no users found
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Fetch user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404 if not found
    }

    // Registration endpoint (Ensure it's mapped to POST)
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request if body is null
        }
        User createdUser = userService.saveUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED); // 201 Created
    }

    // Update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request if body is null
        }

        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            user.setId(id); // Ensure the correct ID is set
            User updatedUser = userService.saveUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK); // 200 OK
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 if user doesn't exist
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content on successful deletion
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 if user doesn't exist
    }
}
