package com.example.webfluxdemo.config;

import com.example.webfluxdemo.dto.InvalidInputResponse;
import com.example.webfluxdemo.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    @Autowired
    private RequestHandler requestHandler;

    @Bean
    RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions.route()
                .GET("router/square/{input}",requestHandler::findSquare)
                .GET("router/table/{input}", RequestPredicates.path("*/*/1?").or(RequestPredicates.path("*/*/20")),requestHandler::findTable)
                .GET("router/table/{input}",request -> ServerResponse.badRequest().bodyValue("Only 10-20 allowed"))
                .GET("router/table/{input}/stream",requestHandler::findTableStream)
                .POST("router/multiply", requestHandler::multiply)
                .onError(InvalidInputException.class,exceptionHandler())
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler(){
        return (throwable, serverRequest) -> {
            InvalidInputException exception = (InvalidInputException) throwable;
            InvalidInputResponse invalidInputResponse = new InvalidInputResponse();
            invalidInputResponse.setInput(exception.getInput());
            invalidInputResponse.setErrorCode(InvalidInputException.getErrorCode());
            invalidInputResponse.setMsg(exception.getMessage());
            return ServerResponse.badRequest().bodyValue(invalidInputResponse);
        };
    }
}
