package com.example.productService.service;

import com.example.productService.dto.ProductDTO;
import com.example.productService.repository.ProductRepository;
import com.example.productService.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Sinks.Many<ProductDTO> sink;

    public Flux<ProductDTO> getAll(){
        return this.productRepository
                .findAll()
                .map(EntityDtoUtil::toDTO);
    }

    public Mono<ProductDTO> getById(String id){
        return this.productRepository
                .findById(id)
                .map(EntityDtoUtil::toDTO);
    }

    public Mono<ProductDTO> add(Mono<ProductDTO> productDTO){
        return productDTO
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.productRepository::insert)
                .map(EntityDtoUtil::toDTO)
                .doOnNext(this.sink::tryEmitNext);
    }

    public Mono<ProductDTO> update(String id, Mono<ProductDTO> productDTOMono){
        return this.productRepository.findById(id)
                .flatMap(product -> productDTOMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(entity -> entity.setName(id)))
                .flatMap(productMono -> this.productRepository.save(productMono))
                .map(EntityDtoUtil::toDTO);
    }

    public Mono<Void> delete(String id){
        return this.productRepository.deleteById(id);
    }

//    public Flux<ProductDTO> getRange(int min, int max){
//        return this.productRepository
//                .findAll()
//                .filter(product -> product.getPrice()>=min&&product.getPrice()<=max)
//                .map(EntityDtoUtil::toDTO);
//    }

    public Flux<ProductDTO> getRange(int min,int max){
        return this.productRepository
                .findByPriceBetween(Range.closed(min,max))
                .map(EntityDtoUtil::toDTO);
    }
}
