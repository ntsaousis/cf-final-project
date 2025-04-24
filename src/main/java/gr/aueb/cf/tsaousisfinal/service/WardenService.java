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



@Service
@RequiredArgsConstructor
public class WardenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WardenService.class);

    private final RoomRepository roomRepository;
    private final RoomService roomService;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final WardenRepository wardenRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Assigns a student to a specific room.
     *
     * @param studentId the ID of the student
     * @param roomId    the name of the room
     * @return RoomReadOnlyDTO with updated room details
     */
    @Transactional
    public RoomReadOnlyDTO assignStudent(Long studentId, Long roomId)
            throws AppObjectNotFoundException, AppObjectAlreadyExists {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppObjectNotFoundException("ROOM", "Room with ID " + roomId + " not found."));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppObjectNotFoundException("STUDENT", "Student with ID " + studentId + " not found."));

        if (student.getRoom() != null) {
            throw new AppObjectAlreadyExists("STUDENT", "Student with ID " + studentId + " is already assigned to a room.");
        }

        if (!roomService.isRoomAvailable(roomId)) {
            throw new AppObjectAlreadyExists("ROOM", "Room with ID " + roomId + " is already full.");
        }

        student.setRoom(room);
        room.getStudents().add(student);

        studentRepository.save(student);
        roomRepository.save(room);

        return mapper.mapToReadOnlyRoomDTO(room);
    }


    @Transactional
    public WardenReadOnlyDTO createWarden(WardenInsertDTO wardenInsertDTO) throws AppObjectAlreadyExists {
        // Check if username already exists
        if (userRepository.findByUsername(wardenInsertDTO.getUser().getUsername()).isPresent()) {
            throw new AppObjectAlreadyExists("WARDEN", "Warden with username: " + wardenInsertDTO.getUser().getUsername() + " already exists.");
        }

        // Check if VAT already exists
        if (userRepository.findByVat(wardenInsertDTO.getUser().getVat()).isPresent()) {
            throw new AppObjectAlreadyExists("WARDEN", "Warden with VAT: " + wardenInsertDTO.getUser().getVat() + " already exists");
        }

        if (userRepository.findByEmail(wardenInsertDTO.getUser().getEmail()).isPresent()) {
            throw new AppObjectAlreadyExists("WARDEN", "Warden with email: " + wardenInsertDTO.getUser().getEmail() + " already exists");
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
     */
    @Transactional
    public StudentReadOnlyDTO removeStudentFromRoom(Long studentId) throws AppObjectInvalidArgumentException, AppObjectNotFoundException {
        LOGGER.info("Warden removing student with ID {}", studentId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppObjectNotFoundException("STUDENT", "Student with ID " + studentId + " not found."));

        if (student.getRoom() == null) {
            LOGGER.warn("Student with ID {} is not assigned to any room.", studentId);
            throw new AppObjectInvalidArgumentException("STUDENT", "Student with ID " + studentId + " is not assigned to any room.");
        }

        Room room = student.getRoom();
        LOGGER.info("Removing student {} from room {}", studentId, room.getId());

        room.getStudents().remove(student);
        student.setRoom(null);
        studentRepository.save(student);

        LOGGER.info("Student with ID {} successfully removed from room", studentId);
        return mapper.mapToStudentReadOnlyDTO(student);
    }


    /**
     * Deletes a student permanently from the database.
     * @param studentId The ID of the student to be deleted.
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteStudent(Long studentId) throws AppObjectNotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppObjectNotFoundException("STUDENT", "Student not found with ID: " + studentId));

        studentRepository.delete(student);
    }

}
