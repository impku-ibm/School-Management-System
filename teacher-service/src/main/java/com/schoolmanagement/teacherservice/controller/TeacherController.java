package com.schoolmanagement.teacherservice.controller;

import com.schoolmanagement.teacherservice.dto.TeacherRequestDto;
import com.schoolmanagement.teacherservice.dto.TeacherResponseDto;
import com.schoolmanagement.teacherservice.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {
    
    private final TeacherService teacherService;
    
    @PostMapping
    public ResponseEntity<TeacherResponseDto> createTeacher(@RequestBody TeacherRequestDto request) {
        TeacherResponseDto response = teacherService.createTeacher(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<TeacherResponseDto>> getAllTeachers() {
        List<TeacherResponseDto> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> getTeacherById(@PathVariable Long id) {
        TeacherResponseDto teacher = teacherService.getTeacherById(id);
        return ResponseEntity.ok(teacher);
    }
    
    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<TeacherResponseDto>> getTeachersBySpecialization(@PathVariable String specialization) {
        List<TeacherResponseDto> teachers = teacherService.getTeachersBySpecialization(specialization);
        return ResponseEntity.ok(teachers);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> updateTeacher(@PathVariable Long id, @RequestBody TeacherRequestDto request) {
        TeacherResponseDto response = teacherService.updateTeacher(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}