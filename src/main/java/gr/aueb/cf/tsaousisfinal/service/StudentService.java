package gr.aueb.cf.tsaousisfinal.service;

import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectInvalidArgumentException;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppServerException;
import gr.aueb.cf.tsaousisfinal.dto.StudentInsertDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.mapper.Mapper;
import gr.aueb.cf.tsaousisfinal.model.Student;
import gr.aueb.cf.tsaousisfinal.repositories.StudentRepository;
import gr.aueb.cf.tsaousisfinal.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public StudentReadOnlyDTO createStudent(StudentInsertDTO studentInsertDTO) throws AppObjectAlreadyExists, IOException {
        // Check if username already exists
        userRepository.findByUsername(studentInsertDTO.getUser().getUsername())
                .ifPresent(user -> {
                    try {
                        throw new AppObjectAlreadyExists("USERNAME", "Username already exists: " + user.getUsername());
                    } catch (AppObjectAlreadyExists e) {
                        throw new RuntimeException(e);
                    }
                });

        // Check if VAT already exists
        userRepository.findByVat(studentInsertDTO.getUser().getVat())
                .ifPresent(user -> {
                    try {
                        throw new AppObjectAlreadyExists("VAT", "VAT already exists: " + user.getVat());
                    } catch (AppObjectAlreadyExists e) {
                        throw new RuntimeException(e);
                    }
                });



        // Map DTO to entity
        Student student = mapper.mapToStudentEntity(studentInsertDTO);

        // Encrypt password
        student.getUser().setPassword(passwordEncoder.encode(studentInsertDTO.getUser().getPassword()));

        // Save student
        student = studentRepository.save(student);

        // Return read-only DTO
        return mapper.mapToStudentReadOnlyDTO(student);
    }

    public Page<StudentReadOnlyDTO> getPaginatedStudents(int page, int size) {
        String defaultSort = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.by(defaultSort).ascending());
        return studentRepository.findAll(pageable).map(mapper::mapToStudentReadOnlyDTO);
    }




    @Transactional(readOnly = true)
    public StudentReadOnlyDTO getStudentById(Long id) throws AppObjectNotFoundException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new AppObjectNotFoundException("STUDENT", "Student not found with ID: " + id));

        return mapper.mapToStudentReadOnlyDTO(student);
    }

    @Transactional(readOnly = true)
    public List<StudentReadOnlyDTO> getAllStudents() throws IOException {
        return studentRepository.findAll().stream()
                .map(mapper::mapToStudentReadOnlyDTO)
                .collect(Collectors.toList());
    }

//    @Transactional
//    public StudentReadOnlyDTO updateStudent(Long id, StudentInsertDTO studentInsertDTO)
//            throws AppObjectNotFoundException, IOException {
//        Student existingStudent = studentRepository.findById(id)
//                .orElseThrow(() -> new AppObjectNotFoundException("STUDENT", "Student not found with ID: " + id));
//
//        // Map new data onto existing student
//        mapper.updateStudentEntityFromDTO(studentInsertDTO, existingStudent);
//
//        // Encrypt password if changed
//        if (studentInsertDTO.getUser().getPassword() != null) {
//            existingStudent.getUser().setPassword(passwordEncoder.encode(studentInsertDTO.getUser().getPassword()));
//        }
//
//        // Save updated student
//        Student updatedStudent = studentRepository.save(existingStudent);
//
//        return mapper.mapToStudentReadOnlyDTO(updatedStudent);
//    }
}

