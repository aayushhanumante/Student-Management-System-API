package com.student.student_api.service;

import com.student.student_api.dto.AttendanceDTO;
import com.student.student_api.entity.Attendance;
import com.student.student_api.entity.AttendanceStatus;
import com.student.student_api.entity.Course;
import com.student.student_api.entity.Student;
import com.student.student_api.repository.AttendanceRepository;
import com.student.student_api.repository.CourseRepository;
import com.student.student_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public AttendanceDTO markAttendance(AttendanceDTO dto) {
        Optional<Student> studentOpt = studentRepository.findById(dto.getStudentId());
        Optional<Course> courseOpt = courseRepository.findById(dto.getCourseId());

        if (studentOpt.isEmpty() || courseOpt.isEmpty()) {
            return null;
        }

        Attendance attendance = new Attendance();
        attendance.setDate(LocalDate.now());
        attendance.setStatus(AttendanceStatus.valueOf(dto.getStatus().toUpperCase()));
        attendance.setStudent(studentOpt.get());
        attendance.setCourse(courseOpt.get());

        Attendance savedAttendance = attendanceRepository.save(attendance);
        return convertToDTO(savedAttendance);
    }

    public List<AttendanceDTO> getAttendanceForStudent(Long studentId) {
        List<Attendance> records = attendanceRepository.findByStudentId(studentId);
        List<AttendanceDTO> dtoList = new ArrayList<>();
        for (Attendance a : records) {
            dtoList.add(convertToDTO(a));
        }
        return dtoList;
    }

    private AttendanceDTO convertToDTO(Attendance attendance) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setDate(attendance.getDate());
        dto.setStatus(attendance.getStatus().name());
        dto.setStudentId(attendance.getStudent().getId());
        dto.setCourseId(attendance.getCourse().getId());
        return dto;
    }
}