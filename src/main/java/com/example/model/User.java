package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory") // Ensuring name is not blank
    private String name;

    @NotBlank(message = "Username is mandatory") // Ensuring username is not blank
    private String username; // Add this line for username

    @NotBlank(message = "Email is mandatory") // Ensuring email is not blank
    @Email(message = "Email should be valid") // Ensuring email format is valid
    private String email;

    @NotBlank(message = "Password is mandatory") // Ensuring password is not blank
    private String password;

    // Default no-args constructor
    public User() {}

    // Parameterized constructor
    public User(Long id, String name, String username, String email, String password) { // Include username in constructor
        this.id = id;
        this.name = name;
        this.username = username; // Add this line
        this.email = email;
        this.password = password; // Include password in constructor
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUsername() { return username; } // Add this getter
    public void setUsername(String username) { this.username = username; } // Add this setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Override toString for debugging (exclude password)
    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', username='" + username + "', email='" + email + "'}"; // Include username
    }

    // Override equals and hashCode for proper comparison and usage in collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return 31 + (id != null ? id.hashCode() : 0);
    }
}
