package com.NewsAggregator.NewsAggregator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class NewsPreference {

    @Id
    @GeneratedValue
    private Long id;

    private String category;

    @ManyToOne
    private User user;

}
