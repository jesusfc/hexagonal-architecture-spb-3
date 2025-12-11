package com.example.hexagonal.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Domain Entity: Product
 * This is a pure domain object with no dependencies on frameworks or infrastructure.
 */
public class Product {
    private final String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;

    public Product(String id, String name, String description, BigDecimal price, Integer stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        validate();
    }

    public Product(String name, String description, BigDecimal price, Integer stock) {
        this(UUID.randomUUID().toString(), name, description, price, stock);
    }

    private void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative");
        }
    }

    public void updateStock(Integer newStock) {
        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        this.stock = newStock;
    }

    public void decreaseStock(Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (this.stock < quantity) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        this.stock -= quantity;
    }

    public boolean isAvailable() {
        return stock > 0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
        validate();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        validate();
    }
}
