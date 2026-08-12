package com.ayub.assessment.api.search;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final ElasticsearchConfig elasticsearchConfig;

    public SearchController(ElasticsearchConfig elasticsearchConfig) {
        this.elasticsearchConfig = elasticsearchConfig;
    }

    @GetMapping("/products")
    public List<Map<String, Object>> searchProducts(@RequestParam String q) {
        return elasticsearchConfig.searchProducts(q);
    }
}
