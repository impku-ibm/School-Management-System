package com.schoolmanagement.studentservice.exceptions;

public class ResourceNotFoundException extends RuntimeException{
   public ResourceNotFoundException(String message) {
      super(message);
   }
}
