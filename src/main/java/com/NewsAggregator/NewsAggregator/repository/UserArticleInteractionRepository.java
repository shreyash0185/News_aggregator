package com.NewsAggregator.NewsAggregator.repository;

import com.NewsAggregator.NewsAggregator.entity.User;
import com.NewsAggregator.NewsAggregator.entity.UserArticleInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserArticleInteractionRepository extends JpaRepository<UserArticleInteraction, Long> {

    List<UserArticleInteraction> findByUserAndReadTrue(User user);
    List<UserArticleInteraction> findByUserAndFavoriteTrue(User user);
    Optional<UserArticleInteraction> findByUserAndArticleId(User user, String articleId);
}
