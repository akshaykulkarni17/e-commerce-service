package com.example.webfluxdemo.config;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
public class CalculatorHandler {

    Mono<ServerResponse> addition(ServerRequest serverRequest){
        return process(serverRequest,(a,b)->ServerResponse.ok().bodyValue(a+b));
    }
    Mono<ServerResponse> subtraction(ServerRequest serverRequest){
        return process(serverRequest,(a,b)->ServerResponse.ok().bodyValue(a-b));
    }
    Mono<ServerResponse> multiplication(ServerRequest serverRequest){
        return process(serverRequest,(a,b)->ServerResponse.ok().bodyValue(a*b));
    }
    Mono<ServerResponse> division(ServerRequest serverRequest){
        return process(serverRequest,(a,b)-> b==0?
             ServerResponse.badRequest().bodyValue("Cannot divide by zero") :
             ServerResponse.ok().bodyValue(a/b));
    }

    Mono<ServerResponse> process(ServerRequest serverRequest, BiFunction<Integer,Integer,Mono<ServerResponse>> opLogic){
            int a = getValue(serverRequest,"a");
            int b = getValue(serverRequest,"b");
           return opLogic.apply(a,b);
    }

    private int getValue(ServerRequest serverRequest, String key){
        return Integer.parseInt(serverRequest.pathVariable(key));
    }
}
