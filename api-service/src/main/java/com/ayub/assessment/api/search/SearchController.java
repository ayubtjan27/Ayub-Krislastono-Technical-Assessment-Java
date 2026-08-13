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
    private final SearchSyncService searchSyncService;

    public SearchController(
            ElasticsearchClient elasticsearchClient,
            SearchSyncService searchSyncService) {

        this.elasticsearchClient = elasticsearchClient;
        this.searchSyncService = searchSyncService;
    }

    @GetMapping("/products")
    public List<Map<String, Object>> searchProducts(
                @RequestParam(required = false) String field,
                @RequestParam String keyword) throws IOException {
        
        SearchResponse<Map> response;

        if (field == null || field.isBlank()) {
            response = elasticsearchClient.search(s -> s
                    .index("products")
                    .size(100)
                    .query(Query.of(query -> query
                            .multiMatch(multiMatch -> multiMatch
                                    .query(keyword)
                                    .fields("name", "category")
                            )
                    )),
                    Map.class
            );

            if (!response.hits().hits().isEmpty()) {
                return response.hits().hits().stream()
                        .map(hit -> (Map<String, Object>) hit.source())
                        .toList();
            }

            response = elasticsearchClient.search(s -> s
                    .index("products")
                    .size(100)
                    .query(Query.of(query -> query
                            .queryString(queryString -> queryString
                                    .query("*" + keyword + "*")
                                    .fields("name", "category")
                            )
                    )),
                    Map.class
            );

        } else {
            if (!field.equals("name")
                    && !field.equals("category")) {

                throw new IllegalArgumentException(
                        "field must be name or category"
                );
            }

            response = elasticsearchClient.search(s -> s
                    .index("products")
                    .size(100)
                    .query(Query.of(query -> query
                            .match(match -> match
                                    .field(field)
                                    .query(keyword)
                            )
                    )),
                    Map.class
            );
        }

        return response.hits().hits().stream()
                .map(hit -> (Map<String, Object>) hit.source())
                .toList();
    }

    @GetMapping("/sync")
    public Map<String, Object> syncProducts()
            throws IOException {

        int total =
                searchSyncService.syncProducts();

        return Map.of(
                "status", "SUCCESS",
                "index", "products",
                "total", total
        );
    }
}