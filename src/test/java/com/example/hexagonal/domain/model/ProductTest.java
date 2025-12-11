package com.example.hexagonal.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldCreateProductWithValidData() {
        Product product = new Product("Laptop", "High performance laptop", new BigDecimal("999.99"), 10);
        
        assertNotNull(product.getId());
        assertEquals("Laptop", product.getName());
        assertEquals("High performance laptop", product.getDescription());
        assertEquals(new BigDecimal("999.99"), product.getPrice());
        assertEquals(10, product.getStock());
        assertTrue(product.isAvailable());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product("", "Description", new BigDecimal("100.00"), 5);
        });
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product("Product", "Description", new BigDecimal("-10.00"), 5);
        });
    }

    @Test
    void shouldThrowExceptionWhenStockIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product("Product", "Description", new BigDecimal("100.00"), -1);
        });
    }

    @Test
    void shouldDecreaseStockCorrectly() {
        Product product = new Product("Product", "Description", new BigDecimal("100.00"), 10);
        
        product.decreaseStock(3);
        
        assertEquals(7, product.getStock());
    }

    @Test
    void shouldThrowExceptionWhenDecreasingStockBeyondAvailable() {
        Product product = new Product("Product", "Description", new BigDecimal("100.00"), 5);
        
        assertThrows(IllegalArgumentException.class, () -> {
            product.decreaseStock(10);
        });
    }

    @Test
    void shouldUpdateStockCorrectly() {
        Product product = new Product("Product", "Description", new BigDecimal("100.00"), 10);
        
        product.updateStock(20);
        
        assertEquals(20, product.getStock());
    }

    @Test
    void shouldReturnFalseWhenProductIsNotAvailable() {
        Product product = new Product("Product", "Description", new BigDecimal("100.00"), 0);
        
        assertFalse(product.isAvailable());
    }
}
