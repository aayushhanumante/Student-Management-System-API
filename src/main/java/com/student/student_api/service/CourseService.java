package com.student.student_api.service;

import com.student.student_api.dto.CourseDTO;
import com.student.student_api.entity.Course;
import com.student.student_api.entity.Teacher;
import com.student.student_api.repository.CourseRepository;
import com.student.student_api.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public CourseService(CourseRepository courseRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    public CourseDTO createCourse(CourseDTO dto) {
        Optional<Teacher> teacherOpt = teacherRepository.findById(dto.getTeacherId());
        if (teacherOpt.isEmpty()) return null;

        Course course = new Course();
        course.setCourseCode(dto.getCourseCode());
        course.setTitle(dto.getTitle());
        course.setCredits(dto.getCredits());
        course.setTeacher(teacherOpt.get());

        Course savedCourse = courseRepository.save(course);
        return convertToDTO(savedCourse);
    }

    public CourseDTO updateCourse(Long id, CourseDTO dto) {
        Optional<Course> courseOpt = courseRepository.findById(id);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            course.setCourseCode(dto.getCourseCode());
            course.setTitle(dto.getTitle());
            course.setCredits(dto.getCredits());
            return convertToDTO(courseRepository.save(course));
        }
        return null;
    }

    public boolean deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setCourseCode(course.getCourseCode());
        dto.setTitle(course.getTitle());
        dto.setCredits(course.getCredits());
        if (course.getTeacher() != null) {
            dto.setTeacherId(course.getTeacher().getId());
        }
        return dto;
    }
}