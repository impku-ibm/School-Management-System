package com.schoolmanagement.gradeservice.controller;

import com.schoolmanagement.gradeservice.dto.GradeRequestDto;
import com.schoolmanagement.gradeservice.dto.GradeResponseDto;
import com.schoolmanagement.gradeservice.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {
    
    private final GradeService gradeService;
    
    @PostMapping
    public ResponseEntity<GradeResponseDto> createGrade(@RequestBody GradeRequestDto request) {
        GradeResponseDto response = gradeService.createGrade(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<GradeResponseDto>> getAllGrades() {
        List<GradeResponseDto> grades = gradeService.getAllGrades();
        return ResponseEntity.ok(grades);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GradeResponseDto> getGradeById(@PathVariable Long id) {
        GradeResponseDto grade = gradeService.getGradeById(id);
        return ResponseEntity.ok(grade);
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeResponseDto>> getGradesByStudent(@PathVariable Long studentId) {
        List<GradeResponseDto> grades = gradeService.getGradesByStudent(studentId);
        return ResponseEntity.ok(grades);
    }
    
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<GradeResponseDto>> getGradesBySubject(@PathVariable Long subjectId) {
        List<GradeResponseDto> grades = gradeService.getGradesBySubject(subjectId);
        return ResponseEntity.ok(grades);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<GradeResponseDto> updateGrade(@PathVariable Long id, @RequestBody GradeRequestDto request) {
        GradeResponseDto response = gradeService.updateGrade(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }
}