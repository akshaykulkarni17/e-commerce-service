package com.example.orderService.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {

    private Integer userId;
    private String productId;
}
