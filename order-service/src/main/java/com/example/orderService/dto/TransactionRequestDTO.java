package com.example.orderService.dto;

import lombok.Data;

@Data
public class TransactionRequestDTO {

    private Integer userId;
    private Integer amount;

}
