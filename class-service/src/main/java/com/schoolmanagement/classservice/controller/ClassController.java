package com.schoolmanagement.classservice.controller;

import com.schoolmanagement.classservice.dto.ClassRequestDto;
import com.schoolmanagement.classservice.dto.ClassResponseDto;
import com.schoolmanagement.classservice.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
public class ClassController {
    
    private final ClassService classService;
    
    @PostMapping
    public ResponseEntity<ClassResponseDto> createClass(@RequestBody ClassRequestDto request) {
        ClassResponseDto response = classService.createClass(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<ClassResponseDto>> getAllClasses() {
        List<ClassResponseDto> classes = classService.getAllClasses();
        return ResponseEntity.ok(classes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ClassResponseDto> getClassById(@PathVariable Long id) {
        ClassResponseDto schoolClass = classService.getClassById(id);
        return ResponseEntity.ok(schoolClass);
    }
    
    @GetMapping("/grade/{gradeLevel}")
    public ResponseEntity<List<ClassResponseDto>> getClassesByGrade(@PathVariable Integer gradeLevel) {
        List<ClassResponseDto> classes = classService.getClassesByGrade(gradeLevel);
        return ResponseEntity.ok(classes);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ClassResponseDto> updateClass(@PathVariable Long id, @RequestBody ClassRequestDto request) {
        ClassResponseDto response = classService.updateClass(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }
}