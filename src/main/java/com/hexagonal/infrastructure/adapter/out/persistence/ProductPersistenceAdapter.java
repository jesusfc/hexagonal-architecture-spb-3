package com.hexagonal.infrastructure.adapter.out.persistence;

import com.hexagonal.domain.model.Product;
import com.hexagonal.domain.port.out.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Persistence Adapter (Output Adapter)
 * Implements the domain port and adapts it to Spring Data JPA
 */
@Component
public class ProductPersistenceAdapter implements ProductRepository {

    private final SpringDataProductRepository springDataProductRepository;

    public ProductPersistenceAdapter(SpringDataProductRepository springDataProductRepository) {
        this.springDataProductRepository = springDataProductRepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = mapToEntity(product);
        ProductEntity savedEntity = springDataProductRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return springDataProductRepository.findById(id)
                .map(this::mapToDomain);
    }

    @Override
    public List<Product> findAll() {
        return springDataProductRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataProductRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataProductRepository.existsById(id);
    }

    private ProductEntity mapToEntity(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    private Product mapToDomain(ProductEntity entity) {
        Product product = new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStock()
        );
        product.setCreatedAt(entity.getCreatedAt());
        product.setUpdatedAt(entity.getUpdatedAt());
        return product;
    }
}
