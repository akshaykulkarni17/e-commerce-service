package com.example.webfluxdemo.service;

import com.example.webfluxdemo.dto.MultiplyRequest;
import com.example.webfluxdemo.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input){
        return Mono.fromSupplier(() -> {
            return new Response(input*input);
        });
    }

    public Flux<Response> findTable(int input) {
        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1))
                .map(integer -> integer*input)
                .map(Response::new);
    }

    public Mono<Response> multiply(Mono<MultiplyRequest> requestMono) {
        return requestMono
                .map(multiplyRequest -> new Response(multiplyRequest.getFirst()* multiplyRequest.getSecond()));


    }
}
