package com.ayub.assessment.api.product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

    @Query(value = """
        WITH ranked AS (
            SELECT
                p.id,
                p.name,
                p.category,
                p.price,
                SUM(o.quantity) total_quantity,
                ROW_NUMBER() OVER (
                    PARTITION BY p.category
                    ORDER BY SUM(o.quantity) DESC
                ) rank_no
            FROM products p
            JOIN order_items o
                ON o.product_id = p.id
            WHERE p.active = true
              AND p.price BETWEEN :minPrice AND :maxPrice
            GROUP BY
                p.id,
                p.name,
                p.category,
                p.price
        )
        SELECT
            id,
            name,
            category,
            price,
            total_quantity
        FROM ranked
        WHERE rank_no <= 3
        ORDER BY total_quantity DESC
        """, nativeQuery = true)

    List<Object[]> advancedAnalytics(
            BigDecimal minPrice,
            BigDecimal maxPrice);

    @Query("""
        SELECT p
        FROM Product p
        WHERE p.active = true
        """)
    List<Product> findActiveProducts();
}