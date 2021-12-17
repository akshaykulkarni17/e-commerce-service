package com.example.userService.dto;

import lombok.Data;

@Data
public class TransactionRequestDTO {

    private Integer userId;
    private Integer amount;

}
