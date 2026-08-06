package com.example.calc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class ResponseDTO {
    private String status;
    private String result;
    private String errorMessage;
    
    public static ResponseDTO success(String result) {
        return new ResponseDTO("SUCCESS", result, null);
    }
    
    public static ResponseDTO error(String message) {
        return new ResponseDTO("ERROR", null, message);
    }
}
