package gr.aueb.cf.tsaousisfinal.rest;

import gr.aueb.cf.tsaousisfinal.core.exceptions.*;
import gr.aueb.cf.tsaousisfinal.dto.StudentInsertDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentUpdateDTO;
import gr.aueb.cf.tsaousisfinal.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentRestController {

    private final StudentService studentService;


    @Operation(
            summary = "Get all students",
            description = "Retrieves a list of all registered students. Requires authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of student list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentReadOnlyDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User must be authenticated",
                    content = @Content(schema = @Schema(implementation = AppObjectNotAuthorizedException.class)))
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<StudentReadOnlyDTO>> getAllStudents() {
        List<StudentReadOnlyDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @Operation(
            summary = "Get student by ID",
            description = "Fetches student details based on the given ID. Requires authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student found"),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth") // Only required for this endpoint
    @GetMapping("/{id}")
    public ResponseEntity<StudentReadOnlyDTO> getStudentById(@PathVariable Long id) throws AppObjectNotFoundException {

        StudentReadOnlyDTO student = studentService.getStudentById(id);

         return new ResponseEntity<>(student, HttpStatus.OK);

    }


    @Operation(
            summary = "Update student details",
            description = "Updates the student details based on the provided ID. Requires authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated successfully",
                    content = @Content(schema = @Schema(implementation = StudentReadOnlyDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Validation failed",
                    content = @Content(schema = @Schema(implementation = ValidationException.class))),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content(schema = @Schema(implementation = AppObjectNotFoundException.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User must be authenticated",
                    content = @Content(schema = @Schema(implementation = AppObjectNotAuthorizedException.class)))
    })
    @SecurityRequirement(name = "bearerAuth") // Requires JWT authentication
    @PutMapping("/{id}")
    public ResponseEntity<StudentReadOnlyDTO> updateStudent(@PathVariable Long id,
                                                            @Valid @RequestBody StudentUpdateDTO studentUpdateDTO,
                                                            BindingResult bindingResult)
            throws ValidationException, AppObjectNotFoundException, AppObjectInvalidArgumentException,AppObjectAlreadyExists {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        StudentReadOnlyDTO updatedStudent = studentService.updateStudent(id, studentUpdateDTO);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);

    }

}






