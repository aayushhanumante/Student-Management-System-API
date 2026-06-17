package com.student.student_api.service;

import com.student.student_api.dto.StudentDTO;
import com.student.student_api.entity.Role;
import com.student.student_api.entity.Student;
import com.student.student_api.entity.User;
import com.student.student_api.repository.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public StudentDTO createStudent(StudentDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.STUDENT);

        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());
        student.setUser(user);

        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    public StudentDTO updateStudent(Long id, StudentDTO dto) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setName(dto.getName());
            student.setEmail(dto.getEmail());
            student.setAge(dto.getAge());
            return convertToDTO(studentRepository.save(student));
        }
        return null;
    }

    public boolean deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> dtoList = new ArrayList<>();
        for (Student s : students) {
            dtoList.add(convertToDTO(s));
        }
        return dtoList;
    }

    public StudentDTO getStudentByUsername(String username) {
        Optional<Student> studentOpt = studentRepository.findByUserUsername(username);
        if (studentOpt.isPresent()) {
            return convertToDTO(studentOpt.get());
        }
        return null;
    }

    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setAge(student.getAge());

        if (student.getUser() != null) {
            dto.setUsername(student.getUser().getUsername());
        } else {
            dto.setUsername("NO_ASSOCIATED_USER");
        }

        return dto;
    }
}