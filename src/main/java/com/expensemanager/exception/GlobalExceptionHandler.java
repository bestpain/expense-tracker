package com.expensemanager.exception;

import com.expensemanager.dto.user.userError.UserErrorResponse;
import com.expensemanager.exception.category.CategoryNotFound;
import com.expensemanager.exception.user.DuplicateResourceException;
import com.expensemanager.exception.user.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    @ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
    public Map<String, String> handleValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();

        if (ex instanceof MethodArgumentNotValidException manve) {
            manve.getBindingResult().getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
        } else if (ex instanceof ConstraintViolationException cve) {
            cve.getConstraintViolations().forEach(violation ->
                    errors.put(violation.getPropertyPath().toString(), violation.getMessage())
            );
        }

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
    public ResponseEntity<UserErrorResponse> handleCategoryNotFound(CategoryNotFound ex) {
        UserErrorResponse userErrorResponse = new UserErrorResponse();
        userErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        userErrorResponse.setMessage(ex.getMessage());
        userErrorResponse.setTimeStamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userErrorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", "Request body is missing or malformed. Please send valid JSON.");
        errorResponse.put("error", ex.getMostSpecificCause().getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }}
