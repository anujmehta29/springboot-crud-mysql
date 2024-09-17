package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder instead of BCryptPasswordEncoder

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("useranuj".equals(username)) {
            // Encode the password using PasswordEncoder
            String encodedPassword = passwordEncoder.encode("passwordanuj"); // Ensure this password matches your actual encoded password
            return new User("useranuj", encodedPassword, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
