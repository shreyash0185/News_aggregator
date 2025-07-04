package com.NewsAggregator.NewsAggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${news.api.key}")
    private String API_KEY;


    private static final List<String> DEFAULT_TOPICS = List.of("technology", "sports", "politics");


    @Cacheable(value = "newsCache", key = "#q + '-' + #from + '-' + #to + '-' + #sortBy")
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

    @Cacheable(value = "newsCache", key = "#keyword")
    public Mono<String> searchNewsByKeyword(String keyword) {
        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("newsapi.org")
                        .path("/v2/everything")
                        .queryParam("q", keyword)
                        .queryParam("apiKey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    private Mono<String> fetchNewsFromApi(String keyword) {
        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("newsapi.org")
                        .path("/v2/everything")
                        .queryParam("q", keyword)
                        .queryParam("apiKey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }


    @Scheduled(fixedRate = 30 * 60 * 1000) // every 30 minutes
    public void refreshNewsCache() {
        DEFAULT_TOPICS.forEach(topic -> {
            fetchNewsFromApi(topic).subscribe(response -> {
                Cache cache = cacheManager.getCache("newsCache");
                if (cache != null) {
                    cache.put(topic, response);
                }
            });
        });
        System.out.println("News cache refreshed at " + LocalDateTime.now());
    }

}
