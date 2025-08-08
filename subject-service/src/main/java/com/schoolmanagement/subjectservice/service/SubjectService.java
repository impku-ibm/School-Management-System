package com.schoolmanagement.subjectservice.service;

import com.schoolmanagement.subjectservice.dto.SubjectRequestDto;
import com.schoolmanagement.subjectservice.dto.SubjectResponseDto;
import com.schoolmanagement.subjectservice.entity.Subject;
import com.schoolmanagement.subjectservice.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    
    private final SubjectRepository subjectRepository;
    
    public SubjectResponseDto createSubject(SubjectRequestDto request) {
        if (subjectRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Subject code already exists");
        }
        if (subjectRepository.existsByName(request.getName())) {
            throw new RuntimeException("Subject name already exists");
        }
        
        Subject subject = Subject.builder()
                .name(request.getName())
                .code(request.getCode())
                .description(request.getDescription())
                .gradeLevel(request.getGradeLevel())
                .build();
        
        Subject saved = subjectRepository.save(subject);
        return mapToResponseDto(saved);
    }
    
    public List<SubjectResponseDto> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    public SubjectResponseDto getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        return mapToResponseDto(subject);
    }
    
    public List<SubjectResponseDto> getSubjectsByGrade(Integer gradeLevel) {
        return subjectRepository.findByGradeLevel(gradeLevel)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    public SubjectResponseDto updateSubject(Long id, SubjectRequestDto request) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        
        subject.setName(request.getName());
        subject.setCode(request.getCode());
        subject.setDescription(request.getDescription());
        subject.setGradeLevel(request.getGradeLevel());
        
        Subject updated = subjectRepository.save(subject);
        return mapToResponseDto(updated);
    }
    
    public void deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new RuntimeException("Subject not found");
        }
        subjectRepository.deleteById(id);
    }
    
    private SubjectResponseDto mapToResponseDto(Subject subject) {
        return SubjectResponseDto.builder()
                .id(subject.getId())
                .name(subject.getName())
                .code(subject.getCode())
                .description(subject.getDescription())
                .gradeLevel(subject.getGradeLevel())
                .build();
    }
}