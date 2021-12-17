package com.example.orderService.dto;

import lombok.Data;

@Data
public class OrderResponseDTO {

    private Integer orderId;
    private Integer userId;
    private String productId;
    private Integer amount;
    private OrderStatus status;
}
