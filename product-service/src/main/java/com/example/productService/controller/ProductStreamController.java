package com.example.productService.controller;

import com.example.productService.dto.ProductDTO;
import com.example.productService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("product")
public class ProductStreamController {


    @Autowired
    private Flux<ProductDTO> flux;


    @GetMapping(value = "stream/{price}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDTO> getProductsStream(@PathVariable int price){
        return this.flux
                .filter(productDTO -> productDTO.getPrice()<=price);
    }


}
