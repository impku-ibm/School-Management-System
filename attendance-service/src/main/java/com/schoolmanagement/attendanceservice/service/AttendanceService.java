package com.schoolmanagement.attendanceservice.service;

import com.schoolmanagement.attendanceservice.dto.AttendanceRequestDto;
import com.schoolmanagement.attendanceservice.dto.AttendanceResponseDto;
import com.schoolmanagement.attendanceservice.entity.Attendance;
import com.schoolmanagement.attendanceservice.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    
    private final AttendanceRepository attendanceRepository;
    
    public AttendanceResponseDto markAttendance(AttendanceRequestDto request) {
        Attendance attendance = Attendance.builder()
                .studentId(request.getStudentId())
                .classId(request.getClassId())
                .subjectId(request.getSubjectId())
                .teacherId(request.getTeacherId())
                .attendanceDate(request.getAttendanceDate())
                .status(request.getStatus())
                .remarks(request.getRemarks())
                .build();
        
        Attendance saved = attendanceRepository.save(attendance);
        return mapToResponseDto(saved);
    }
    
    public List<AttendanceResponseDto> getAllAttendance() {
        return attendanceRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    public List<AttendanceResponseDto> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    public List<AttendanceResponseDto> getAttendanceByClass(Long classId) {
        return attendanceRepository.findByClassId(classId)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    public List<AttendanceResponseDto> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByAttendanceDate(date)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    public AttendanceResponseDto updateAttendance(Long id, AttendanceRequestDto request) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance record not found"));
        
        attendance.setStatus(request.getStatus());
        attendance.setRemarks(request.getRemarks());
        
        Attendance updated = attendanceRepository.save(attendance);
        return mapToResponseDto(updated);
    }
    
    public void deleteAttendance(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("Attendance record not found");
        }
        attendanceRepository.deleteById(id);
    }
    
    private AttendanceResponseDto mapToResponseDto(Attendance attendance) {
        return AttendanceResponseDto.builder()
                .id(attendance.getId())
                .studentId(attendance.getStudentId())
                .classId(attendance.getClassId())
                .subjectId(attendance.getSubjectId())
                .teacherId(attendance.getTeacherId())
                .attendanceDate(attendance.getAttendanceDate())
                .status(attendance.getStatus())
                .remarks(attendance.getRemarks())
                .build();
    }
}