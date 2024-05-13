package com.clothing.OrderService.exception;

import com.clothing.OrderService.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(BusinessException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getApiStatus().getMessage(),
                exception.getApiStatus().name(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(exception.getApiStatus().getStatus()).body(errorResponse);
    }
}

