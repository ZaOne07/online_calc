package com.example.calc;

import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
@RestController
public class Server {

  @Autowired
  private CalculatorService calculator;

    public static void main(String[] args) {
      SpringApplication.run(Server.class, args);
    }

    @PostMapping("/calc")
    public ResponseDTO calculate(@RequestBody RequestDTO request) {
      return calculator.calculate(request.getExpression());
    }
}