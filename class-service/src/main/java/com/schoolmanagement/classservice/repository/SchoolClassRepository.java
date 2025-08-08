package com.schoolmanagement.classservice.repository;

import com.schoolmanagement.classservice.entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    List<SchoolClass> findByGradeLevel(Integer gradeLevel);
    List<SchoolClass> findByClassTeacherId(Long classTeacherId);
}