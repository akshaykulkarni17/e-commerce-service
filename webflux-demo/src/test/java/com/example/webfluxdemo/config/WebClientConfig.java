package com.example.webfluxdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                //.defaultHeaders(httpHeaders -> httpHeaders.setBasicAuth("username","password"))
                .filter(this::generateToken)
                .build();
    }

    private Mono<ClientResponse> generateToken(ClientRequest request, ExchangeFunction exchangeFunction) {
        System.out.println("Generating token");
        ClientRequest clientRequest =
                ClientRequest.from(request)
                        .headers(httpHeaders -> httpHeaders.setBearerAuth("Ooolala"))
                        .build();
        return exchangeFunction.exchange(clientRequest);
    }


}
