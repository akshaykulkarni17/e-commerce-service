package com.example.userService.util;

import com.example.userService.dto.*;
import com.example.userService.entity.User;
import com.example.userService.entity.UserTransaction;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class EntityDtoUtil {

    public static User toEntity(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        return user;
    }

    public static UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        return userDTO;
    }

    public static UserTransaction toEntity(TransactionRequestDTO transactionRequestDTO){
        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserId(transactionRequestDTO.getUserId());
        userTransaction.setAmount(transactionRequestDTO.getAmount());
        userTransaction.setTransactionDate(LocalDateTime.now());
        return userTransaction;
    }

    public static TransactionResponseDTO toDTO(TransactionRequestDTO transactionRequestDTO, TransactionStatus status){
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
        transactionResponseDTO.setUserId(transactionRequestDTO.getUserId());
        transactionResponseDTO.setAmount(transactionRequestDTO.getAmount());
        transactionResponseDTO.setStatus(status);
        return transactionResponseDTO;
    }

    public static TransactionDTO toDTO(UserTransaction userTransaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        BeanUtils.copyProperties(userTransaction,transactionDTO);
        return transactionDTO;
    }
}
