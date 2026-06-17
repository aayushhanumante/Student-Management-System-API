package com.student.student_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDTO {
    private Long id;
    private String name;
    private String email;
    private String department;
    private String username;
    private String password;
}