package com.example.hexagonal.adapter.in.web;

import com.example.hexagonal.domain.model.Product;
import com.example.hexagonal.domain.port.in.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest request = new ProductRequest("Laptop", "High performance", new BigDecimal("999.99"), 10);
        Product product = new Product("123", "Laptop", "High performance", new BigDecimal("999.99"), 10);
        
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(999.99))
                .andExpect(jsonPath("$.stock").value(10));
    }

    @Test
    void shouldGetProductById() throws Exception {
        String productId = "123";
        Product product = new Product(productId, "Laptop", "Description", new BigDecimal("999.99"), 10);
        
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
        when(productService.getProductById("999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/{id}", "999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        Product product1 = new Product("1", "Product1", "Desc1", new BigDecimal("100.00"), 5);
        Product product2 = new Product("2", "Product2", "Desc2", new BigDecimal("200.00"), 10);
        
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Product1"))
                .andExpect(jsonPath("$[1].name").value("Product2"));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        String productId = "123";
        ProductRequest request = new ProductRequest("Updated Name", "Updated Desc", new BigDecimal("150.00"), 15);
        Product updatedProduct = new Product(productId, "Updated Name", "Updated Desc", new BigDecimal("150.00"), 15);
        
        when(productService.updateProduct(eq(productId), any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.price").value(150.00));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        String productId = "123";

        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDecreaseStock() throws Exception {
        String productId = "123";
        Product product = new Product(productId, "Product", "Desc", new BigDecimal("100.00"), 7);
        
        when(productService.decreaseStock(productId, 3)).thenReturn(product);

        mockMvc.perform(post("/api/products/{id}/decrease-stock", productId)
                .param("quantity", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock").value(7));
    }
}
