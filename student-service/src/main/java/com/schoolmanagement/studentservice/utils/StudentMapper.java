package com.schoolmanagement.studentservice.utils;

import com.schoolmanagement.studentservice.dtos.StudentRequestDto;
import com.schoolmanagement.studentservice.dtos.StudentResponseDto;
import com.schoolmanagement.studentservice.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {
   Student toEntity(StudentRequestDto dto);
   StudentResponseDto toResponseDto(Student student);
   void updateStudentFromDto(StudentRequestDto dto, @MappingTarget Student student);
}

