package com.Singhify.Singhify.APIResponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorStruct {
    private String message;
    private int statusCode;
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "{" +"\n"+
                "message: " + message + "\n"+
                "statusCode: " + statusCode +"\n"+
                "timestamp: " + timestamp +"\n"+
                '}';
    }
}
