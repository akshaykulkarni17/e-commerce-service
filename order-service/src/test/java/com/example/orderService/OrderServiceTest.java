package com.example.orderService;

import com.example.orderService.client.ProductClient;
import com.example.orderService.client.UserClient;
import com.example.orderService.dto.OrderRequestDTO;
import com.example.orderService.dto.OrderResponseDTO;
import com.example.orderService.dto.ProductDTO;
import com.example.orderService.dto.UserDTO;
import com.example.orderService.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private OrderService orderService;

    @Test
    public void placeOrders(){
        Flux<OrderResponseDTO> flux =
                Flux.zip(this.productClient.getAllProducts(),this.userClient.getAllUsers())
                        .map(objects -> getOrderRequestDTO(objects.getT1(),objects.getT2()))
                        .flatMap(orderRequestDTO -> this.orderService.placeOrder(Mono.just(orderRequestDTO)))
                        .doOnNext(System.out::println);
        StepVerifier.create(flux)
                .expectNextCount(4)
                .verifyComplete();
    }

    private OrderRequestDTO getOrderRequestDTO(ProductDTO t1, UserDTO t2) {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setProductId(t1.getName());
        orderRequestDTO.setUserId(t2.getId());
        return orderRequestDTO;
    }
}
