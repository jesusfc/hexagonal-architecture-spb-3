package com.hexagonal.domain.port.out;

import com.hexagonal.domain.model.Product;
import java.util.List;
import java.util.Optional;

/**
 * Output port (driven port) for Product persistence
 * This defines the contract for data persistence that will be implemented by infrastructure adapters
 */
public interface ProductRepository {
    
    Product save(Product product);
    
    Optional<Product> findById(Long id);
    
    List<Product> findAll();
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
}
