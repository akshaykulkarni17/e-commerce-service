package com.example.orderService.dto;

import lombok.Data;

@Data
public class RequestContext {

    private OrderRequestDTO orderRequestDTO;
    private ProductDTO productDTO;
    private TransactionRequestDTO transactionRequestDTO;
    private TransactionResponseDTO transactionResponseDTO;

    public RequestContext(OrderRequestDTO orderRequestDTO) {
        this.orderRequestDTO = orderRequestDTO;
    }
}
