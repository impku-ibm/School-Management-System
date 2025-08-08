package com.schoolmanagement.authservice.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
   private String email;
   private String password;
}
