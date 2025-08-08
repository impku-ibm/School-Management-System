package com.schoolmanagement.teacherservice.service;

import com.schoolmanagement.teacherservice.dto.TeacherRequestDto;
import com.schoolmanagement.teacherservice.dto.TeacherResponseDto;
import com.schoolmanagement.teacherservice.entity.Teacher;
import com.schoolmanagement.teacherservice.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    
    private final TeacherRepository teacherRepository;
    
    public TeacherResponseDto createTeacher(TeacherRequestDto request) {
        if (teacherRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Teacher email already exists");
        }
        
        Teacher teacher = Teacher.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .qualification(request.getQualification())
                .specialization(request.getSpecialization())
                .joiningDate(request.getJoiningDate())
                .address(request.getAddress())
                .build();
        
        Teacher saved = teacherRepository.save(teacher);
        return mapToResponseDto(saved);
    }
    
    public List<TeacherResponseDto> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    public TeacherResponseDto getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        return mapToResponseDto(teacher);
    }
    
    public List<TeacherResponseDto> getTeachersBySpecialization(String specialization) {
        return teacherRepository.findBySpecialization(specialization)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    public TeacherResponseDto updateTeacher(Long id, TeacherRequestDto request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setPhone(request.getPhone());
        teacher.setQualification(request.getQualification());
        teacher.setSpecialization(request.getSpecialization());
        teacher.setJoiningDate(request.getJoiningDate());
        teacher.setAddress(request.getAddress());
        
        Teacher updated = teacherRepository.save(teacher);
        return mapToResponseDto(updated);
    }
    
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new RuntimeException("Teacher not found");
        }
        teacherRepository.deleteById(id);
    }
    
    private TeacherResponseDto mapToResponseDto(Teacher teacher) {
        return TeacherResponseDto.builder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .email(teacher.getEmail())
                .phone(teacher.getPhone())
                .qualification(teacher.getQualification())
                .specialization(teacher.getSpecialization())
                .joiningDate(teacher.getJoiningDate())
                .address(teacher.getAddress())
                .build();
    }
}