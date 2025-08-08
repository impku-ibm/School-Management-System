package com.schoolmanagement.gradeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeResponseDto {
    private Long id;
    private Long studentId;
    private Long subjectId;
    private Long teacherId;
    private Double marks;
    private Double totalMarks;
    private Double percentage;
    private String examType;
    private LocalDate examDate;
    private String remarks;
}