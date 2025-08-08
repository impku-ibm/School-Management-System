package com.schoolmanagement.studentservice.service;

import com.schoolmanagement.studentservice.dtos.StudentRequestDto;
import com.schoolmanagement.studentservice.dtos.StudentResponseDto;

import java.util.List;

public interface StudentService {
   StudentResponseDto createStudent(StudentRequestDto dto);
   List<StudentResponseDto> getAllStudents();
   StudentResponseDto getStudentById(Long id);
   StudentResponseDto updateStudent(Long id, StudentRequestDto dto);
   void deleteStudent(Long id);
}
