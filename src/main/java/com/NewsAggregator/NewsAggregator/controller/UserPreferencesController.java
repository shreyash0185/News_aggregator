package com.NewsAggregator.NewsAggregator.controller;

import com.NewsAggregator.NewsAggregator.entity.UserPreferences;
import com.NewsAggregator.NewsAggregator.entity.UserPreferencesDTO;
import com.NewsAggregator.NewsAggregator.service.UserPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserPreferencesController {

    @Autowired
    private UserPreferencesService userPreferencesService;

    @GetMapping("/api/getPreferences")
    public ResponseEntity<UserPreferences> getUserPreferences(@RequestParam ("userId") Long userId) {
       return  ResponseEntity.ok(userPreferencesService.getUserPreferences(userId));
    }

    @PostMapping("/api/updatePreferences")
    public ResponseEntity<UserPreferences> updateUserPreferences(@RequestHeader("Authorization") String authHeader,@RequestBody UserPreferencesDTO preferences) {
        return ResponseEntity.ok(userPreferencesService.updateUserPreferences(authHeader, preferences));
    }

}
