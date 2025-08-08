package com.schoolmanagement.classservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassResponseDto {
    private Long id;
    private String className;
    private String section;
    private Integer gradeLevel;
    private Integer capacity;
    private Long classTeacherId;
}