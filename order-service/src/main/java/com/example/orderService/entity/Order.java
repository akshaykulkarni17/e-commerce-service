package com.example.orderService.entity;

import com.example.orderService.dto.OrderStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="purchase_order")
public class Order {

    @Id
    @GeneratedValue
    private Integer orderId;
    private String productId;
    private Integer userId;
    private Integer amount;
    private OrderStatus status;
}
