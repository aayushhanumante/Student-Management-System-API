package com.student.student_api.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class AttendanceDTO {
    private Long id;
    private LocalDate date;
    private String status;
    private Long studentId;
    private Long courseId;
}