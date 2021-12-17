package com.example.userService.repository;

import com.example.userService.entity.UserTransaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<UserTransaction,Integer> {


    Flux<UserTransaction> findByUserId(Integer userId);

}
