package com.schoolmanagement.studentservice.service.impl;

import com.schoolmanagement.studentservice.dtos.StudentRequestDto;
import com.schoolmanagement.studentservice.dtos.StudentResponseDto;
import com.schoolmanagement.studentservice.entity.Student;
import com.schoolmanagement.studentservice.exceptions.ResourceNotFoundException;
import com.schoolmanagement.studentservice.repository.StudentRepository;
import com.schoolmanagement.studentservice.service.StudentService;
import com.schoolmanagement.studentservice.utils.StudentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
   private final StudentRepository studentRepository;
  private final StudentMapper studentMapper;

   @Override
   public StudentResponseDto createStudent(StudentRequestDto dto) {
      Student student = studentMapper.toEntity(dto);
      Student savedstudent = studentRepository.save(student);
      return studentMapper.toResponseDto(savedstudent);
   }

   @Override
   public List<StudentResponseDto> getAllStudents() {
      return studentRepository.findAll()
                              .stream()
                              .map(studentMapper::toResponseDto)
                              .collect(Collectors.toList());
   }

   @Override
   public StudentResponseDto getStudentById(Long id) {
      Student student = studentRepository.findById(id)
                                         .orElseThrow(() -> new RuntimeException("Student not found"));
      return studentMapper.toResponseDto(student);
   }

   @Override
   public StudentResponseDto updateStudent(Long id, StudentRequestDto dto) {
      Student existing = studentRepository.findById(id)
                                          .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
      studentMapper.updateStudentFromDto(dto, existing); // Optional for partial update
      Student updated = studentRepository.save(existing);
      return studentMapper.toResponseDto(updated);
   }

   @Override
   public void deleteStudent(Long id) {
      studentRepository.deleteById(id);
   }
}
