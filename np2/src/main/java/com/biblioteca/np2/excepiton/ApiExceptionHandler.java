package com.biblioteca.np2.excepiton;

import com.biblioteca.np2.domain.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        log.error("IllegalArgumentException: {}", ex.getMessage());
        
        ErrorDto error = new ErrorDto(
            ex.getMessage(),
            "VALIDATION_ERROR"
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDto> handleApiException(
            ApiException ex, WebRequest request) {
        log.error("ApiException: {} - {}", ex.getKey(), ex.getMessage());
        
        ErrorDto error = new ErrorDto(
            ex.getMessage(),
            ex.getKey()
        );
        
        return ResponseEntity.status(ex.getStatus()).body(error);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDto> handleRuntimeException(
            RuntimeException ex, WebRequest request) {
        log.error("RuntimeException: {}", ex.getMessage(), ex);
        
        ErrorDto error = new ErrorDto(
            "Erro interno no servidor",
            "INTERNAL_SERVER_ERROR"
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGlobalException(
            Exception ex, WebRequest request) {
        log.error("Exception: {}", ex.getMessage(), ex);
        
        ErrorDto error = new ErrorDto(
            "Erro inesperado no servidor",
            "UNEXPECTED_ERROR"
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

