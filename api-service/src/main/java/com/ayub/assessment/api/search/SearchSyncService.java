package com.ayub.assessment.api.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayub.assessment.api.product.Product;
import com.ayub.assessment.api.product.ProductRepository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;

@Service
public class SearchSyncService {

    private final ProductRepository productRepository;
    private final ElasticsearchClient elasticsearchClient;

    public SearchSyncService(
            ProductRepository productRepository,
            ElasticsearchClient elasticsearchClient) {

        this.productRepository = productRepository;
        this.elasticsearchClient = elasticsearchClient;
    }

    @Transactional(readOnly = true)
    public int syncProducts() throws IOException {

        List<Product> products =
                productRepository.findAll();

        if (elasticsearchClient.indices()
                .exists(e -> e.index("products"))
                .value()) {

            elasticsearchClient.indices()
                    .delete(d -> d.index("products"));
        }

        elasticsearchClient.indices()
                .create(c -> c.index("products"));

        if (products.isEmpty()) {
            return 0;
        }

        List<BulkOperation> operations =
                new ArrayList<>();

        for (Product product : products) {

            ProductDocument document =
                    new ProductDocument(
                            product.getId(),
                            product.getName(),
                            product.getCategory(),
                            product.getPrice());

            operations.add(
                    BulkOperation.of(operation ->
                            operation.index(index ->
                                    index
                                            .index("products")
                                            .id(String.valueOf(product.getId()))
                                            .document(document)
                            )
                    )
            );
        }

        BulkResponse response =
                elasticsearchClient.bulk(
                        BulkRequest.of(request ->
                                request
                                        .operations(operations)
                        )
                );

        if (response.errors()) {
            throw new IOException(
                    "Elasticsearch synchronization failed"
            );
        }

        return products.size();
    }

    public record ProductDocument(
            Long id,
            String name,
            String category,
            java.math.BigDecimal price) {
    }
}