package com.ayub.assessment.api.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/api/search")
public class SearchController {
    private final ElasticsearchClient client;
    public SearchController(ElasticsearchClient client){this.client=client;}
    @GetMapping("/products")
    public List<Map<String,Object>> search(@RequestParam String q) throws IOException {
        SearchResponse<Map> response=client.search(s->s.index("products").query(x->x.multiMatch(m->m.query(q).fields("name","category"))),Map.class);
        return response.hits().hits().stream().map(h->h.source()).toList();
    }
}
