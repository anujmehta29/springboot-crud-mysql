package com.example.controller; // Ensure this matches the directory structure

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/") // Maps HTTP GET requests to the root URL
    public String home() {
        return "Welcome to the CRUD Application!";
    }
}
