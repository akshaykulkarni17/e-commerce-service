package com.example.webfluxdemo;

import com.example.webfluxdemo.dto.Response;
import com.example.webfluxdemo.exception.InvalidInputException;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

public class ExchangeTest extends BaseTest{


    @Test
    public void badRequestTest(){
        Mono<Object> responseMono =
                this.webClient
                        .get()
                        .uri("reactive-math/square/{input}",5)
                        .exchangeToMono(clientResponse -> {
                            if (clientResponse.statusCode().is4xxClientError())
                                return clientResponse.bodyToMono(InvalidInputException.class);
                            return clientResponse.bodyToMono(Response.class);
                        })
                        .doOnNext(System.out::println);
        StepVerifier.create(responseMono)
                //.expectError(InvalidInputException.class);
                .expectNextCount(1)
                .verifyComplete();
    }
}
