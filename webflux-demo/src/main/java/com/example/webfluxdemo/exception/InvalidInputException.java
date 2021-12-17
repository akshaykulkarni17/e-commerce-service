package com.example.webfluxdemo.exception;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@ToString
public class InvalidInputException  extends RuntimeException{

    private static final String Message = "Allowed range is 10 20";
    @Getter
    private static final int errorCode = 999;
    @Getter
    private int input;

    public InvalidInputException(int input){
        super(Message);
        this.input=input;
    }


}
