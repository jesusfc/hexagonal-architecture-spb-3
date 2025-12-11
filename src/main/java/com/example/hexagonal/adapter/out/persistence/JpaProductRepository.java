package com.example.hexagonal.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository
 */
@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, String> {
}
