package gr.aueb.cf.tsaousisfinal.service;

import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectInvalidArgumentException;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.tsaousisfinal.dto.RoomReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.dto.WardenInsertDTO;
import gr.aueb.cf.tsaousisfinal.dto.WardenReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.mapper.Mapper;
import gr.aueb.cf.tsaousisfinal.model.Student;
import gr.aueb.cf.tsaousisfinal.model.Warden;
import gr.aueb.cf.tsaousisfinal.model.static_data.Room;
import gr.aueb.cf.tsaousisfinal.repositories.RoomRepository;
import gr.aueb.cf.tsaousisfinal.repositories.StudentRepository;
import gr.aueb.cf.tsaousisfinal.repositories.UserRepository;
import gr.aueb.cf.tsaousisfinal.repositories.WardenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WardenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WardenService.class);

    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final WardenRepository wardenRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Assigns a student to a specific room.
     *
     * @param studentId the ID of the student
     * @param roomId    the ID of the room
     * @return RoomReadOnlyDTO with updated room details
     */
    @Transactional
    public RoomReadOnlyDTO assignStudentToRoom(Long studentId, Long roomId)
            throws AppObjectNotFoundException, AppObjectAlreadyExists {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppObjectNotFoundException("ROOM", "Room with ID " + roomId + " not found."));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppObjectNotFoundException("STUDENT", "Student with ID " + studentId + " not found."));

        if (student.getRoom() != null) {
            throw new AppObjectAlreadyExists("STUDENT", "Student with ID " + studentId + " is already assigned to a room.");
        }

        if (room.getStudents().size() >= room.getRoomCapacity()) {
            throw new AppObjectAlreadyExists("ROOM", "Room with ID " + roomId + " is already full.");
        }

        // Αναθέτουμε τον φοιτητή στο δωμάτιο
        student.setRoom(room);
        room.getStudents().add(student);

        // Μειώνουμε τη χωρητικότητα του δωματίου
        room.setRoomCapacity(room.getRoomCapacity() - 1);

        studentRepository.save(student);
        roomRepository.save(room);

        return mapper.mapToReadOnlyRoomDTO(room);
    }


    @Transactional
    public WardenReadOnlyDTO createWarden(WardenInsertDTO wardenInsertDTO) throws AppObjectAlreadyExists {
        // Check if username already exists
        if (userRepository.findByUsername(wardenInsertDTO.getUser().getUsername()).isPresent()) {
            throw new AppObjectAlreadyExists("USERNAME", "Username already exists: " + wardenInsertDTO.getUser().getUsername());
        }

        // Check if VAT already exists
        if (userRepository.findByVat(wardenInsertDTO.getUser().getVat()).isPresent()) {
            throw new AppObjectAlreadyExists("VAT", "VAT already exists: " + wardenInsertDTO.getUser().getVat());
        }

        // Map DTO to entity
        Warden warden = mapper.mapToWardenEntity(wardenInsertDTO);

        // Encode password
        warden.getUser().setPassword(passwordEncoder.encode(wardenInsertDTO.getUser().getPassword()));

        // Save warden to the database
        Warden savedWarden = wardenRepository.save(warden);

        // Map entity to ReadOnlyDTO and return
        return mapper.mapToWardenReadOnlyDTO(savedWarden);
    }

    /**
     * Removes a student from their assigned room.
     *
     * @param studentId the ID of the student
     * @return StudentReadOnlyDTO with updated student details
     */
    @Transactional
    public StudentReadOnlyDTO removeStudentFromRoom(Long studentId)
            throws AppObjectNotFoundException, AppObjectInvalidArgumentException {
        LOGGER.info("Warden removing student with ID {} from their room", studentId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppObjectNotFoundException("STUDENT", "Student with ID " + studentId + " not found."));

        Room room = student.getRoom();

        if (room == null) {
            throw new AppObjectInvalidArgumentException("STUDENT", "Student with ID " + studentId + " is not assigned to any room.");
        }

        room.getStudents().remove(student);
        student.setRoom(null);
        studentRepository.save(student);

        StudentReadOnlyDTO studentDTO = mapper.mapToStudentReadOnlyDTO(student);
        studentDTO.setRoomId(null); // Explicitly set roomId to null
        LOGGER.info("Student with ID {} successfully removed from room", studentId);
        return studentDTO;
    }

    /**
     * Fetches a list of all students assigned to a specific room.
     *
     * @param roomId the ID of the room
     * @return List of StudentReadOnlyDTO with details of the students
     */
    @Transactional(readOnly = true)
    public List<StudentReadOnlyDTO> getStudentsInRoom(Long roomId) throws AppObjectNotFoundException {
        LOGGER.info("Fetching students in room with ID {}", roomId);

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppObjectNotFoundException("ROOM", "Room with ID " + roomId + " not found."));

        List<StudentReadOnlyDTO> students = room.getStudents().stream()
                .map(mapper::mapToStudentReadOnlyDTO)
                .peek(dto -> dto.setRoomId(roomId)) // Ensure roomId is set
                .collect(Collectors.toList());

        LOGGER.info("Found {} students in room with ID {}", students.size(), roomId);
        return students;
    }

    /**
     * Checks if a specific room has available capacity.
     *
     * @param roomId the ID of the room
     * @return true if the room has capacity, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean isRoomAvailable(Long roomId) throws AppObjectNotFoundException {
        LOGGER.info("Checking availability for room with ID {}", roomId);

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppObjectNotFoundException("ROOM", "Room with ID " + roomId + " not found."));

        boolean available = room.getStudents().size() < room.getRoomCapacity();
        LOGGER.info("Room with ID {} is {}available", roomId, available ? "" : "not ");
        return available;
    }
}
