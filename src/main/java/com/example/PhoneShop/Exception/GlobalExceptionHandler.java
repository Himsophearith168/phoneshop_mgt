package com.example.PhoneShop.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorException> handleApiException(APIException e) {
        ErrorException errorException = new ErrorException(e.getStatus(), e.getMessage());
        return new ResponseEntity<>(errorException, e.getStatus());
    }

    @ExceptionHandler({ResourceNotFoundException.class, ResourseNotFoundException.class})
    public ResponseEntity<ErrorException> handleResourceNotFoundException(RuntimeException e) {
        ErrorException errorException = new ErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorException> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        ErrorException errorException = new ErrorException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorException> handleNoResourceFound(NoResourceFoundException e) {
        ErrorException errorException = new ErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorException> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        ErrorException errorException = new ErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorException> handleGlobalException(Exception e) {
        ErrorException errorException = new ErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

