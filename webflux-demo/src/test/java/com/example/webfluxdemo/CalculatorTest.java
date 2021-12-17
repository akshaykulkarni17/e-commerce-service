package com.example.webfluxdemo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

public class CalculatorTest extends BaseTest{

    private static final int A = 10;
    private static final String FORMAT = "%d %s %d = %s";
    @Test
    public void calculatorTest(){
        Flux<String> flux =
                Flux.range(1,5)
                        .flatMap(b -> Flux.just("+","-","*","/")
                                .flatMap(op -> send(b,op)))
                        .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(20)
                .verifyComplete();
    }

    private Mono<String> send(int b, String operation){
        return this.webClient
                .get()
                .uri("calculator/{a}/{b}",A,b)
                .headers(httpHeaders -> httpHeaders.set("OP",operation))
                .retrieve()
                .bodyToMono(String.class)
                .map(res -> String.format(FORMAT,A,operation,b,res));

    }
}
