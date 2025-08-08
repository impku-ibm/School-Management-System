package com.schoolmanagement.classservice.service;

import com.schoolmanagement.classservice.dto.ClassRequestDto;
import com.schoolmanagement.classservice.dto.ClassResponseDto;
import com.schoolmanagement.classservice.entity.SchoolClass;
import com.schoolmanagement.classservice.repository.SchoolClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassService {
    
    private final SchoolClassRepository classRepository;
    
    public ClassResponseDto createClass(ClassRequestDto request) {
        SchoolClass schoolClass = SchoolClass.builder()
                .className(request.getClassName())
                .section(request.getSection())
                .gradeLevel(request.getGradeLevel())
                .capacity(request.getCapacity())
                .classTeacherId(request.getClassTeacherId())
                .build();
        
        SchoolClass saved = classRepository.save(schoolClass);
        return mapToResponseDto(saved);
    }
    
    public List<ClassResponseDto> getAllClasses() {
        return classRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    public ClassResponseDto getClassById(Long id) {
        SchoolClass schoolClass = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        return mapToResponseDto(schoolClass);
    }
    
    public List<ClassResponseDto> getClassesByGrade(Integer gradeLevel) {
        return classRepository.findByGradeLevel(gradeLevel)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    public ClassResponseDto updateClass(Long id, ClassRequestDto request) {
        SchoolClass schoolClass = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        
        schoolClass.setClassName(request.getClassName());
        schoolClass.setSection(request.getSection());
        schoolClass.setGradeLevel(request.getGradeLevel());
        schoolClass.setCapacity(request.getCapacity());
        schoolClass.setClassTeacherId(request.getClassTeacherId());
        
        SchoolClass updated = classRepository.save(schoolClass);
        return mapToResponseDto(updated);
    }
    
    public void deleteClass(Long id) {
        if (!classRepository.existsById(id)) {
            throw new RuntimeException("Class not found");
        }
        classRepository.deleteById(id);
    }
    
    private ClassResponseDto mapToResponseDto(SchoolClass schoolClass) {
        return ClassResponseDto.builder()
                .id(schoolClass.getId())
                .className(schoolClass.getClassName())
                .section(schoolClass.getSection())
                .gradeLevel(schoolClass.getGradeLevel())
                .capacity(schoolClass.getCapacity())
                .classTeacherId(schoolClass.getClassTeacherId())
                .build();
    }
}