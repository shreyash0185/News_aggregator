package com.NewsAggregator.NewsAggregator.repository;

import com.NewsAggregator.NewsAggregator.entity.User;
import com.NewsAggregator.NewsAggregator.entity.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {

    UserPreferences findByUser(User user);

    UserPreferences findByUserId(Long userId);

    boolean existsByUser(User user);
}
