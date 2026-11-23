package com.example.sms.mapper;

import com.example.sms.dto.EnrollmentDto;
import com.example.sms.entity.Enrollment;

public class EnrollmentMapper {

    public static EnrollmentDto mapToDto(Enrollment enrollment) {
        String fullName = enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName();

        return new EnrollmentDto(
                enrollment.getId(),
                enrollment.getStudent().getId(),
                fullName,
                enrollment.getCourse().getId(),
                enrollment.getCourse().getTitle(),
                enrollment.getEnrollmentDate()
        );
    }
}
