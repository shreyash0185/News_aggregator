package com.NewsAggregator.NewsAggregator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserPreferencesDTO {

    private String preferredLanguage;
    private String preferredRegion;
    private String preferredCategory;
    private Long userId;

    public UserPreferencesDTO(String preferredLanguage, String preferredRegion, String preferredCategory, Long userId) {
        this.preferredLanguage = preferredLanguage;
        this.preferredRegion = preferredRegion;
        this.preferredCategory = preferredCategory;
        this.userId = userId;
    }

    public UserPreferencesDTO() {
        // Default constructor
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getPreferredRegion() {
        return preferredRegion;
    }

    public void setPreferredRegion(String preferredRegion) {
        this.preferredRegion = preferredRegion;
    }

    public String getPreferredCategory() {
        return preferredCategory;
    }

    public void setPreferredCategory(String preferredCategory) {
        this.preferredCategory = preferredCategory;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
