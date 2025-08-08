package com.schoolmanagement.studentservice.exceptions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<ApiErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
      return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<ApiErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
      String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                          .map(err -> err.getField() + ": " + err.getDefaultMessage())
                          .collect(Collectors.joining(", "));
      return buildResponse(HttpStatus.BAD_REQUEST, errorMsg, request.getRequestURI());
   }

   @ExceptionHandler(MethodArgumentTypeMismatchException.class)
   public ResponseEntity<ApiErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
      String msg = "Invalid type for parameter: " + ex.getName();
      return buildResponse(HttpStatus.BAD_REQUEST, msg, request.getRequestURI());
   }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<ApiErrorResponse> handleGeneral(Exception ex, HttpServletRequest request) {
      return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getRequestURI());
   }

   private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String message, String path) {
      ApiErrorResponse response = ApiErrorResponse.builder()
                                                  .timestamp(LocalDateTime.now())
                                                  .status(status.value())
                                                  .error(status.getReasonPhrase())
                                                  .message(message)
                                                  .path(path)
                                                  .build();
      return new ResponseEntity<>(response, status);
   }
}
