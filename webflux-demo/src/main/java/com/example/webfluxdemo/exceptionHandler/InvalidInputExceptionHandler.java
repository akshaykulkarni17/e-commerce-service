package com.example.webfluxdemo.exceptionHandler;

import com.example.webfluxdemo.dto.InvalidInputResponse;
import com.example.webfluxdemo.exception.InvalidInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidInputExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<InvalidInputResponse> handle(InvalidInputException exception){
        InvalidInputResponse invalidInputResponse = new InvalidInputResponse();
        invalidInputResponse.setInput(exception.getInput());
        invalidInputResponse.setMsg(exception.getMessage());
        invalidInputResponse.setErrorCode(InvalidInputException.getErrorCode());
        return ResponseEntity.badRequest().body(invalidInputResponse);
    }
}
