package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Configuring access to specific endpoints
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/users/register", "/api/public/**").permitAll() // Allow public registration and other public endpoints
                .requestMatchers(HttpMethod.GET, "/api/users/**").authenticated() // Secure GET requests for /api/users/**
                .requestMatchers(HttpMethod.POST, "/api/users/**").authenticated() // Secure POST requests for /api/users/**
                .requestMatchers(HttpMethod.PUT, "/api/users/**").authenticated() // Secure PUT requests for /api/users/**
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").authenticated() // Secure DELETE requests for /api/users/**
                .anyRequest().permitAll() // Allow all other requests without authentication
            )
            // Enabling HTTP Basic authentication
            .httpBasic();

        // Disabling CSRF protection for stateless APIs
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    // Bean for password encoding with BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
