package br.edu.unichristus.backend.exception;

import br.edu.unichristus.backend.domain.dto.ErrorDTO;
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
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        log.error("IllegalArgumentException: {}", ex.getMessage());
        
        ErrorDTO error = new ErrorDTO(
            ex.getMessage(),
            "VALIDATION_ERROR"
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDTO> handleApiException(
            ApiException ex, WebRequest request) {
        log.error("ApiException: {} - {}", ex.getKey(), ex.getMessage());
        
        ErrorDTO error = new ErrorDTO(
            ex.getMessage(),
            ex.getKey()
        );
        
        return ResponseEntity.status(ex.getStatus()).body(error);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> handleRuntimeException(
            RuntimeException ex, WebRequest request) {
        log.error("RuntimeException: {}", ex.getMessage(), ex);
        
        ErrorDTO error = new ErrorDTO(
            "Erro interno no servidor",
            "INTERNAL_SERVER_ERROR"
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGlobalException(
            Exception ex, WebRequest request) {
        log.error("Exception: {}", ex.getMessage(), ex);
        
        ErrorDTO error = new ErrorDTO(
            "Erro inesperado no servidor",
            "UNEXPECTED_ERROR"
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

