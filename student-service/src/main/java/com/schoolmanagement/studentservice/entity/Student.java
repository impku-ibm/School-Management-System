package com.schoolmanagement.studentservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String firstName;

   @Column(nullable = false)
   private String lastName;

   @Column(nullable = false, unique = true)
   private String email;

   @Column(nullable = false)
   private String gender;

   private String phone;

   private LocalDate dateOfBirth;

   private String address;

   private String city;

   private String state;

   private String country;

   private String zipCode;

   private LocalDate enrollmentDate;

   @Enumerated(EnumType.STRING)
   private Status status; // ACTIVE, INACTIVE, SUSPENDED

   private String createdBy;
   private LocalDateTime createdAt;

   private String updatedBy;
   private LocalDateTime updatedAt;

   // Future: @ManyToOne for Class, Guardian, etc.
}
