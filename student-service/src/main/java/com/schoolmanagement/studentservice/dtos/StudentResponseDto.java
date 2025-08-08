package com.schoolmanagement.studentservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponseDto {
   private Long id;
   private String fullName;
   private String email;
   private String mobileNumber;
   private String gender;
   private LocalDate dateOfBirth;
   private String address;
}
