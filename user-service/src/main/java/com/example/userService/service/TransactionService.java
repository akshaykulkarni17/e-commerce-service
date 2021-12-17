package com.example.userService.service;

import com.example.userService.dto.TransactionDTO;
import com.example.userService.dto.TransactionRequestDTO;
import com.example.userService.dto.TransactionResponseDTO;
import com.example.userService.dto.TransactionStatus;
import com.example.userService.entity.UserTransaction;
import com.example.userService.repository.TransactionRepository;
import com.example.userService.repository.UserRepository;
import com.example.userService.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Mono<TransactionResponseDTO> post(final TransactionRequestDTO requestDTO){
        return this.userRepository.updateBalance(requestDTO.getUserId(), requestDTO.getAmount())
                .filter(Boolean::booleanValue)
                .map(posted -> EntityDtoUtil.toEntity(requestDTO))
                .flatMap(this.transactionRepository::save)
                .map(userTransaction -> EntityDtoUtil.toDTO(requestDTO, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toDTO(requestDTO,TransactionStatus.DECLINED));
    }

    public Flux<TransactionDTO> get(){
        return this.transactionRepository.findAll()
                .map(EntityDtoUtil::toDTO);
    }

    public Flux<TransactionDTO> getById(Integer userId){
        return this.transactionRepository.findByUserId(userId)
                .map(EntityDtoUtil::toDTO);
    }

}
