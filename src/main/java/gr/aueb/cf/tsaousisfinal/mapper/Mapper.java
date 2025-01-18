package gr.aueb.cf.tsaousisfinal.mapper;


import gr.aueb.cf.tsaousisfinal.dto.StudentInsertDTO;
import gr.aueb.cf.tsaousisfinal.dto.UserInsertDTO;
import gr.aueb.cf.tsaousisfinal.dto.WardenInsertDTO;
import gr.aueb.cf.tsaousisfinal.model.User;
import gr.aueb.cf.tsaousisfinal.model.Warden;
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
        student.setIsActive(studentInsertDTO.getIsActive());

        User user = new User();
        UserInsertDTO userInsertDTO = studentInsertDTO.getUser();
        user.setFirstName(userInsertDTO.getFirstName());
        user.setLastName(userInsertDTO.getLastName());
        user.setUsername(userInsertDTO.getUsername());
        user.setVat(userInsertDTO.getVat());
        user.setPassword(userInsertDTO.getPassword());
        user.setGenderType(userInsertDTO.getGenderType());
        user.setIsActive(userInsertDTO.getIsActive());
        user.setRole(userInsertDTO.getRole());
        user.setDateOfBirth(userInsertDTO.getDateOfBirth());
        student.setUser(user);
        return student;
    }

    public Warden mapToWardenEntity(WardenInsertDTO wardenInsertDTO) {
        Warden warden = new Warden();
        warden.setIsActive(wardenInsertDTO.getIsActive());

        User user = new User();
        UserInsertDTO userInsertDTO = wardenInsertDTO.getUser();
        user.setFirstName(userInsertDTO.getFirstName());
        user.setLastName(userInsertDTO.getLastName());
        user.setUsername(userInsertDTO.getUsername());
        user.setPassword(userInsertDTO.getPassword());
        user.setVat(userInsertDTO.getVat());
        user.setGenderType(userInsertDTO.getGenderType());
        user.setIsActive(userInsertDTO.getIsActive());
        user.setRole(userInsertDTO.getRole());
        user.setDateOfBirth(userInsertDTO.getDateOfBirth());
        warden.setUser(user);
        return warden;
    }









}
