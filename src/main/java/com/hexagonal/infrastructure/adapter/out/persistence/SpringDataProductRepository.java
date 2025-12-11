package com.hexagonal.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository interface
 * This provides basic CRUD operations out of the box
 */
@Repository
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {
}
