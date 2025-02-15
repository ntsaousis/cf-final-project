package gr.aueb.cf.tsaousisfinal.mapper;


import gr.aueb.cf.tsaousisfinal.dto.*;
import gr.aueb.cf.tsaousisfinal.model.User;
import gr.aueb.cf.tsaousisfinal.model.Warden;
import gr.aueb.cf.tsaousisfinal.model.static_data.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import gr.aueb.cf.tsaousisfinal.model.Student;

@Component
@RequiredArgsConstructor
public class Mapper {

     private final PasswordEncoder passwordEncoder;

    public Student mapToStudentEntity(StudentInsertDTO studentInsertDTO) {
        Student student = new Student();
//        student.setIsActive(studentInsertDTO.getIsActive());

        User user = new User();

        UserInsertDTO userInsertDTO = studentInsertDTO.getUser();
        user.setIsActive(true);
        user.setFirstName(userInsertDTO.getFirstName());
        user.setLastName(userInsertDTO.getLastName());
        user.setUsername(userInsertDTO.getUsername());
        user.setEmail(userInsertDTO.getEmail());
        user.setVat(userInsertDTO.getVat());
        user.setPassword(userInsertDTO.getPassword());
        user.setGenderType(userInsertDTO.getGenderType());

        user.setRole(userInsertDTO.getRole());

        student.setUser(user);
        return student;
    }

    public Warden mapToWardenEntity(WardenInsertDTO wardenInsertDTO) {
        Warden warden = new Warden();
//        warden.setIsActive(wardenInsertDTO.getIsActive());

        User user = new User();
        UserInsertDTO userInsertDTO = wardenInsertDTO.getUser();
        user.setIsActive(true);
        user.setFirstName(userInsertDTO.getFirstName());
        user.setLastName(userInsertDTO.getLastName());
        user.setUsername(userInsertDTO.getUsername());
        user.setPassword(userInsertDTO.getPassword());
        user.setEmail(userInsertDTO.getEmail());
        user.setVat(userInsertDTO.getVat());
        user.setGenderType(userInsertDTO.getGenderType());
        user.setRole(userInsertDTO.getRole());

        warden.setUser(user);
        return warden;
    }

    public StudentReadOnlyDTO mapToStudentReadOnlyDTO(Student student) {
        StudentReadOnlyDTO studentReadOnlyDTO = new StudentReadOnlyDTO();

        studentReadOnlyDTO.setId(student.getId());
//        studentReadOnlyDTO.setIsActive(student.getIsActive());
        studentReadOnlyDTO.setUuid(student.getUuid());

        UserReadOnlyDTO userDTO = new UserReadOnlyDTO();
        userDTO.setUserId(student.getUser().getId());
        userDTO.setFirstName(student.getUser().getFirstName());
        userDTO.setLastName(student.getUser().getLastName());
        userDTO.setUsername(student.getUser().getUsername());
        userDTO.setRole(student.getUser().getRole().name());
        userDTO.setEmail(student.getUser().getEmail());

        studentReadOnlyDTO.setUser(userDTO);
        if (student.getRoom() != null) {
            RoomReadOnlyDTO roomDTO = new RoomReadOnlyDTO();
            roomDTO.setRoomId(student.getRoom().getId());
            roomDTO.setRoomName(student.getRoom().getRoomName());
            roomDTO.setRoomCapacity(student.getRoom().getRoomCapacity());
            roomDTO.setAvailable(student.getRoom().isAvailable());
            studentReadOnlyDTO.setRoom(roomDTO);
        } else {
            studentReadOnlyDTO.setRoom(null); // Ή άφησε το πεδίο `room` κενό
        }

        return studentReadOnlyDTO;




    }

    public WardenReadOnlyDTO mapToWardenReadOnlyDTO(Warden warden) {
        WardenReadOnlyDTO wardenReadOnlyDTO = new WardenReadOnlyDTO();
        wardenReadOnlyDTO.setId(warden.getId());
//        wardenReadOnlyDTO.setIsActive(warden.getIsActive());
        wardenReadOnlyDTO.setUuid(warden.getUuid());

        UserReadOnlyDTO userDTO = new UserReadOnlyDTO();
        userDTO.setUserId(warden.getUser().getId());
        userDTO.setFirstName(warden.getUser().getFirstName());
        userDTO.setLastName(warden.getUser().getLastName());
        userDTO.setUsername(warden.getUser().getUsername());
        userDTO.setRole(warden.getUser().getRole().name());
        wardenReadOnlyDTO.setUser(userDTO);
        return wardenReadOnlyDTO;
    }

    public RoomReadOnlyDTO mapToReadOnlyRoomDTO(Room room) {
        RoomReadOnlyDTO roomReadOnlyDTO = new RoomReadOnlyDTO();

        roomReadOnlyDTO.setRoomId(room.getId());
        roomReadOnlyDTO.setRoomName(room.getRoomName());
        roomReadOnlyDTO.setRoomCapacity(room.getRoomCapacity());
        roomReadOnlyDTO.setAvailable(room.isAvailable());
        return roomReadOnlyDTO;
    }

}
