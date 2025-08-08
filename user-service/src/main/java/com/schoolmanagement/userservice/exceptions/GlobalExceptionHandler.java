package com.schoolmanagement.userservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
      log.warn("Resource not found: {}", ex.getMessage());
      return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler(IllegalArgumentException.class)
   public ResponseEntity<Object> handleBadRequest(IllegalArgumentException ex) {
      log.warn("Bad request: {}", ex.getMessage());
      return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<Object> handleGenericException(Exception ex) {
      log.error("Unexpected error: {}", ex.getMessage(), ex);
      return buildErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
   }

   private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
      Map<String, Object> errorBody = new HashMap<>();
      errorBody.put("timestamp", LocalDateTime.now());
      errorBody.put("status", status.value());
      errorBody.put("error", status.getReasonPhrase());
      errorBody.put("message", message);

      return new ResponseEntity<>(errorBody, status);
   }
}

