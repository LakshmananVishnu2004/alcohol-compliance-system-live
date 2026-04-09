package com.vishnu.demo.exception;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserExists(UserAlreadyExistsException ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("status", 400);
        error.put("message", ex.getMessage());
        error.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidUser(InvalidUserException ex) {

        Map<String, Object> error = new HashMap<>();

        error.put("status", 400);
        error.put("message", ex.getMessage());
        error.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PurchaseLimitExceededException.class)
    public ResponseEntity<?> handlePurchaseLimit(PurchaseLimitExceededException ex) {
        return ResponseEntity.badRequest().body(
                Map.of(
                        "error", "LIMIT_EXCEEDED",
                        "message", ex.getMessage()
                )
        );
    }

    @ExceptionHandler(OtpNotFoundException.class)
    public ResponseEntity<?> handleOtpNotFound(OtpNotFoundException ex) {
        return ResponseEntity.status(404).body(
                Map.of(
                        "error", "OTP_NOT_FOUND",
                        "message", ex.getMessage()
                )
        );
    }
}