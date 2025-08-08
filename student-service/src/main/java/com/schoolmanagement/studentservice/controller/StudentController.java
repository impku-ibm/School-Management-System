package com.schoolmanagement.studentservice.controller;

import com.schoolmanagement.studentservice.dtos.StudentRequestDto;
import com.schoolmanagement.studentservice.dtos.StudentResponseDto;
import com.schoolmanagement.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

   private final StudentService studentService;

   @PostMapping
   public ResponseEntity<StudentResponseDto> create(@RequestBody StudentRequestDto dto) {
      return new ResponseEntity<>(studentService.createStudent(dto), HttpStatus.CREATED);
   }

   @GetMapping
   public ResponseEntity<List<StudentResponseDto>> getAll() {
      return ResponseEntity.ok(studentService.getAllStudents());
   }

   @GetMapping("/{id}")
   public ResponseEntity<StudentResponseDto> getById(@PathVariable Long id) {
      return ResponseEntity.ok(studentService.getStudentById(id));
   }

   @PutMapping("/{id}")
   public ResponseEntity<StudentResponseDto> update(@PathVariable Long id, @RequestBody StudentRequestDto dto) {
      return ResponseEntity.ok(studentService.updateStudent(id, dto));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      studentService.deleteStudent(id);
      return ResponseEntity.noContent().build();
   }
}
