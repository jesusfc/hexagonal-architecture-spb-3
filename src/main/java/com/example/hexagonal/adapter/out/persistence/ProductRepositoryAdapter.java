package com.example.hexagonal.adapter.out.persistence;

import com.example.hexagonal.domain.model.Product;
import com.example.hexagonal.domain.port.out.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Output Adapter (Secondary Adapter / Driven Adapter)
 * Implements the ProductRepository port using JPA.
 * Translates domain operations into database operations.
 */
@Component
public class ProductRepositoryAdapter implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;

    public ProductRepositoryAdapter(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = ProductMapper.toEntity(product);
        ProductEntity saved = jpaProductRepository.save(entity);
        return ProductMapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(String id) {
        return jpaProductRepository.findById(id)
                .map(ProductMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaProductRepository.findAll().stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        jpaProductRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return jpaProductRepository.existsById(id);
    }
}
