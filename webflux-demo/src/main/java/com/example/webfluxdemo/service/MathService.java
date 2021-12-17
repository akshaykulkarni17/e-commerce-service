package com.example.webfluxdemo.service;

import com.example.webfluxdemo.dto.Response;
import org.springframework.stereotype.Service;

import javax.swing.text.Utilities;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MathService {

    public  Response findSquare(int input){
        return new Response(input*input);
    }

    public  List<Response> findTable(int input){
        return IntStream.range(1,11)
                .peek(value -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .mapToObj(value -> new Response(value*input))
                .collect(Collectors.toList());
    }
}
