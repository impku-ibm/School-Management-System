package com.schoolmanagement.userservice.dtos;

import com.schoolmanagement.userservice.entity.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {
   private String fullName;

   @Email(message = "Invalid email format")
   private String email;

   @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
   private String mobileNumber;

   private String address;

   private UserRole role;
}
