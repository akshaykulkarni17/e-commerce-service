package com.example.webfluxdemo.controller;

import com.example.webfluxdemo.dto.MultiplyRequest;
import com.example.webfluxdemo.dto.Response;
import com.example.webfluxdemo.exception.InvalidInputException;
import com.example.webfluxdemo.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {

    @Autowired
    private ReactiveMathService mathService;

    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable int input){
        if (input<10 || input>20) throw new InvalidInputException(input);
        return this.mathService.findSquare(input);
    }

    @GetMapping("square/{input}/ass")
    public Mono<ResponseEntity<Response>> assignment(@PathVariable int input){
        return Mono.just(input)
                .filter(integer -> integer>10&&integer<20)
                .flatMap(integer -> this.mathService.findSquare(integer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping("square/{input}/error")
    public Mono<Response> findSquareError(@PathVariable int input){
        return Mono.just(input)
                .handle((integer, sink) -> {
                    if(integer>10&&integer<20)
                        sink.next(integer);
                    else sink.error(new InvalidInputException(input));
                })
                .cast(Integer.class)
                .flatMap(i->this.mathService.findSquare(i));
    }

    @GetMapping("table/{input}")
    public Flux<Response> findTable(@PathVariable int input){
        return this.mathService.findTable(input);
    }

    @GetMapping(value = "table/{input}/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> findTableStream(@PathVariable int input){
        return this.mathService.findTable(input);
    }

    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequest> requestMono){
        return this.mathService.multiply(requestMono);
    }



}
