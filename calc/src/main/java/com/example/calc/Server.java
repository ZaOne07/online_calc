package com.example.calc;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Server {
  static String response;
    public static void main(String[] args) {
      accept("123-456");
      System.out.println(response);
      SpringApplication.run(Server.class, args);
    }

    @PostMapping("/calc")
    public static void accept(@RequestBody String val) {
      val = val.strip();
      String[] nums = new String[2];
      Arrays.fill(nums, "");
      while (!val.isEmpty()) {
        char ch = val.charAt(0);
        val = val.substring(1);
        if (Character.isDigit(ch)) nums[0] = nums[0] + ch;
        else {determineSign(val, nums, ch); return;}
      }
      response = "No sign found";
    }

    public static void determineSign(String subval, String[] nums, char sign) {
      switch (sign) {
        case '+':
          calculateAdd(subval, nums);
          break;
        case '-':
          calculateSub(subval, nums);
          break;
        case '*':
          calculateMult(subval, nums);
          break;
        case ' ':
          determineSign(subval.substring(1), nums, subval.charAt(0));
          break;
        default:
          response = "Unknown sign - " + sign;
      }
    }

    public static void calculateAdd(String subval, String[] nums) {
      nums[1] = valExtraction(subval, nums[1]);
      try {
        response = String.valueOf(Integer.parseInt(nums[0].strip()) + Integer.parseInt(nums[1].strip()));
      } catch (Exception e) {
        response = "Illegal symbol found";
      }
    }

    public static void calculateSub(String subval, String[] nums) {
      nums[1] = valExtraction(subval, nums[1]);
      try {
        response = String.valueOf(Integer.parseInt(nums[0].strip()) - Integer.parseInt(nums[1].strip()));
      } catch (Exception e) {
        response = "Illegal symbol found";
      }
    }

    public static void calculateMult(String subval, String[] nums) {
      nums[1] = valExtraction(subval, nums[1]);
      try {
        response = String.valueOf(Integer.parseInt(nums[0].strip()) * Integer.parseInt(nums[1].strip()));
      } catch (Exception e) {
        response = "Illegal symbol found";
      }
    }

    public static String valExtraction(String subval, String num) {
      if (subval.charAt(0) == ' ') subval = subval.substring(1);
      while (!subval.isEmpty()) {
        char ch = subval.charAt(0);
        subval = subval.substring(1);
        num = num + ch;
      }
      return num;
    }

    
}