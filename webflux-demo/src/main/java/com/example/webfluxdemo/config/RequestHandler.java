package com.example.webfluxdemo.config;

import com.example.webfluxdemo.dto.MultiplyRequest;
import com.example.webfluxdemo.dto.Response;
import com.example.webfluxdemo.exception.InvalidInputException;
import com.example.webfluxdemo.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {

    @Autowired
    private ReactiveMathService mathService;

    public Mono<ServerResponse> findSquare(ServerRequest serverRequest){
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        if (input<10||input>20)
            return Mono.error(new InvalidInputException(input));
        Mono<Response> response = this.mathService.findSquare(input);
        return ServerResponse.ok().body(response,Response.class);
    }

    public Mono<ServerResponse> findTable(ServerRequest serverRequest){
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> response = this.mathService.findTable(input);
        return ServerResponse.ok().body(response,Response.class);
    }

    public Mono<ServerResponse> findTableStream(ServerRequest serverRequest){
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> response = this.mathService.findTable(input);
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(response,Response.class);
    }

    public Mono<ServerResponse> multiply(ServerRequest serverRequest){
        Mono<MultiplyRequest> multiplyRequestMono = serverRequest.bodyToMono(MultiplyRequest.class);
        Mono<Response> response = this.mathService.multiply(multiplyRequestMono);
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(response,Response.class);
    }
}
