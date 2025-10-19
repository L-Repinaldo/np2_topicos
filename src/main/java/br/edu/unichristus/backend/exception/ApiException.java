package br.edu.unichristus.backend.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiException extends RuntimeException {
    public final HttpStatus status;
    public final String key;

    public ApiException(HttpStatus status, String key, String message) {
        super(message);
        this.status = status;
        this.key = key;
    }
}