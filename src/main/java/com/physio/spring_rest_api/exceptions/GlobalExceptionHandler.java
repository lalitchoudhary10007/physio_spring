package com.physio.spring_rest_api.exceptions;

import com.physio.spring_rest_api.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value(),
                        null),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> alreadyExistsExceptionHandler(AlreadyExistsException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>(
                        false,
                        ex.msg,
                        HttpStatus.ALREADY_REPORTED.value(),
                        null),
                HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>(
                        false,
                        ex.getAllErrors().getFirst().getDefaultMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        null),
                HttpStatus.BAD_REQUEST);
    }

}
