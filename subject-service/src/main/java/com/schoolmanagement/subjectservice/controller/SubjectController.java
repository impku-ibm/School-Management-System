package com.schoolmanagement.subjectservice.controller;

import com.schoolmanagement.subjectservice.dto.SubjectRequestDto;
import com.schoolmanagement.subjectservice.dto.SubjectResponseDto;
import com.schoolmanagement.subjectservice.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {
    
    private final SubjectService subjectService;
    
    @PostMapping
    public ResponseEntity<SubjectResponseDto> createSubject(@RequestBody SubjectRequestDto request) {
        SubjectResponseDto response = subjectService.createSubject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<SubjectResponseDto>> getAllSubjects() {
        List<SubjectResponseDto> subjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDto> getSubjectById(@PathVariable Long id) {
        SubjectResponseDto subject = subjectService.getSubjectById(id);
        return ResponseEntity.ok(subject);
    }
    
    @GetMapping("/grade/{gradeLevel}")
    public ResponseEntity<List<SubjectResponseDto>> getSubjectsByGrade(@PathVariable Integer gradeLevel) {
        List<SubjectResponseDto> subjects = subjectService.getSubjectsByGrade(gradeLevel);
        return ResponseEntity.ok(subjects);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponseDto> updateSubject(@PathVariable Long id, @RequestBody SubjectRequestDto request) {
        SubjectResponseDto response = subjectService.updateSubject(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}