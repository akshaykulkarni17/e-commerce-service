package com.example.userService.service;

import com.example.userService.dto.UserDTO;
import com.example.userService.repository.UserRepository;
import com.example.userService.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Flux<UserDTO> getAll(){
        return this.userRepository
                .findAll()
                .map(EntityDtoUtil::toDTO);
    }

    public Mono<UserDTO> getById(int userId){
        return this.userRepository
                .findById(userId)
                .map(EntityDtoUtil::toDTO);
    }

    public Mono<UserDTO> add(Mono<UserDTO> userDTOMono){
        return userDTOMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(user -> this.userRepository.save(user))
                .map(EntityDtoUtil::toDTO);
    }

    public Mono<UserDTO> update(Integer id, Mono<UserDTO> userDTOMono){
        return this.userRepository
                .findById(id)
                .flatMap(user -> userDTOMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(u -> u.setId(id)))
                .flatMap(this.userRepository::save)
                .map(EntityDtoUtil::toDTO);
    }

    public Mono<Void> delete(Integer id){
        return this.userRepository.deleteById(id);

    }
}
