package com.example.hexagonal.adapter.in.web;

import com.example.hexagonal.domain.model.Product;
import com.example.hexagonal.domain.port.in.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Input Adapter (Primary Adapter / Driving Adapter)
 * REST API Controller that exposes HTTP endpoints.
 * Translates HTTP requests into domain operations using the ProductService port.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        Product product = new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock()
        );
        Product created = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(toResponse(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable String id,
            @RequestBody ProductRequest request) {
        try {
            Product product = new Product(
                    id,
                    request.getName(),
                    request.getDescription(),
                    request.getPrice(),
                    request.getStock()
            );
            Product updated = productService.updateProduct(id, product);
            return ResponseEntity.ok(toResponse(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/decrease-stock")
    public ResponseEntity<ProductResponse> decreaseStock(
            @PathVariable String id,
            @RequestParam Integer quantity) {
        try {
            Product updated = productService.decreaseStock(id, quantity);
            return ResponseEntity.ok(toResponse(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.isAvailable()
        );
    }
}
