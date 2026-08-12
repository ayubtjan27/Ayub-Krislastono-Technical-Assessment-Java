package com.ayub.assessment.api.search;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final ElasticsearchClient elasticsearchClient;

    public SearchController(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @GetMapping("/products")
    public List<Map<String, Object>> searchProducts(@RequestParam String q) throws IOException {
        SearchResponse<Map> response = elasticsearchClient.search(s -> s
                .index("products")
                .query(Query.of(query -> query
                        .multiMatch(multiMatch -> multiMatch
                                .query(q)
                                .fields("name", "category")
                        )
                )),
                Map.class
        );

        return response.hits().hits().stream()
                .map(hit -> (Map<String, Object>) hit.source())
                .toList();
    }
}