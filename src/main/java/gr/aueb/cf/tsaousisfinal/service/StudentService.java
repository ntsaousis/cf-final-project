package gr.aueb.cf.tsaousisfinal.service;

import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectInvalidArgumentException;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppServerException;
import gr.aueb.cf.tsaousisfinal.dto.StudentInsertDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentUpdateDTO;
import gr.aueb.cf.tsaousisfinal.mapper.Mapper;
import gr.aueb.cf.tsaousisfinal.model.Student;
import gr.aueb.cf.tsaousisfinal.model.static_data.Room;
import gr.aueb.cf.tsaousisfinal.repositories.RoomRepository;
import gr.aueb.cf.tsaousisfinal.repositories.StudentRepository;
import gr.aueb.cf.tsaousisfinal.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public StudentReadOnlyDTO createStudent(StudentInsertDTO studentInsertDTO) throws AppObjectAlreadyExists {
        // Check if username already exists
        if (userRepository.findByUsername(studentInsertDTO.getUser().getUsername()).isPresent()) {
            throw new AppObjectAlreadyExists("STUDENT", "Student with username: " + studentInsertDTO.getUser().getUsername() + " already exists.");
        }

        // Check if VAT already exists
        if (userRepository.findByVat(studentInsertDTO.getUser().getVat()).isPresent()) {
            throw new AppObjectAlreadyExists("STUDENT", "Student with VAT: " + studentInsertDTO.getUser().getVat() + " already exists");
        }

        if (userRepository.findByEmail(studentInsertDTO.getUser().getEmail()).isPresent()) {
            throw new AppObjectAlreadyExists("STUDENT", "Student with email: " + studentInsertDTO.getUser().getEmail() + " already exists");
        }

        // Map DTO to entity
        Student student = mapper.mapToStudentEntity(studentInsertDTO);

        // Encrypt password
        student.getUser().setPassword(passwordEncoder.encode(studentInsertDTO.getUser().getPassword()));

        // Save student
        student = studentRepository.save(student);

        // Return read-only DTO
        return mapper.mapToStudentReadOnlyDTO(student);
    }

    @Transactional(rollbackFor = Exception.class)
    public Page<StudentReadOnlyDTO> getPaginatedStudents(int page, int size) {
        String defaultSort = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.by(defaultSort).ascending());
        return studentRepository.findAll(pageable).map(mapper::mapToStudentReadOnlyDTO);
    }


    @Transactional(readOnly = true)
    public StudentReadOnlyDTO getStudentById(Long id) throws AppObjectNotFoundException {
        Student student = studentRepository.findByUserId(id)
                .orElseThrow(() -> new AppObjectNotFoundException("STUDENT", "Student not found with ID: " + id));

        return mapper.mapToStudentReadOnlyDTO(student);
    }

    @Transactional(rollbackFor = Exception.class)
    public StudentReadOnlyDTO getStudentByUuid(String uuid) throws AppObjectNotFoundException {
        Student student = studentRepository.findByUuid(uuid)
                .orElseThrow(() -> new AppObjectNotFoundException("STUDENT", "Student not found found" + uuid));
        return mapper.mapToStudentReadOnlyDTO(student);
    }

    @Transactional(readOnly = true)
    public List<StudentReadOnlyDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(mapper::mapToStudentReadOnlyDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public StudentReadOnlyDTO getStudentByUsername(String username) throws AppObjectNotFoundException {
        Student student = studentRepository.findByUserUsername(username)
                .orElseThrow(() -> new AppObjectNotFoundException("STUDENT", "Student not found with username: " + username));

        return mapper.mapToStudentReadOnlyDTO(student);
    }

    @Transactional(rollbackFor = Exception.class)
    public StudentReadOnlyDTO updateStudent(Long id, StudentUpdateDTO studentUpdateDTO)
            throws AppObjectNotFoundException, AppObjectInvalidArgumentException, AppObjectAlreadyExists {


        Student student = studentRepository.findByUserId(id)
                .orElseThrow(() -> new AppObjectNotFoundException("STUDENT", "Student not found with ID: " + id));

        if (userRepository.existsByEmail(studentUpdateDTO.getUser().getEmail())) {
            throw new AppObjectAlreadyExists("Student", "Email already exists");
        }
        student.getUser().setEmail(studentUpdateDTO.getUser().getEmail());


        // Save changes
        Student updatedStudent = studentRepository.save(student);
        return mapper.mapToStudentReadOnlyDTO(updatedStudent);
    }

    /**
     * Fetches a list of all students assigned to a specific room.
     *
     * @param roomId the ID of the room
     * @return List of StudentReadOnlyDTO with details of the students
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<StudentReadOnlyDTO> getStudentsInRoom(Long roomId) throws AppObjectNotFoundException {
        LOGGER.info("Fetching students in room with ID {}", roomId);

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppObjectNotFoundException("ROOM", "Room with ID " + roomId + " not found."));

        List<StudentReadOnlyDTO> students = room.getStudents().stream()
                .map(mapper::mapToStudentReadOnlyDTO)
                .peek(dto -> dto.getRoom().setRoomId(roomId)) // Ensure roomId is set
                .collect(Collectors.toList());

        LOGGER.info("Found {} students in room with ID {}", students.size(), roomId);
        return students;
    }
}




