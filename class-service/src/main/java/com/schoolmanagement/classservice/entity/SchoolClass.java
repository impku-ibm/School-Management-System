package com.schoolmanagement.classservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "school_classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClass {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String className;
    
    @Column(nullable = false)
    private String section;
    
    @Column(nullable = false)
    private Integer gradeLevel;
    
    private Integer capacity;
    
    private Long classTeacherId;
}