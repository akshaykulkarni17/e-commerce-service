package com.example.webfluxdemo;

import com.example.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class GetMultiResponseTest extends BaseTest{


    @Test
    public void fluxTest(){
        Flux<Response> flux = this.webClient
                                .get()
                                .uri("reactive-math/table/{input}",5)
                                .retrieve()
                                .bodyToFlux(Response.class);

        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();

    }

    @Test
    public void fluxStreamTest(){
        Flux<Response> flux = this.webClient
                .get()
                .uri("reactive-math/table/{input}/stream",5)
                .retrieve()
                .bodyToFlux(Response.class);

        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();

    }
}
