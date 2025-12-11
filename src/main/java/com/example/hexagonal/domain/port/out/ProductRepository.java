package com.example.hexagonal.domain.port.out;

import com.example.hexagonal.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Output Port (Secondary Port / Driven Port)
 * Defines the contract for persistence operations.
 * This is implemented by the adapters (persistence layer).
 */
public interface ProductRepository {
    
    /**
     * Save a product
     */
    Product save(Product product);
    
    /**
     * Find a product by its ID
     */
    Optional<Product> findById(String id);
    
    /**
     * Find all products
     */
    List<Product> findAll();
    
    /**
     * Delete a product by its ID
     */
    void deleteById(String id);
    
    /**
     * Check if a product exists by ID
     */
    boolean existsById(String id);
}
