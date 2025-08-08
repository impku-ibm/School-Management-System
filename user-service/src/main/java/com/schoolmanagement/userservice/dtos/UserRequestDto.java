package com.schoolmanagement.userservice.dtos;

import com.schoolmanagement.userservice.entity.UserRole;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {

   @NotBlank(message = "Full name is required")
   private String fullName;

   @NotBlank(message = "Username is required")
   private String username;

   @NotBlank(message = "Password is required")
   private String password;

   @Email(message = "Invalid email format")
   private String email;

   @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
   private String mobileNumber;

   @NotBlank(message = "Address is required")
   private String address;

   @NotNull(message = "Role is required")
   private UserRole role;
}
