package com.NewsAggregator.NewsAggregator.entity;

import jakarta.persistence.*;

@Entity
public class UserArticleInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String articleId;

    private boolean read;
    private boolean favorite;

    @ManyToOne
    private User user;

    public UserArticleInteraction(Long id, boolean read, String articleId, boolean favorite, User user) {
        this.id = id;
        this.read = read;
        this.articleId = articleId;
        this.favorite = favorite;
        this.user = user;
    }

    public UserArticleInteraction() {
        // Default constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
