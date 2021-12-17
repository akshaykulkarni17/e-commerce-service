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
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private Flux<ProductDTO> flux;

    @GetMapping("all")
    public Flux<ProductDTO> getAllProducts(){
        return this.productService.getAll();
    }

    @GetMapping(value = "stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDTO> getProductsStream(){
        return this.flux;
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDTO>> getProductById(@PathVariable String id){
        this.simulateIntermittentException();
        return this.productService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Mono<ProductDTO> addProduct(@RequestBody Mono<ProductDTO> productDTO){
        return this.productService.add(productDTO);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDTO>> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDTO> productDTOMono){
        return this.productService.update(id,productDTOMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteProduct(@PathVariable String id){
        return this.productService.delete(id);
    }

    @GetMapping("price-range")
    public Flux<ProductDTO> getProductsRange(@RequestParam("min") int min, @RequestParam("max") int max){
        return this.productService.getRange(min, max);
    }
    private void simulateIntermittentException(){
        int next = ThreadLocalRandom.current().nextInt(1,10);
        if(next>5)
            throw new RuntimeException("something went wrong");
    }
}
