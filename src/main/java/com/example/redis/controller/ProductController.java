package com.example.redis.controller;

import com.example.redis.entity.Product;
import com.example.redis.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<String> addProducts(@RequestBody List<Product> products) {
        productService.addProducts(products);
        return new ResponseEntity<>("Products saved successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>("No products found", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }
}
