package com.example.orderService.controller;

import com.example.orderService.dto.OrderRequestDTO;
import com.example.orderService.dto.OrderResponseDTO;
import com.example.orderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Mono<ResponseEntity<OrderResponseDTO>> placeOrder(@RequestBody Mono<OrderRequestDTO> orderRequestDTOMono){
        return this.orderService
                .placeOrder(orderRequestDTOMono)
                .map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class,ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientRequestException.class,ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping
    public Flux<OrderResponseDTO> queryOrders(@RequestParam("userId") Optional<Integer> userId){
        if (userId.isPresent()){
            return this.orderService.queryOrders(userId.get());
        }
        return this.orderService.getAllOrders();
    }
}
