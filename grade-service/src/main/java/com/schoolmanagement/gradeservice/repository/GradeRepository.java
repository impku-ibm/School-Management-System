package com.schoolmanagement.gradeservice.repository;

import com.schoolmanagement.gradeservice.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentId(Long studentId);
    List<Grade> findBySubjectId(Long subjectId);
    List<Grade> findByTeacherId(Long teacherId);
    List<Grade> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
}