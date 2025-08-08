package com.schoolmanagement.attendanceservice.dto;

import com.schoolmanagement.attendanceservice.entity.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceRequestDto {
    private Long studentId;
    private Long classId;
    private Long subjectId;
    private Long teacherId;
    private LocalDate attendanceDate;
    private AttendanceStatus status;
    private String remarks;
}