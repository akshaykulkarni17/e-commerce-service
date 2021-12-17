package com.example.orderService.client;

import com.example.orderService.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(@Value("${product.service.url}") String url){
        this.webClient = WebClient.builder()
                            .baseUrl(url)
                            .build();
    }

    public Mono<ProductDTO> getProductById(String productId){
              return   this.webClient
                            .get()
                            .uri("/{id}",productId)
                            .retrieve()
                            .bodyToMono(ProductDTO.class);
    }

    public Flux<ProductDTO> getAllProducts(){
        return this.webClient
                .get()
                .uri("/all")
                .retrieve()
                .bodyToFlux(ProductDTO.class);
    }

}
