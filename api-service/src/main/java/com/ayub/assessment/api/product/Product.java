package com.ayub.assessment.api.product;

import java.io.Serializable;
import java.math.BigDecimal;

import com.ayub.assessment.api.utility.EncryptedBigDecimalConverter;
import com.ayub.assessment.api.utility.EncryptedStringConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 512)
    @Convert(converter = EncryptedStringConverter.class)
    private String name;

    @Column(nullable = false, length = 512)
    @Convert(converter = EncryptedStringConverter.class)
    private String category;

    @Column(nullable = false, length = 512)
    @Convert(converter = EncryptedBigDecimalConverter.class)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean active;

    public Product() {
    }

    public Product(
            Long id,
            String name,
            String category,
            BigDecimal price,
            boolean active) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}