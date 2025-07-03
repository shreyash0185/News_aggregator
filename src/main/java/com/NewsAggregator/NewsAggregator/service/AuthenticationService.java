package com.NewsAggregator.NewsAggregator.service;


import com.NewsAggregator.NewsAggregator.entity.User;
import com.NewsAggregator.NewsAggregator.entity.UserDTO;
import com.NewsAggregator.NewsAggregator.entity.VerificationToken;
import com.NewsAggregator.NewsAggregator.repository.UserRepository;
import com.NewsAggregator.NewsAggregator.repository.VerificationTokenRepository;
import com.NewsAggregator.NewsAggregator.util.JwtUtility;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // This is used to encode the password before saving it to the database
    //we use bycrypet liyberary

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public User registerUser(UserDTO userDTO) {

        User user = new User();
        user.setUsername(userDTO.getUserId());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(false);//after email verification, we will set it to true
        user.setRole("USER"); // Default role for new users
        return userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .disabled(!user.isEnabled()) // Set the account as disabled if isEnabled is false
                .build();

    }

    public void persistVerificationToken(User registeredUser, String generatedToken) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(registeredUser);
        verificationToken.setToken(generatedToken);
        verificationToken.setExpiryDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)); // Token valid for 24 hours
        verificationTokenRepository.save(verificationToken);

    }

    public boolean verifyRegistration(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken == null) {
            return false; // Token not found
        }

        long registeredExpiryDate = verificationToken.getExpiryDate().getTime();
        if(registeredExpiryDate < System.currentTimeMillis()) {
            return false; // Token has expired
        }

        return true;
    }

    public void enableUser(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken != null) {
            User user = verificationToken.getUser();
            user.setEnabled(true); // Enable the user account
            userRepository.save(user); // Save the updated user
            verificationTokenRepository.delete(verificationToken); // Optionally delete the token after use
        } else {
            throw new IllegalArgumentException("Invalid token");
        }
    }

    public String authenticateUser(String username, String password) {
        User registeredUser = userRepository.findByUsername(username);
        if (registeredUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        if(!registeredUser.isEnabled()){
            throw new IllegalStateException("User account is not enabled. Please verify your email.");
        }

        boolean isPasswordMatch = passwordEncoder.matches(password, registeredUser.getPassword());
        if (!isPasswordMatch) {
            throw new IllegalArgumentException("Invalid password");
        }

        return JwtUtility.generateToken(registeredUser.getUsername(), registeredUser.getRole());

    }
}
