package com.rating.service.exceptions;

import com.rating.service.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandller {
    ResponseEntity<ApiResponse> HandlerResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse response = ApiResponse.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .sucesss(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
