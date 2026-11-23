package com.example.sms.service;

import com.example.sms.dto.EnrollmentDto;
import com.example.sms.dto.StudentDto;

import java.util.List;

public interface StudentService {
    EnrollmentDto enrollStudentInCourse(Long studentId, Long courseId);
    StudentDto createStudent(StudentDto studentDto);
    StudentDto getStudentById(Long studentId);
    List<StudentDto> getAllStudents();
    StudentDto updateStudent(Long studentId, StudentDto updatedStudent);
    void deleteStudent(Long studentId);
}
