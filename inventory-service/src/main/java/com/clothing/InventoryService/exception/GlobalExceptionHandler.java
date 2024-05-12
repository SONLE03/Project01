package com.clothing.InventoryService.exception;

import com.clothing.InventoryService.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex) {
        return "Unknow";
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException businessException){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(businessException.getApiStatus().name());
        errorResponse.setMessage(businessException.getApiStatus().getMessage());
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(businessException.getApiStatus().getStatus()).body(errorResponse);
    }
}
