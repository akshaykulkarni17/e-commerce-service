package com.example.orderService.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private Integer id;
    private Integer userId;
    private Integer amount;
    private LocalDateTime transactionDate;

}
