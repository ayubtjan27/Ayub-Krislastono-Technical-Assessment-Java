package com.ayub.assessment.api.product;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Cacheable(
            cacheNames = "products",
            key = "#id")
    public Product find(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    @Transactional
    @CacheEvict(
            cacheNames = "products",
            key = "#result.id")
    public Product save(Product product) {

        Product saved =
                repository.save(product);

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

    @Transactional(readOnly = true)
    public List<AnalyticsRow> analytics(
            BigDecimal min,
            BigDecimal max) {

        List<Product> products =
                repository.findActiveProducts();

        return products.stream()
                .filter(product ->
                        product.getPrice() != null
                                && product.getPrice().compareTo(min) >= 0
                                && product.getPrice().compareTo(max) <= 0)
                .collect(Collectors.groupingBy(
                        Product::getCategory))
                .values()
                .stream()
                .flatMap(categoryProducts ->
                        categoryProducts.stream()
                                .sorted(Comparator.comparing(
                                        Product::getPrice).reversed())
                                .limit(3))
                .map(product -> new AnalyticsRow(
                        product.getId(),
                        product.getName(),
                        product.getCategory(),
                        product.getPrice(),
                        0L))
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