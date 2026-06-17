package com.student.student_api.controller;

import com.student.student_api.dto.EnrollmentDTO;
import com.student.student_api.dto.StudentDTO;
import com.student.student_api.service.EnrollmentService;
import com.student.student_api.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    private final StudentService studentService;

    public EnrollmentController(EnrollmentService enrollmentService, StudentService studentService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping
    public ResponseEntity<EnrollmentDTO> createEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
        EnrollmentDTO created = enrollmentService.enrollStudent(enrollmentDTO);
        if (created == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(created);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/me")
    public ResponseEntity<List<EnrollmentDTO>> getMyEnrollments(Principal principal) {
        StudentDTO student = studentService.getStudentByUsername(principal.getName());
        if (student == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(enrollmentService.getEnrollmentsForStudent(student.getId()));
    }
}