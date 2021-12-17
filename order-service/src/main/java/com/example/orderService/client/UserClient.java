package com.example.orderService.client;

import com.example.orderService.dto.TransactionRequestDTO;
import com.example.orderService.dto.TransactionResponseDTO;
import com.example.orderService.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

    private final WebClient webClient;

    public UserClient(@Value("${user.service.url}") String url){
        this.webClient = WebClient.builder()
                            .baseUrl(url)
                            .build();
    }

    public Flux<UserDTO> getAllUsers(){
        return this.webClient
                .get()
                .uri("/all")
                .retrieve()
                .bodyToFlux(UserDTO.class);
    }
    
}
