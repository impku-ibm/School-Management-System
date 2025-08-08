package com.schoolmanagement.gradeservice.service;

import com.schoolmanagement.gradeservice.dto.GradeRequestDto;
import com.schoolmanagement.gradeservice.dto.GradeResponseDto;
import com.schoolmanagement.gradeservice.entity.Grade;
import com.schoolmanagement.gradeservice.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {
    
    private final GradeRepository gradeRepository;
    
    public GradeResponseDto createGrade(GradeRequestDto request) {
        Grade grade = Grade.builder()
                .studentId(request.getStudentId())
                .subjectId(request.getSubjectId())
                .teacherId(request.getTeacherId())
                .marks(request.getMarks())
                .totalMarks(request.getTotalMarks())
                .examType(request.getExamType())
                .examDate(request.getExamDate())
                .remarks(request.getRemarks())
                .build();
        
        Grade saved = gradeRepository.save(grade);
        return mapToResponseDto(saved);
    }
    
    public List<GradeResponseDto> getAllGrades() {
        return gradeRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    public GradeResponseDto getGradeById(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
        return mapToResponseDto(grade);
    }
    
    public List<GradeResponseDto> getGradesByStudent(Long studentId) {
        return gradeRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    public List<GradeResponseDto> getGradesBySubject(Long subjectId) {
        return gradeRepository.findBySubjectId(subjectId)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    public GradeResponseDto updateGrade(Long id, GradeRequestDto request) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
        
        grade.setStudentId(request.getStudentId());
        grade.setSubjectId(request.getSubjectId());
        grade.setTeacherId(request.getTeacherId());
        grade.setMarks(request.getMarks());
        grade.setTotalMarks(request.getTotalMarks());
        grade.setExamType(request.getExamType());
        grade.setExamDate(request.getExamDate());
        grade.setRemarks(request.getRemarks());
        
        Grade updated = gradeRepository.save(grade);
        return mapToResponseDto(updated);
    }
    
    public void deleteGrade(Long id) {
        if (!gradeRepository.existsById(id)) {
            throw new RuntimeException("Grade not found");
        }
        gradeRepository.deleteById(id);
    }
    
    private GradeResponseDto mapToResponseDto(Grade grade) {
        double percentage = (grade.getMarks() / grade.getTotalMarks()) * 100;
        
        return GradeResponseDto.builder()
                .id(grade.getId())
                .studentId(grade.getStudentId())
                .subjectId(grade.getSubjectId())
                .teacherId(grade.getTeacherId())
                .marks(grade.getMarks())
                .totalMarks(grade.getTotalMarks())
                .percentage(Math.round(percentage * 100.0) / 100.0)
                .examType(grade.getExamType())
                .examDate(grade.getExamDate())
                .remarks(grade.getRemarks())
                .build();
    }
}