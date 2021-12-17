package com.example.orderService.util;

import com.example.orderService.dto.*;
import com.example.orderService.entity.Order;
import org.springframework.beans.BeanUtils;

public class EntityDTOUtil {

    public static void toTransactionRequestDTO(RequestContext context){
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
        transactionRequestDTO.setUserId(context.getOrderRequestDTO().getUserId());
        transactionRequestDTO.setAmount(context.getProductDTO().getPrice());
        context.setTransactionRequestDTO(transactionRequestDTO);
    }

    public static Order toOrderEntity(RequestContext requestContext){
        Order order = new Order();
        order.setUserId(requestContext.getTransactionResponseDTO().getUserId());
        order.setAmount(requestContext.getTransactionResponseDTO().getAmount());
        order.setProductId(requestContext.getProductDTO().getName());
        order.setStatus(
                requestContext.getTransactionResponseDTO().getStatus().equals(TransactionStatus.APPROVED) ?
                        OrderStatus.COMPLETED :
                        OrderStatus.FAILED
        );
        return order;
    }

    public static OrderResponseDTO toOrderResponseDTO(Order order){
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(order.getOrderId());
        BeanUtils.copyProperties(order,orderResponseDTO);
        return orderResponseDTO;
    }
}
