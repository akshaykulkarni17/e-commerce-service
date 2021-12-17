package com.example.webfluxdemo.dto;

import lombok.Data;

@Data
public class InvalidInputResponse {

    private int errorCode;
    private int input;
    private String msg;

}
