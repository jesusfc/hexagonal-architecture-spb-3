package com.hexagonal.infrastructure.adapter.in.rest;

import com.hexagonal.domain.model.Product;
import com.hexagonal.domain.port.in.ProductUseCase;
import com.hexagonal.infrastructure.adapter.in.rest.dto.ProductRequest;
import com.hexagonal.infrastructure.adapter.in.rest.dto.ProductResponse;
import com.hexagonal.infrastructure.adapter.in.rest.dto.StockRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller (Input Adapter)
 * This adapter receives HTTP requests and delegates to the use case
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = mapToProduct(request);
        Product createdProduct = productUseCase.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(createdProduct));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return productUseCase.getProductById(id)
                .map(product -> ResponseEntity.ok(mapToResponse(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productUseCase.getAllProducts().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        try {
            Product product = mapToProduct(request);
            Product updatedProduct = productUseCase.updateProduct(id, product);
            return ResponseEntity.ok(mapToResponse(updatedProduct));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productUseCase.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/decrease-stock")
    public ResponseEntity<Void> decreaseStock(
            @PathVariable Long id,
            @Valid @RequestBody StockRequest request) {
        try {
            productUseCase.decreaseStock(id, request.getQuantity());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/increase-stock")
    public ResponseEntity<Void> increaseStock(
            @PathVariable Long id,
            @Valid @RequestBody StockRequest request) {
        try {
            productUseCase.increaseStock(id, request.getQuantity());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private Product mapToProduct(ProductRequest request) {
        return new Product(
                null,
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock()
        );
    }

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
