package com.example.productService.service;

import com.example.productService.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        ProductDTO p1 = new ProductDTO("mobile",500);
        ProductDTO p2 = new ProductDTO("headphone",100);
        ProductDTO p3 = new ProductDTO("laptop",5000);
        ProductDTO p4 = new ProductDTO("table",200);

        Flux.just(p1,p2,p3,p4)
                .map(Mono::just)
                .flatMap(productDTOMono -> this.productService.add(productDTOMono))
                .subscribe(System.out::println);

    }
}
