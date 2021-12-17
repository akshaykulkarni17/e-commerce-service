package com.example.userService.controller;

import com.example.userService.dto.TransactionDTO;
import com.example.userService.dto.TransactionRequestDTO;
import com.example.userService.dto.TransactionResponseDTO;
import com.example.userService.entity.UserTransaction;
import com.example.userService.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDTO> post(@RequestBody Mono<TransactionRequestDTO> transactionRequestDTO){
        return transactionRequestDTO.flatMap(this.transactionService::post);
    }

    @GetMapping
    public Flux<TransactionDTO> get(@RequestParam("user") Optional<Integer> userId){
        if(userId.isPresent()){
            return this.transactionService.getById(userId.get());
        }
        return this.transactionService.get();
    }
}

