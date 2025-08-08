package com.schoolmanagement.userservice.dtos;

import com.schoolmanagement.userservice.entity.UserRole;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
   private Long id;
   private String fullName;
   private String username;
   private String email;
   private String mobileNumber;
   private String address;
   private UserRole role;
}