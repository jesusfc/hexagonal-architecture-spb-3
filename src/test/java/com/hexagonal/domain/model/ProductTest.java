package com.hexagonal.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Product domain entity
 */
class ProductTest {

    @Test
    void testCreateProduct() {
        Product product = new Product(1L, "Laptop", "Gaming Laptop", 1299.99, 10);
        
        assertNotNull(product);
        assertEquals(1L, product.getId());
        assertEquals("Laptop", product.getName());
        assertEquals("Gaming Laptop", product.getDescription());
        assertEquals(1299.99, product.getPrice());
        assertEquals(10, product.getStock());
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product(1L, "Laptop", "Gaming Laptop", 1299.99, 10);
        LocalDateTime initialUpdatedAt = product.getUpdatedAt();
        
        // Wait a bit to ensure timestamp changes
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        product.updateProduct("Gaming Laptop Pro", "High-end Gaming Laptop", 1599.99, 5);
        
        assertEquals("Gaming Laptop Pro", product.getName());
        assertEquals("High-end Gaming Laptop", product.getDescription());
        assertEquals(1599.99, product.getPrice());
        assertEquals(5, product.getStock());
        assertTrue(product.getUpdatedAt().isAfter(initialUpdatedAt));
    }

    @Test
    void testDecreaseStock() {
        Product product = new Product(1L, "Laptop", "Gaming Laptop", 1299.99, 10);
        
        product.decreaseStock(3);
        
        assertEquals(7, product.getStock());
    }

    @Test
    void testDecreaseStockInsufficientStock() {
        Product product = new Product(1L, "Laptop", "Gaming Laptop", 1299.99, 5);
        
        assertThrows(IllegalArgumentException.class, () -> product.decreaseStock(10));
    }

    @Test
    void testIncreaseStock() {
        Product product = new Product(1L, "Laptop", "Gaming Laptop", 1299.99, 10);
        
        product.increaseStock(5);
        
        assertEquals(15, product.getStock());
    }

    @Test
    void testProductEquality() {
        Product product1 = new Product(1L, "Laptop", "Gaming Laptop", 1299.99, 10);
        Product product2 = new Product(1L, "Different Name", "Different Desc", 999.99, 5);
        Product product3 = new Product(2L, "Laptop", "Gaming Laptop", 1299.99, 10);
        
        assertEquals(product1, product2); // Same ID
        assertNotEquals(product1, product3); // Different ID
    }
}
