package com.example.orderService.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {

    private String name;
    private String description;
    private Integer price;

    public ProductDTO(String description, Integer price) {
        this.description = description;
        this.price = price;
    }
}
