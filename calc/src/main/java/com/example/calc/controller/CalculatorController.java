package com.example.calc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.calc.dto.*;
import com.example.calc.service.CalculatorService;

@RestController
public class CalculatorController {

  @Autowired
  private CalculatorService calculator;

    @PostMapping("/calc")
    public ResponseDTO calculate(@RequestBody RequestDTO request) {
      return calculator.calculate(request.getExpression());
    }
}