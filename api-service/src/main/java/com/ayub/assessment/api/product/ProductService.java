package com.ayub.assessment.api.product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final KafkaTemplate<String, Object> kafka;

    public ProductService(
            ProductRepository repository,
            KafkaTemplate<String, Object> kafka) {

        this.repository = repository;
        this.kafka = kafka;
    }

    @Cacheable(cacheNames = "products", key = "#id")
    public Product find(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    @CacheEvict(cacheNames = "products", key = "#result.id")
    public Product save(Product product) {

        Product saved = repository.save(product);

        kafka.send(
                "product-events",
                String.valueOf(saved.getId()),
                new ProductEvent(
                        saved.getId(),
                        saved.getName(),
                        saved.getCategory(),
                        saved.getPrice()));

        return saved;
    }

    public List<AnalyticsRow> analytics(
            BigDecimal min,
            BigDecimal max) {

        return repository.advancedAnalytics(min, max)
                .stream()
                .map(r -> new AnalyticsRow(
                        ((Number) r[0]).longValue(),
                        (String) r[1],
                        (String) r[2],
                        (BigDecimal) r[3],
                        ((Number) r[4]).longValue()))
                .toList();
    }

    public record AnalyticsRow(
            Long id,
            String name,
            String category,
            BigDecimal price,
            Long totalQuantity) {
    }

    public record ProductEvent(
            Long id,
            String name,
            String category,
            BigDecimal price) {
    }
}