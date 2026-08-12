package com.ayub.assessment.stream.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.ayub.assessment.stream.event.ProductEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ProductStreamProcessor {
    private final ElasticsearchClient client;
    public ProductStreamProcessor(ElasticsearchClient client){this.client=client;}
    @KafkaListener(topics="product-events", groupId="product-search-indexer")
    public void consume(ProductEvent event) throws IOException {
        client.index(IndexRequest.of(i->i.index("products").id(String.valueOf(event.id())).document(event)));
    }
}
