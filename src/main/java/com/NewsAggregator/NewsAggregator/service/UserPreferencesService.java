package com.NewsAggregator.NewsAggregator.service;

import com.NewsAggregator.NewsAggregator.entity.User;
import com.NewsAggregator.NewsAggregator.entity.UserPreferences;
import com.NewsAggregator.NewsAggregator.entity.UserPreferencesDTO;
import com.NewsAggregator.NewsAggregator.repository.UserPreferencesRepository;
import com.NewsAggregator.NewsAggregator.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
public class UserPreferencesService {

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;


    public UserPreferences updateUserPreferences(String authHeader ,UserPreferencesDTO preferences) {
        if (preferences == null || preferences.getUserId() == null) {
            return null;
        }


       User user = JwtUtility.getUserFromToken(authHeader);
        UserPreferences userPreferences = userPreferencesRepository.findByUserId(preferences.getUserId());
        if (userPreferences != null) {
            userPreferences.setPreferredCategory(preferences.getPreferredCategory());
            userPreferences.setPreferredRegion(preferences.getPreferredRegion());
            userPreferences.setPreferredLanguage(preferences.getPreferredLanguage());
            return userPreferencesRepository.save(userPreferences);
        } else {
            UserPreferences newUserPreferences = new UserPreferences(
                    preferences.getPreferredLanguage(),
                    preferences.getPreferredRegion(),
                    preferences.getPreferredCategory(),
                    user
            );
            return userPreferencesRepository.save(newUserPreferences);

        }

    }



    public UserPreferences  getUserPreferences(Long userId) {

        if (userId == null) {
            return null;
        }

        UserPreferences userPreferences = userPreferencesRepository.findByUserId(userId);
        if (userPreferences != null) {
            return userPreferences;
        } else {
            return null;
        }
    }
}
