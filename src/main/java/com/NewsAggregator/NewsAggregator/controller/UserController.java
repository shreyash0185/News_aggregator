package com.NewsAggregator.NewsAggregator.controller;


import com.NewsAggregator.NewsAggregator.entity.User;
import com.NewsAggregator.NewsAggregator.entity.UserDTO;
import com.NewsAggregator.NewsAggregator.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public User registerUser(@RequestBody  @Valid UserDTO userDTO){
        User registeredUser = authenticationService.registerUser(userDTO);
        String generatedToken = UUID.randomUUID().toString();
        String applicationUrl = "http://localhost:8080/verify?token=" + generatedToken;
        System.out.println("Verification link: " + applicationUrl); //send the mail for this as wedont have SMTP server for nw -> we nned SMTp server to send the mail
        authenticationService.persistVerificationToken(registeredUser, generatedToken);
        return registeredUser;
    }

    @PostMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        boolean isVerified = authenticationService.verifyRegistration(token);
        if (isVerified) {
            authenticationService.enableUser(token);
            return "User registration verified successfully.";
        } else {
            return "Invalid or expired verification token.";
        }
    }

    @PostMapping("/login")
    public String signIn(@RequestParam("username") String username, @RequestParam("password") String password) {
        return authenticationService.authenticateUser(username, password);
    }


    @GetMapping("/hellow")
    public String helloWorld() {
        return "Hello World";
    }


    @PostMapping("/test")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public String testEndpoint(@RequestBody String data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Collection<?  extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("User Authorities: " + authorities);
        System.out.println("Authenticated User: " + username);
//Bad Code to check here
//        if(authorities[0].getAuthority().equals("ROLE_ADMIN")){
//            System.out.println("Admin access granted");
//        } else {
//            System.out.println("User access granted");
//        }
        return "Test endpoint received data: " + data;
    }


}
