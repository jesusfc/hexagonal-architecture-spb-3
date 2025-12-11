package com.example.hexagonal.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * JPA Entity for Product
 * This is part of the persistence adapter and should not be exposed to the domain.
 */
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;

    public ProductEntity() {
    }

    public ProductEntity(String id, String name, String description, BigDecimal price, Integer stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
