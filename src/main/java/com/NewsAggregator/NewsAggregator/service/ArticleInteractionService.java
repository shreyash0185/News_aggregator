package com.NewsAggregator.NewsAggregator.service;

import com.NewsAggregator.NewsAggregator.entity.User;
import com.NewsAggregator.NewsAggregator.entity.UserArticleInteraction;
import com.NewsAggregator.NewsAggregator.repository.UserArticleInteractionRepository;
import com.NewsAggregator.NewsAggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleInteractionService {

    @Autowired
    private UserArticleInteractionRepository userArticleInteractionRepository;

    @Autowired
    private UserRepository userRepository;

    public String markAsRead(String articleId, Long userId) {
        return saveInteraction(articleId, userId, true, false);
    }

    public String markAsFavorite(String articleId, Long userId) {
        return saveInteraction(articleId, userId, false, true);
    }

    private String saveInteraction(String articleId, Long userId, boolean markRead, boolean markFavorite) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserArticleInteraction interaction = userArticleInteractionRepository.findByUserAndArticleId(user, articleId)
                .orElse(new UserArticleInteraction());

        interaction.setUser(user);
        interaction.setArticleId(articleId);
        if (markRead) interaction.setRead(true);
        if (markFavorite) interaction.setFavorite(true);

        userArticleInteractionRepository.save(interaction);
        return "Success";
    }

    public List<UserArticleInteraction> getReadArticles(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return userArticleInteractionRepository.findByUserAndReadTrue(user);
    }

    public List<UserArticleInteraction> getFavoriteArticles(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return userArticleInteractionRepository.findByUserAndFavoriteTrue(user);
    }
}
