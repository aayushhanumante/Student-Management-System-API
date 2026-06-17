package com.student.student_api.service;

import com.student.student_api.dto.EnrollmentDTO;
import com.student.student_api.entity.Course;
import com.student.student_api.entity.Enrollment;
import com.student.student_api.entity.Student;
import com.student.student_api.repository.CourseRepository;
import com.student.student_api.repository.EnrollmentRepository;
import com.student.student_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public EnrollmentDTO enrollStudent(EnrollmentDTO dto) {
        Optional<Student> studentOpt = studentRepository.findById(dto.getStudentId());
        Optional<Course> courseOpt = courseRepository.findById(dto.getCourseId());

        if (studentOpt.isEmpty() || courseOpt.isEmpty()) {
            return null;
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStudent(studentOpt.get());
        enrollment.setCourse(courseOpt.get());

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return convertToDTO(savedEnrollment);
    }

    public List<EnrollmentDTO> getEnrollmentsForStudent(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        List<EnrollmentDTO> dtoList = new ArrayList<>();
        for (Enrollment e : enrollments) {
            dtoList.add(convertToDTO(e));
        }
        return dtoList;
    }

    private EnrollmentDTO convertToDTO(Enrollment enrollment) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(enrollment.getId());
        dto.setEnrollmentDate(enrollment.getEnrollmentDate());
        dto.setStudentId(enrollment.getStudent().getId());
        dto.setCourseId(enrollment.getCourse().getId());
        return dto;
    }
}