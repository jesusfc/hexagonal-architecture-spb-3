package com.example.hexagonal.adapter.out.persistence;

import com.example.hexagonal.domain.model.Product;

/**
 * Mapper to convert between domain objects and persistence entities
 */
public class ProductMapper {

    public static ProductEntity toEntity(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }

    public static Product toDomain(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStock()
        );
    }
}
