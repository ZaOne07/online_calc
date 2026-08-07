package com.example.calc.service;

import java.util.Stack;
import org.springframework.stereotype.Service;
import com.example.calc.dto.ResponseDTO;

@Service
public class CalculatorService {

    public ResponseDTO calculate(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            return ResponseDTO.error("Empty input");
        }

        Stack<Double> numStack = new Stack<>();
        Stack<Character> opStack = new Stack<>();

        String result = process(expression, numStack, opStack);

        try {
            Double.parseDouble(result);
            return ResponseDTO.success(result);
        } catch (Exception e) {
            return ResponseDTO.error(result);
        }
    }
    
    private String process(String expression, Stack<Double> numStack, Stack<Character> opStack) {
        String num = "";

        for (int i = 0 ; i < expression.length() ; i++) {
            char ch = expression.charAt(i);
            if (ch == ' ') {
                if (!num.isEmpty() && Character.isDigit(expression.charAt(i + 1))) return "Invalid number format";
                continue;
            }
            else if (Character.isDigit(ch) || (ch == '-' && i == 0)) num = num + ch;
            
            else if (ch == '.') {
                if (num.contains(".")) return "Invalid number format";
                if (num.isEmpty()) num = "0";
                num = num + ch;
            }
            else if (isOperator(ch)) {
                if (num.isEmpty()) return "Illegal symbol placement";
                numStack.push(Double.parseDouble(num));
                num = "";
            
                while (!opStack.isEmpty() && priority(opStack.peek()) >= priority(ch)) {
                    String error = calculate(numStack, opStack);
                    if (error != null) return error;
                }
          
                opStack.push(ch); // push the operator to stack
            } 
            else return "Illegal symbol found"; // if the character isnt a digit or an operator, return error message
        }
        if (!num.isEmpty()) numStack.push(Double.parseDouble(num)); // push resulting number to stack
        else { 
            return "No number found"; // return error if no number is found
        }

        if (opStack.isEmpty()) return "Invalid expression";
        while (!opStack.isEmpty()) { // perform all remaining operations
            String error = calculate(numStack, opStack);
            if (error != null) return error;
        }

        if (numStack.isEmpty()) return "No result";
        else if (numStack.size() > 1) return "Invalid expression"; // if theres more than 1 number after calculation, return error message
        else return String.valueOf(numStack.peek());
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

    private String calculate(Stack<Double> numStack, Stack<Character> opStack) {
        if (opStack.isEmpty()) return "Invalid expression";

        char sign = opStack.pop();

        if (numStack.size() < 2) return "Invalid expression";

        switch (sign) { // act upon the obtained operator
            case '+':
                numStack.push(numStack.pop() + numStack.pop());
                break;
            case '-':
                double b = numStack.pop();
                double a = numStack.pop();
                numStack.push(a - b);
                break;
            case '*':
                numStack.push(numStack.pop() * numStack.pop());
                break;
            case '/':
                double divr = numStack.pop();
                double divnd = numStack.pop();
                if (Math.abs(divr) < 1e-15) { 
                    return "Division by zero"; 
                }
                numStack.push(divnd / divr);
                break;
            default: // return error is operator is invalid
                return "Unknown operator - " + sign;
        }
        return null;
    }
}
