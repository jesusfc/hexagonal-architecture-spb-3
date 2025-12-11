package com.example.hexagonal.domain.port.in;

import com.example.hexagonal.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Input Port (Primary Port / Driving Port)
 * Defines the use cases that the application provides.
 * This is implemented by the application layer and called by the adapters (controllers).
 */
public interface ProductService {
    
    /**
     * Create a new product
     */
    Product createProduct(Product product);
    
    /**
     * Get a product by its ID
     */
    Optional<Product> getProductById(String id);
    
    /**
     * Get all products
     */
    List<Product> getAllProducts();
    
    /**
     * Update an existing product
     */
    Product updateProduct(String id, Product product);
    
    /**
     * Delete a product by its ID
     */
    void deleteProduct(String id);
    
    /**
     * Decrease product stock
     */
    Product decreaseStock(String id, Integer quantity);
}
