package com.example.demospr;

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
public class DemoApplication {
    public static void main(String[] args) {
      System.out.println(accept("11 - 14"));
      SpringApplication.run(DemoApplication.class, args);
    }

    @PostMapping("/calc")
    public static int accept(@RequestBody String val) {
      val = val.strip();
      String[] nums = new String[2];
      Arrays.fill(nums, "");
      while (!val.isEmpty()) {
        char ch = val.charAt(0);
        val = val.substring(1);
        if (Character.isDigit(ch)) nums[0] = nums[0] + ch;
        else return determineSign(val, nums);
      }
      System.out.println("No sign found");
      return 0;
    }

    public static int determineSign(String subval, String[] nums) {
      char sign = subval.charAt(0);
      subval = subval.substring(1);
      switch (sign) {
        case '+':
          return calculateAdd(subval, nums);
        case '-':
          return calculateSub(subval, nums);
        case '*':
          return calculateMult(subval, nums);
        case ' ':
          return determineSign(subval.substring(1), nums);
        default:
          System.out.println("Unknown sign - " + sign);
          return 0;
      }
    }

    public static int calculateAdd(String subval, String[] nums) {
      if (subval.charAt(0) == ' ') subval = subval.substring(1);
      while (!subval.isEmpty()) {
        char ch = subval.charAt(0);
        subval = subval.substring(1);
        nums[1] = nums[1] + ch;
      }
      try {
        return Integer.parseInt(nums[0].strip()) + Integer.parseInt(nums[1].strip());
      } catch (Exception e) {
        System.out.println("Illegal symbol found");
        return 0;
      }
    }

    public static int calculateSub(String subval, String[] nums) {
      if (subval.charAt(0) == ' ') subval = subval.substring(1);
      while (!subval.isEmpty()) {
        char ch = subval.charAt(0);
        subval = subval.substring(1);
        nums[1] = nums[1] + ch;
      }
      try {
        return Integer.parseInt(nums[0].strip()) - Integer.parseInt(nums[1].strip());
      } catch (Exception e) {
        System.out.println("Illegal symbol found");
        return 0;
      }
    }

    public static int calculateMult(String subval, String[] nums) {
      if (subval.charAt(0) == ' ') subval = subval.substring(1);
      while (!subval.isEmpty()) {
        char ch = subval.charAt(0);
        subval = subval.substring(1);
        nums[1] = nums[1] + ch;
      }
      try {
        return Integer.parseInt(nums[0].strip()) * Integer.parseInt(nums[1].strip());
      } catch (Exception e) {
        System.out.println("Illegal symbol found");
        return 0;
      }
    }
}