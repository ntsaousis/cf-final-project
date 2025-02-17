package gr.aueb.cf.tsaousisfinal.rest;

import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectInvalidArgumentException;
import gr.aueb.cf.tsaousisfinal.core.exceptions.ValidationException;
import gr.aueb.cf.tsaousisfinal.dto.StudentInsertDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.dto.WardenInsertDTO;
import gr.aueb.cf.tsaousisfinal.dto.WardenReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.service.StudentService;
import gr.aueb.cf.tsaousisfinal.service.WardenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {

    private final StudentService studentService;
    private final WardenService wardenService;


    @Operation(
            summary = "Register a new student",
            description = "Creates a new student and returns the created object. This endpoint is public and does not require authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created successfully"),
            @ApiResponse(responseCode = "409", description = "Conflict - Student already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input", content = @Content)
    })
    // Endpoint για φοιτητές
    @PostMapping("/student")
    public ResponseEntity<StudentReadOnlyDTO> registerStudent(@Valid @RequestBody StudentInsertDTO studentInsertDTO, BindingResult bindingResult) throws AppObjectAlreadyExists, IOException, ValidationException {

        if (bindingResult.hasErrors()) {
            throw  new ValidationException(bindingResult);
        }

        StudentReadOnlyDTO registeredStudent = studentService.createStudent(studentInsertDTO);
            return new ResponseEntity<>(registeredStudent, HttpStatus.CREATED);


    }


    @Operation(
            summary = "Register a new warden",
            description = "Creates a new warden and returns the created object. This endpoint is public and does not require authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created successfully"),
            @ApiResponse(responseCode = "409", description = "Conflict - Student already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input", content = @Content)
    })

    // Endpoint για επιμελητές
    @PostMapping("/warden")
    public ResponseEntity<WardenReadOnlyDTO> registerWarden(@Valid @RequestBody WardenInsertDTO wardenInsertDTO, BindingResult bindingResult) throws AppObjectAlreadyExists
        , AppObjectInvalidArgumentException, ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
         WardenReadOnlyDTO registeredWarden = wardenService.createWarden(wardenInsertDTO);
            return new ResponseEntity<>(registeredWarden, HttpStatus.CREATED);


    }
}






