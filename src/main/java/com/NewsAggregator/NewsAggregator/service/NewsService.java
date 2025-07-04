package com.NewsAggregator.NewsAggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NewsService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    private final String API_KEY = "a836c8875eeb48d597ff649a874a249c";


    public Mono<String> searchNews(String q, String from, String to, String sortBy) {

        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("newsapi.org")
                        .path("/v2/everything")
                        .queryParam("q", q)
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .queryParam("sortBy", sortBy)
                        .queryParam("apiKey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(String.class);

    }
}
