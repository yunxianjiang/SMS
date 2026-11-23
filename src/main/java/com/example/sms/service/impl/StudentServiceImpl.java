package com.example.sms.service.impl;

import com.example.sms.dto.EnrollmentDto;
import com.example.sms.dto.StudentDto;
import com.example.sms.entity.Course;
import com.example.sms.entity.Enrollment;
import com.example.sms.entity.Student;
import com.example.sms.exception.ResourceNotFoundException;
import com.example.sms.mapper.EnrollmentMapper;
import com.example.sms.mapper.StudentMapper;
import com.example.sms.repository.CourseRepository;
import com.example.sms.repository.EnrollmentRepository;
import com.example.sms.repository.StudentRepository;
import com.example.sms.service.StudentService;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepo;
    private CourseRepository courseRepo;
    private EnrollmentRepository enrollmentRepo;

    @Override
    @Transactional
    public EnrollmentDto enrollStudentInCourse(Long studentId, Long courseId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (course.getAvailableSeats() <= 0) {
            throw new IllegalStateException("Course is full");
        }

        course.setAvailableSeats(course.getAvailableSeats() - 1);
        courseRepo.save(course);
        Enrollment enrollment = new Enrollment(student, course, LocalDate.now());
        Enrollment savedEnrollment = enrollmentRepo.save(enrollment);

        return EnrollmentMapper.mapToDto(savedEnrollment);
    }


    @Override
    @Transactional
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = StudentMapper.mapToStudent(studentDto);
        Student saved = studentRepo.save(student);
        return StudentMapper.mapToStudentDto(saved);
    }

    @Override
    public StudentDto getStudentById(Long studentId) {
        Student student = studentRepo.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Not exist"));
        return StudentMapper.mapToStudentDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepo.findAll();
        return students.stream()
                .map(StudentMapper::mapToStudentDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentDto updateStudent(Long studentId, StudentDto updatedStudent) {
        Student student = studentRepo.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Not exist"));
        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setEmail(updatedStudent.getEmail());
        Student saved = studentRepo.save(student);
        return StudentMapper.mapToStudentDto(saved);
    }

    @Override
    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = studentRepo.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Not exist"));
        studentRepo.delete(student);
    }
}
