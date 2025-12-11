package com.hexagonal.application.service;

import com.hexagonal.domain.model.Product;
import com.hexagonal.domain.port.in.ProductUseCase;
import com.hexagonal.domain.port.out.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Application service implementing the ProductUseCase
 * This contains the business logic and orchestrates domain operations
 */
@Service
@Transactional
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        if (product.getId() != null && productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Product with id " + product.getId() + " already exists");
        }
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.updateProduct(
                            product.getName(),
                            product.getDescription(),
                            product.getPrice(),
                            product.getStock()
                    );
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with id " + id + " not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public void decreaseStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + productId + " not found"));
        product.decreaseStock(quantity);
        productRepository.save(product);
    }

    @Override
    public void increaseStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + productId + " not found"));
        product.increaseStock(quantity);
        productRepository.save(product);
    }
}
