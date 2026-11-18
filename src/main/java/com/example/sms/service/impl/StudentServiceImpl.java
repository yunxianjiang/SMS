package com.example.sms.service.impl;

import com.example.sms.dto.StudentDto;
import com.example.sms.entity.Student;
import com.example.sms.exception.ResourceNotFoundException;
import com.example.sms.mapper.StudentMapper;
import com.example.sms.repository.StudentRepository;
import com.example.sms.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository repo;

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = StudentMapper.mapToStudent(studentDto);
        Student saved = repo.save(student);
        return StudentMapper.mapToStudentDto(saved);
    }

    @Override
    public StudentDto getStudentById(Long studentId) {
        Student student = repo.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Not exist"));
        return StudentMapper.mapToStudentDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = repo.findAll();
        return students.stream()
                .map(StudentMapper::mapToStudentDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto updateStudent(Long studentId, StudentDto updatedStudent) {
        Student student = repo.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Not exist"));
        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setEmail(updatedStudent.getEmail());
        Student saved = repo.save(student);
        return StudentMapper.mapToStudentDto(saved);
    }

    @Override
    public void deleteStudent(Long studentId) {
        Student student = repo.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Not exist"));
        repo.delete(student);
    }
}
