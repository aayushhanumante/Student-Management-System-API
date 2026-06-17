package com.student.student_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDTO {
    private Long id;
    private String courseCode;
    private String title;
    private int credits;
    private Long teacherId;
}