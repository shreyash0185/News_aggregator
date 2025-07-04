package com.NewsAggregator.NewsAggregator.controller;

import com.NewsAggregator.NewsAggregator.entity.UserArticleInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.NewsAggregator.NewsAggregator.service.ArticleInteractionService;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class ArticleInteractionController {

    @Autowired
    private ArticleInteractionService articleInteractionService;

    @PostMapping("/{id}/read")
    public ResponseEntity<String> markAsRead(@PathVariable("id") String articleId, @RequestParam Long userId) {
        return ResponseEntity.ok(articleInteractionService.markAsRead(articleId, userId));
    }

    @PostMapping("/{id}/favorite")
    public ResponseEntity<String> markAsFavorite(@PathVariable("id") String articleId, @RequestParam Long userId) {
        return ResponseEntity.ok(articleInteractionService.markAsFavorite(articleId, userId));
    }

    @GetMapping("/read")
    public ResponseEntity<List<UserArticleInteraction>> getRead(@RequestParam Long userId) {
        return ResponseEntity.ok(articleInteractionService.getReadArticles(userId));
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<UserArticleInteraction>> getFavorites(@RequestParam Long userId) {
        return ResponseEntity.ok(articleInteractionService.getFavoriteArticles(userId));
    }

}
