package com.NewsAggregator.NewsAggregator.controller;

import com.NewsAggregator.NewsAggregator.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/search")
    public Mono<String> searchNews(@RequestParam String q,
                                   @RequestParam String from,
                                   @RequestParam String to,
                                   @RequestParam(defaultValue = "popularity") String sortBy) {

        return newsService.searchNews(q, from, to, sortBy);
    }


}
