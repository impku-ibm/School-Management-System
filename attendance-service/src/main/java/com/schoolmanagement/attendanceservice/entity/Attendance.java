package com.schoolmanagement.attendanceservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long studentId;
    
    @Column(nullable = false)
    private Long classId;
    
    @Column(nullable = false)
    private Long subjectId;
    
    @Column(nullable = false)
    private Long teacherId;
    
    @Column(nullable = false)
    private LocalDate attendanceDate;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status; // PRESENT, ABSENT, LATE
    
    private String remarks;
}