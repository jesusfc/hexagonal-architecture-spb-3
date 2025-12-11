package com.example.hexagonal.application.service;

import com.example.hexagonal.domain.model.Product;
import com.example.hexagonal.domain.port.out.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void shouldCreateProduct() {
        Product product = new Product("Laptop", "Description", new BigDecimal("999.99"), 10);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product created = productService.createProduct(product);

        assertNotNull(created);
        assertEquals("Laptop", created.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void shouldGetProductById() {
        String productId = "123";
        Product product = new Product(productId, "Laptop", "Description", new BigDecimal("999.99"), 10);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> found = productService.getProductById(productId);

        assertTrue(found.isPresent());
        assertEquals(productId, found.get().getId());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void shouldGetAllProducts() {
        Product product1 = new Product("Product1", "Desc1", new BigDecimal("100.00"), 5);
        Product product2 = new Product("Product2", "Desc2", new BigDecimal("200.00"), 10);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void shouldUpdateProduct() {
        String productId = "123";
        Product existingProduct = new Product(productId, "Old Name", "Old Desc", new BigDecimal("100.00"), 5);
        Product updatedData = new Product(productId, "New Name", "New Desc", new BigDecimal("150.00"), 10);
        
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        Product result = productService.updateProduct(productId, updatedData);

        assertEquals("New Name", result.getName());
        assertEquals("New Desc", result.getDescription());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        String productId = "999";
        Product product = new Product("Product", "Desc", new BigDecimal("100.00"), 5);
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(productId, product);
        });
    }

    @Test
    void shouldDeleteProduct() {
        String productId = "123";
        when(productRepository.existsById(productId)).thenReturn(true);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentProduct() {
        String productId = "999";
        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            productService.deleteProduct(productId);
        });
    }

    @Test
    void shouldDecreaseStock() {
        String productId = "123";
        Product product = new Product(productId, "Product", "Desc", new BigDecimal("100.00"), 10);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.decreaseStock(productId, 3);

        assertEquals(7, result.getStock());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenDecreasingStockOfNonExistentProduct() {
        String productId = "999";
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            productService.decreaseStock(productId, 5);
        });
    }
}
