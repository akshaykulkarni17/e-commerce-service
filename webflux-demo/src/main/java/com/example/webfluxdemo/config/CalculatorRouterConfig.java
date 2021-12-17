package com.example.webfluxdemo.config;

import com.example.webfluxdemo.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class CalculatorRouterConfig {

    @Autowired
    CalculatorHandler calculatorHandler;
    @Bean
    public RouterFunction<ServerResponse> CalculatorRouterFunction(){
        return RouterFunctions.route()
                .path("calculator",this::operationHandler)
                .build();
    }

    private RouterFunction<ServerResponse> operationHandler() {
        return RouterFunctions.route()
                .GET("{a}/{b}", isOperation("+"),calculatorHandler::addition)
                .GET("{a}/{b}", isOperation("-") ,calculatorHandler::subtraction)
                .GET("{a}/{b}", isOperation("*") ,calculatorHandler::multiplication)
                .GET("{a}/{b}", isOperation("/") ,calculatorHandler::division)
                .GET("{a}/{b}", request -> ServerResponse.badRequest().bodyValue("Invalid OP"))
                .build();
    }

    private RequestPredicate isOperation(String operation) {
        return RequestPredicates.headers(headers -> operation.equals(headers.asHttpHeaders().toSingleValueMap().get("OP")));
    }
}
