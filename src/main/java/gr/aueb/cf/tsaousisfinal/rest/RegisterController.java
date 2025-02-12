package gr.aueb.cf.tsaousisfinal.rest;

import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectInvalidArgumentException;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppServerException;
import gr.aueb.cf.tsaousisfinal.core.exceptions.ValidationException;
import gr.aueb.cf.tsaousisfinal.dto.StudentInsertDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.dto.WardenInsertDTO;
import gr.aueb.cf.tsaousisfinal.dto.WardenReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.service.StudentService;
import gr.aueb.cf.tsaousisfinal.service.WardenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;




  @RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {

    private final StudentService studentService;
    private final WardenService wardenService;

    // Endpoint για φοιτητές
    @PostMapping("/student")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody StudentInsertDTO studentInsertDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            StudentReadOnlyDTO registeredStudent = studentService.createStudent(studentInsertDTO);
            return new ResponseEntity<>(registeredStudent, HttpStatus.CREATED);
        } catch (AppObjectAlreadyExists e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint για επιμελητές
    @PostMapping("/warden")
    public ResponseEntity<WardenReadOnlyDTO> registerWarden(@Valid @RequestBody WardenInsertDTO wardenInsertDTO, BindingResult bindingResult) throws AppObjectAlreadyExists
        , AppObjectInvalidArgumentException, AppServerException, ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
         WardenReadOnlyDTO registeredWarden = wardenService.createWarden(wardenInsertDTO);
            return new ResponseEntity<>(registeredWarden, HttpStatus.CREATED);


    }
}






