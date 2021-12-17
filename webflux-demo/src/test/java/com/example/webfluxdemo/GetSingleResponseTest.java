package com.example.webfluxdemo;

import com.example.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class GetSingleResponseTest extends BaseTest{


    @Test
    public void blockTest(){

        Response response = this.webClient
                                .get()
                                .uri("reactive-math/square/{input}",15)
                                .retrieve()
                                .bodyToMono(Response.class)
                                .block();
        System.out.println(response);
    }

    @Test
    public void stepVerifierTest(){

        Mono<Response> response = this.webClient
                .get()
                .uri("reactive-math/square/{input}",15)
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(response)
                .expectNextMatches(response1 -> response1.getOutput()==225)
                .verifyComplete();

    }

    @Test
    public void badRequestTest(){
        Mono<Response> response = this.webClient
                .get()
                .uri("reactive-math/square/{input}",5)
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(response)
                .expectError();
                //.verifyError(HttpClientErrorException.BadRequest.class);
    }
}
