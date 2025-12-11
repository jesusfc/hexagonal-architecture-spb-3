package com.example.hexagonal.application.service;

import com.example.hexagonal.domain.model.Product;
import com.example.hexagonal.domain.port.in.ProductService;
import com.example.hexagonal.domain.port.out.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application Service (Use Case Implementation)
 * Implements the input port (ProductService) and uses the output port (ProductRepository).
 * Contains the business logic and orchestrates the domain objects.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(String id, Product product) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.updateStock(product.getStock());
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    @Override
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public Product decreaseStock(String id, Integer quantity) {
        return productRepository.findById(id)
                .map(product -> {
                    product.decreaseStock(quantity);
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }
}
