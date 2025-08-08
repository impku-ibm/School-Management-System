package com.schoolmanagement.subjectservice.repository;

import com.schoolmanagement.subjectservice.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByGradeLevel(Integer gradeLevel);
    boolean existsByCode(String code);
    boolean existsByName(String name);
}