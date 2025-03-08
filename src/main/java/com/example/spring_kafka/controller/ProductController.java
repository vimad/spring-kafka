package com.example.spring_kafka.controller;

import com.example.spring_kafka.model.Product;
import com.example.spring_kafka.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public String createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }
}
