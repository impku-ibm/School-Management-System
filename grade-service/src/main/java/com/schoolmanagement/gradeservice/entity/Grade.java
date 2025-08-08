package com.schoolmanagement.gradeservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "grades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long studentId;
    
    @Column(nullable = false)
    private Long subjectId;
    
    @Column(nullable = false)
    private Long teacherId;
    
    @Column(nullable = false)
    private Double marks;
    
    @Column(nullable = false)
    private Double totalMarks;
    
    @Column(nullable = false)
    private String examType; // QUIZ, MIDTERM, FINAL, ASSIGNMENT
    
    private LocalDate examDate;
    
    private String remarks;
}