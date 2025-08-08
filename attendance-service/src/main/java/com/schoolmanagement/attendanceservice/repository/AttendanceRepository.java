package com.schoolmanagement.attendanceservice.repository;

import com.schoolmanagement.attendanceservice.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findByClassId(Long classId);
    List<Attendance> findByAttendanceDate(LocalDate date);
    List<Attendance> findByStudentIdAndAttendanceDate(Long studentId, LocalDate date);
    List<Attendance> findByClassIdAndAttendanceDate(Long classId, LocalDate date);
}