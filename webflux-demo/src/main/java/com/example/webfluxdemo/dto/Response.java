package com.example.webfluxdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Response {

    private Date date = new Date();
    private int output ;

    public Response(int output) {
        this.output = output;
    }
}
