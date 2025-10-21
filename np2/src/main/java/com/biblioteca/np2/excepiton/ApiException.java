package com.biblioteca.np2.excepiton;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiException extends RuntimeException{

    private HttpStatus status;
    private String key;

    public ApiException(HttpStatus status,String key, String message){
        super(message);
        this.key = key;
        this.status = status;
    }

}
