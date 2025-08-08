package com.schoolmanagement.studentservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRequestDto {
   private String firstName;
   private String lastName;
   private String email;
   private String mobileNumber;
   private String gender;
   private LocalDate dateOfBirth;
   private String address;
}
