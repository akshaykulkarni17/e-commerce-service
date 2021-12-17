package com.example.orderService.client;

import com.example.orderService.dto.ProductDTO;
import com.example.orderService.dto.TransactionRequestDTO;
import com.example.orderService.dto.TransactionResponseDTO;
import com.example.orderService.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserTransactionClient {

    private final WebClient webClient;

    public UserTransactionClient(@Value("${transactions.service.url}") String url){
        this.webClient = WebClient.builder()
                            .baseUrl(url)
                            .build();
    }

    public Mono<TransactionResponseDTO> authorizeTransaction(TransactionRequestDTO transactionRequestDTO){
            return this.webClient
                    .post()
                    .bodyValue(transactionRequestDTO)
                    .retrieve()
                    .bodyToMono(TransactionResponseDTO.class);
    }

    
}
