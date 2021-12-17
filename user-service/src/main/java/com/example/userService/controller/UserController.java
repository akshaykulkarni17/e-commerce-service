package com.example.userService.controller;

import com.example.userService.dto.UserDTO;
import com.example.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("all")
    public Flux<UserDTO> getAll(){
        return this.userService.getAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDTO>> getById(@PathVariable Integer id){
        return this.userService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Mono<UserDTO> add(@RequestBody Mono<UserDTO> userDTO){
        return this.userService.add(userDTO);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<UserDTO>> update(@PathVariable Integer id, @RequestBody Mono<UserDTO> userDTOMono){
        return this.userService.update(id,userDTOMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable Integer id){
        return this.userService.delete(id);
    }
}
