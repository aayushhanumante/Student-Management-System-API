package com.student.student_api.controller;

import com.student.student_api.dto.AttendanceDTO;
import com.student.student_api.dto.StudentDTO;
import com.student.student_api.service.AttendanceService;
import com.student.student_api.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final StudentService studentService;

    public AttendanceController(AttendanceService attendanceService, StudentService studentService) {
        this.attendanceService = attendanceService;
        this.studentService = studentService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping
    public ResponseEntity<AttendanceDTO> recordAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        AttendanceDTO created = attendanceService.markAttendance(attendanceDTO);
        if (created == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(created);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/me")
    public ResponseEntity<List<AttendanceDTO>> getMyAttendance(Principal principal) {
        StudentDTO student = studentService.getStudentByUsername(principal.getName());
        if (student == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(attendanceService.getAttendanceForStudent(student.getId()));
    }
}