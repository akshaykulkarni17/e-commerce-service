package com.example.productService.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Product {

    @Id
    private String name;
    private String description;
    private Integer price;
}
