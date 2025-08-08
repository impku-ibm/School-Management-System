package com.schoolmanagement.attendanceservice.controller;

import com.schoolmanagement.attendanceservice.dto.AttendanceRequestDto;
import com.schoolmanagement.attendanceservice.dto.AttendanceResponseDto;
import com.schoolmanagement.attendanceservice.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    
    private final AttendanceService attendanceService;
    
    @PostMapping
    public ResponseEntity<AttendanceResponseDto> markAttendance(@RequestBody AttendanceRequestDto request) {
        AttendanceResponseDto response = attendanceService.markAttendance(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<AttendanceResponseDto>> getAllAttendance() {
        List<AttendanceResponseDto> attendance = attendanceService.getAllAttendance();
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByStudent(@PathVariable Long studentId) {
        List<AttendanceResponseDto> attendance = attendanceService.getAttendanceByStudent(studentId);
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByClass(@PathVariable Long classId) {
        List<AttendanceResponseDto> attendance = attendanceService.getAttendanceByClass(classId);
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByDate(@PathVariable LocalDate date) {
        List<AttendanceResponseDto> attendance = attendanceService.getAttendanceByDate(date);
        return ResponseEntity.ok(attendance);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AttendanceResponseDto> updateAttendance(@PathVariable Long id, @RequestBody AttendanceRequestDto request) {
        AttendanceResponseDto response = attendanceService.updateAttendance(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}