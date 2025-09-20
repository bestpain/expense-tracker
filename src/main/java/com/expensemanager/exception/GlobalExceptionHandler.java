package com.expensemanager.exception;

import com.expensemanager.dto.user.userError.UserErrorResponse;
import com.expensemanager.exception.category.CategoryNotFound;
import com.expensemanager.exception.user.DuplicateResourceException;
import com.expensemanager.exception.user.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exp) {
        Map<String, String> errors = new HashMap<>();
        exp.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }

    //DuplicateResourceException
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<UserErrorResponse> duplicateExceptionHandler(DuplicateResourceException exp) {
        UserErrorResponse userErrorResponse = new UserErrorResponse();
        userErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        userErrorResponse.setMessage(exp.getMessage());
        userErrorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<UserErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        UserErrorResponse userErrorResponse = new UserErrorResponse();
        userErrorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        userErrorResponse.setMessage("Invalid email or password");
        userErrorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(CategoryNotFound.class)
    public ResponseEntity<?> handleCategoryNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }
}
