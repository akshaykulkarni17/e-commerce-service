package com.example.orderService.dto;

import lombok.Data;

@Data
public class TransactionResponseDTO {

    private Integer userId;
    private Integer amount;
    private TransactionStatus status;
}
