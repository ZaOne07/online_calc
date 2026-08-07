package com.example.calc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.calc.controller.CalculatorController;

@SpringBootApplication
public class CalculatorApplication {
    public static void main(String[] args) {
      SpringApplication.run(CalculatorController.class, args);
    }
}
