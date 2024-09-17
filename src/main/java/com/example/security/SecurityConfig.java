package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // Protecting /api/users/** endpoints and requiring authentication
                .requestMatchers("/api/users/**").authenticated()
                
                // Allowing public access to /users/register and other endpoints
                .requestMatchers("/users/register").permitAll()
                
                // Any other requests are allowed without authentication
                .anyRequest().permitAll()
            )
            .httpBasic(); // Using basic authentication for simplicity

        // Disabling CSRF protection if not needed (typically disabled for APIs)
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    // Defining the password encoder bean for secure password storage
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
