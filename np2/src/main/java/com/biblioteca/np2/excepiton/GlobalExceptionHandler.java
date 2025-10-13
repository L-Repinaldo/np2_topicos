package com.biblioteca.np2.excepiton;


import com.biblioteca.np2.domain.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDto> handleApi(ApiException ex){

        log.error("ApiException: ()", ex.getMessage(), ex);
        return ResponseEntity.status(ex.getStatus()).body(new ErrorDto(ex.getMessage(), ex.getKey()));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGeneric(Exception ex){

        log.error("Exception não tratada", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Exception não tratada",
                "unichristus.exception"));

    }
}
