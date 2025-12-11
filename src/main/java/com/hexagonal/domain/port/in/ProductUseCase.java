package com.hexagonal.domain.port.in;

import com.hexagonal.domain.model.Product;
import java.util.List;
import java.util.Optional;

/**
 * Input port (driving port) for Product use cases
 * This defines the operations that can be performed from outside (e.g., REST controllers)
 */
public interface ProductUseCase {
    
    Product createProduct(Product product);
    
    Optional<Product> getProductById(Long id);
    
    List<Product> getAllProducts();
    
    Product updateProduct(Long id, Product product);
    
    void deleteProduct(Long id);
    
    void decreaseStock(Long productId, Integer quantity);
    
    void increaseStock(Long productId, Integer quantity);
}
