package com.hexagonal.infrastructure.adapter.in.rest;

import com.hexagonal.domain.model.Product;
import com.hexagonal.domain.port.in.ProductUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for ProductController
 */
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductUseCase productUseCase;

    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product(1L, "Laptop", "Gaming Laptop", 1299.99, 10);
        when(productUseCase.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Laptop\",\"description\":\"Gaming Laptop\",\"price\":1299.99,\"stock\":10}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(1299.99));
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = new Product(1L, "Laptop", "Gaming Laptop", 1299.99, 10);
        when(productUseCase.getProductById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void testGetProductByIdNotFound() throws Exception {
        when(productUseCase.getProductById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllProducts() throws Exception {
        Product product1 = new Product(1L, "Laptop", "Gaming Laptop", 1299.99, 10);
        Product product2 = new Product(2L, "Mouse", "Gaming Mouse", 49.99, 20);
        when(productUseCase.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[1].name").value("Mouse"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product product = new Product(1L, "Gaming Laptop Pro", "Updated Description", 1599.99, 8);
        when(productUseCase.updateProduct(eq(1L), any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Gaming Laptop Pro\",\"description\":\"Updated Description\",\"price\":1599.99,\"stock\":8}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gaming Laptop Pro"))
                .andExpect(jsonPath("$.price").value(1599.99));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateProductWithInvalidData() throws Exception {
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"price\":-100,\"stock\":-5}"))
                .andExpect(status().isBadRequest());
    }
}
