package gr.aueb.cf.tsaousisfinal.service;

import gr.aueb.cf.tsaousisfinal.dto.StudentInsertDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.dto.UpdateStudentDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.model.Student;
import gr.aueb.cf.tsaousisfinal.model.User;
import gr.aueb.cf.tsaousisfinal.repositories.StudentRepository;
import gr.aueb.cf.tsaousisfinal.repositories.UserRepository;
import gr.aueb.cf.tsaousisfinal.core.exceptions.*;
import gr.aueb.cf.tsaousisfinal.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing Student entities.
 */
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public StudentReadOnlyDTO createStudent(StudentInsertDTO studentInsertDTO) throws AppObjectAlreadyExists, IOException {
        if (userRepository.findByUsername(studentInsertDTO.getUser().getUsername()).isPresent()) {
            throw new AppObjectAlreadyExists("USERNAME","Username already exists: " + studentInsertDTO.getUser().getUsername());
        }
        if (userRepository.findByVat(studentInsertDTO.getUser().getVat()).isPresent()) {
            throw new AppObjectAlreadyExists("VAT","VAT already exists: " + studentInsertDTO.getUser().getVat());
        }

        Student student = mapper.mapToStudentEntity(studentInsertDTO);
        student.getUser().setPassword(passwordEncoder.encode(studentInsertDTO.getUser().getPassword()));
        student = studentRepository.save(student);
        return mapper.mapToStudentReadOnlyDTO(student);
    }

    @Transactional(readOnly = true)
    public StudentReadOnlyDTO getStudentById(Long id) throws AppObjectNotFoundException, IOException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new AppObjectNotFoundException("STUDENT","Student not found with id: " + id));
        return mapper.mapToStudentReadOnlyDTO(student);
    }

    @Transactional
    public StudentReadOnlyDTO updateStudent(Long id, StudentInsertDTO studentInsertDTO) throws AppObjectNotFoundException, IOException {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new AppObjectNotFoundException("STUDENT","Student not found with id: " + id));

        User user = userRepository.findByUsername(studentInsertDTO.getUser().getUsername())
                .orElseThrow(() -> new AppObjectNotFoundException("USER","User not found with username: " + studentInsertDTO.getUser().getUsername()));

        Student updatedStudent = mapper.mapToStudentEntity(studentInsertDTO);
        updatedStudent.setId(existingStudent.getId());
        updatedStudent.setUser(user);
        studentRepository.save(updatedStudent);
        return mapper.mapToStudentReadOnlyDTO(updatedStudent);
    }

}
