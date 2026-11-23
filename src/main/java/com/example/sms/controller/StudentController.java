package com.example.sms.controller;

import com.example.sms.dto.EnrollmentDto;
import com.example.sms.dto.StudentDto;
import com.example.sms.service.StudentService;
import lombok.AllArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
@Validated
public class StudentController {

    private StudentService service;

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody StudentDto studentDto) {
        StudentDto saved = service.createStudent(studentDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable("id") Long studentId) {
        StudentDto studentDto = service.getStudentById(studentId);
        return ResponseEntity.ok(studentDto);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> students = service.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PutMapping("{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable("id") Long studentId,
                                                    @Valid @RequestBody StudentDto updatedStudent) {
        StudentDto updated = service.updateStudent(studentId, updatedStudent);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long studentId) {
        service.deleteStudent(studentId);
        return ResponseEntity.ok("Successfully deleted");
    }

    @PostMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<EnrollmentDto> enrollStudent(
            @PathVariable @Positive Long studentId,
            @PathVariable @Positive Long courseId) {
        return new ResponseEntity<>(
                service.enrollStudentInCourse(studentId, courseId),
                HttpStatus.CREATED
        );
    }
}
