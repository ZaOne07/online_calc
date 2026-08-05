package com.example.calc;

import java.util.Stack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
@RestController
public class Server {
String response;
Stack<Integer> numStack = new Stack<>();
Stack<Character> opStack = new Stack<>();

// <-------- boot sequence -------->
    public static void main(String[] args) {
      SpringApplication.run(Server.class, args);
    }

// <-------- service --------> 
    @PostMapping("/calc")
    public void acceptServer(@RequestBody String val) {
      opStack.clear(); // clean previous results
      numStack.clear(); 
      response = ""; 

      if (val == null || val.isEmpty()) { // return message if input is empty
        response = "Empty input"; 
        return; 
      }

      stacker(val); 
    }

    @GetMapping("/calc")
    public String sendServer() { return response; } // send result on request


// <-------- calculation -------->
    public void stacker(String val) {
      String num = "";
      for (int i = 0 ; i < val.length() ; i++) {
        char ch = val.charAt(i);
        if (ch == ' ') continue;
        else if (Character.isDigit(ch) || (ch == '-' && i == 0)) num = num + ch; // add digit to the number, or add - to number if the first number is negative

        else if (isOperator(ch) && !num.isEmpty()) {
          numStack.push(Integer.parseInt(num)); // encountering an operator means the number has ended, push it to stack
          num = "";
            
          while (!opStack.isEmpty() && priority(opStack.peek()) >= priority(ch)) { // perform operations with same or higher priority before this one
            calculate();
            if (!response.isEmpty()) return; // if an error is encountered, stop process
          }
          
          opStack.push(ch); // push the operator to stack
        } else {
          response = "Illegal symbol found"; // if the character isnt a digit or an operator, return error message
          return;
        }
      }
      if (!num.isEmpty()) numStack.push(Integer.parseInt(num)); // push resulting number to stack
      else { 
        response = "No number found"; // return error if no number is found
        return;
      }

      while (!opStack.isEmpty()) { // perform all remaining operations
        calculate();
        if (!response.isEmpty()) return;
      }

      if (numStack.isEmpty()) response = "No result";
      else if (numStack.size() > 1) response = "Invalid expression"; // if theres more than 1 number after calculation, return error message
      else response = String.valueOf(numStack.peek());
    }

    private boolean isOperator(char c) { // determines if a character is an operator
      return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int priority(char op) { // determines the character's priority
      switch (op) {
        case '+':
        case '-': return 1;
        case '*':
        case '/': return 2;
        default: return 0;
      }
    }

    public void calculate() {
      if (opStack.isEmpty()) { // return error if calculation is attempted without operators
        response = "Invalid expression"; 
        return; 
      }

      char sign = opStack.pop();

      if (numStack.size() < 2) { // return error if calculation is attempted with insufficient operands
        response = "Invalid expression"; 
        return; 
      } 

      switch (sign) { // act upon the obtained operator
        case '+':
          calcAdd();
          break;
        case '-':
          calcSub();
          break;
        case '*':
          calcMult();
          break;
        case '/':
          calcDiv();
          break;
        default: // return error is operator is invalid
          response = "Unknown operator - " + sign;
          return;
      }
    }
    
    // pretty obvious stuff here tbh
    public void calcAdd() { numStack.push(numStack.pop() + numStack.pop()); }

    public void calcSub() {
      int b = numStack.pop();
      int a = numStack.pop();
      numStack.push(a - b);
    }

    public void calcMult() { numStack.push(numStack.pop() * numStack.pop()); }

    public void calcDiv() {
      int b = numStack.pop();
      int a = numStack.pop();
      if (b == 0) { 
        response = "Division by zero"; 
        return; 
      }
      numStack.push(a / b);
    }
}