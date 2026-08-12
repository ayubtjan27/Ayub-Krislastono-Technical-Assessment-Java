package com.ayub.assessment.api.product;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity @Table(name="products")
public class Product implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private boolean active;
    public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
    public String getCategory(){return category;} public void setCategory(String v){category=v;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
    public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
}
