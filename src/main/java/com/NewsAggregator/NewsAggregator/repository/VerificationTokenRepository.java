package com.NewsAggregator.NewsAggregator.repository;


import com.NewsAggregator.NewsAggregator.entity.User;
import com.NewsAggregator.NewsAggregator.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    void deleteByUser(User user);
}
