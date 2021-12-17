package com.example.orderService.service;

import com.example.orderService.client.ProductClient;
import com.example.orderService.client.UserTransactionClient;
import com.example.orderService.dto.OrderRequestDTO;
import com.example.orderService.dto.OrderResponseDTO;
import com.example.orderService.dto.RequestContext;
import com.example.orderService.repository.OrderRepository;
import com.example.orderService.util.EntityDTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private UserTransactionClient userTransactionClient;

    public Mono<OrderResponseDTO> placeOrder(Mono<OrderRequestDTO> orderRequestDTO){
        return orderRequestDTO
                .map(RequestContext::new)
                .flatMap(this::productResponse)
                .doOnNext(EntityDTOUtil::toTransactionRequestDTO)
                .flatMap(this::userTransactionResponse)
                .map(EntityDTOUtil::toOrderEntity)
                .map(this.orderRepository::save)
                .map(EntityDTOUtil::toOrderResponseDTO)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private  Mono<RequestContext> userTransactionResponse(RequestContext requestContext) {
        return this.userTransactionClient
                .authorizeTransaction(requestContext.getTransactionRequestDTO())
                .doOnNext(requestContext::setTransactionResponseDTO)
                .thenReturn(requestContext);
    }

    private Mono<RequestContext> productResponse(RequestContext context) {
        return this.productClient.getProductById(context.getOrderRequestDTO().getProductId())
                .doOnNext(context::setProductDTO)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(context);
    }

    public Flux<OrderResponseDTO> queryOrders(Integer userId){
        return Flux.fromStream(this.orderRepository.findByUserId(userId).stream())
                .map(EntityDTOUtil::toOrderResponseDTO)
                .subscribeOn(Schedulers.boundedElastic());

    }

    public Flux<OrderResponseDTO> getAllOrders(){
        return Flux.fromStream(this.orderRepository.findAll().stream())
                .map(EntityDTOUtil::toOrderResponseDTO)
                .subscribeOn(Schedulers.boundedElastic());

    }
}
