package com.example.webfluxdemo;

import com.example.webfluxdemo.dto.MultiplyRequest;
import com.example.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class PostResponseTest extends BaseTest{

    @Test
    public void postTest(){
        Mono<Response> responseMono = this.webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(new MultiplyRequest(5,6))
                .retrieve()
                .bodyToMono(Response.class);
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.getOutput()==30)
                .verifyComplete();
    }
}
