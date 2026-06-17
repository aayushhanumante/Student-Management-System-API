package com.student.student_api.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class EnrollmentDTO {
    private Long id;
    private LocalDate enrollmentDate;
    private Long studentId;
    private Long courseId;
}