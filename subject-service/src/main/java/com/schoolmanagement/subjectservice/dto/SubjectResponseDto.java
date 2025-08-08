package com.schoolmanagement.subjectservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectResponseDto {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Integer gradeLevel;
}