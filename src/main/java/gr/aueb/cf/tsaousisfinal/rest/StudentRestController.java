package gr.aueb.cf.tsaousisfinal.rest;

import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.tsaousisfinal.core.exceptions.ValidationException;
import gr.aueb.cf.tsaousisfinal.dto.StudentInsertDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentUpdateDTO;
import gr.aueb.cf.tsaousisfinal.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentRestController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentReadOnlyDTO>> getAllStudents() {
        List<StudentReadOnlyDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentReadOnlyDTO> getStudentById(@PathVariable Long id) {
        try {
            StudentReadOnlyDTO student = studentService.getStudentById(id);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (AppObjectNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<StudentReadOnlyDTO> updateStudent(@PathVariable Long id, @RequestBody StudentUpdateDTO studentUpdateDTO,
                                                            BindingResult bindingResult) throws ValidationException, AppObjectNotFoundException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        try {
            StudentReadOnlyDTO updatedStudent = studentService.updateStudent(id, studentUpdateDTO);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (AppObjectNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }


    }

}






