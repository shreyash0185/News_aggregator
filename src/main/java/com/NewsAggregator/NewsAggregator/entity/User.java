package com.NewsAggregator.NewsAggregator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "master_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String role;
    private boolean isEnabled;

//    private String verificationToken;

    public User(Long userId, boolean isEnabled, String role, String password, String username) {
        this.id = userId;
        this.isEnabled = isEnabled;
        this.role = role;
        this.password = password;
        this.username = username;
    }

    // Default constructor
    public User() {
        // Default constructor
    }

    //User constructor for creating a new user
    public User(User user) {
        this.id = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.isEnabled = user.isEnabled();
    }

    public Long getUserId() {
        return id;
    }

    public void setUserId(Long userId) {
        this.id = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
