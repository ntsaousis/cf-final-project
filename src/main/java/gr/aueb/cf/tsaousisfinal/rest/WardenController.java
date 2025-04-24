package gr.aueb.cf.tsaousisfinal.rest;


import gr.aueb.cf.tsaousisfinal.core.exceptions.*;
import gr.aueb.cf.tsaousisfinal.dto.ResponseMessageDTO;
import gr.aueb.cf.tsaousisfinal.dto.RoomAssignmentDTO;
import gr.aueb.cf.tsaousisfinal.dto.RoomReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.service.WardenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wardens")
@RequiredArgsConstructor
public class WardenController {

    private final WardenService wardenService;




    /**
     * Hard delete a student (permanently removes from database)
     */
    @Operation(
            summary = "Hard deletes a student",
            description = "Permanently removes a student from the database. Requires Warden authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student successfully deleted",
                    content = @Content(schema = @Schema(implementation = ResponseMessageDTO.class))),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content(schema = @Schema(implementation = AppObjectNotFoundException.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - Only Wardens can delete students",
                    content = @Content(schema = @Schema(implementation = AppObjectNotAuthorizedException.class)))
    })
    @SecurityRequirement(name = "bearerAuth") // Requires authentication
    @DeleteMapping("/students/{id}")
    public ResponseEntity<ResponseMessageDTO> deleteStudent(@PathVariable Long id) throws AppObjectNotFoundException {
        wardenService.deleteStudent(id);
        return ResponseEntity.ok(new ResponseMessageDTO("STUDENT ", "Student permanently deleted."));
    }



    @Operation(
            summary = "Assign a student to a room",
            description = "Assigns a student to a specified room. Ensures that the student is not already assigned to another room.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student successfully assigned to the room",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RoomReadOnlyDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request (e.g., student already assigned)",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Student or room not found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/assign")
    public ResponseEntity<?> assignStudentToRoom(@Valid @RequestBody RoomAssignmentDTO request) throws
            AppObjectNotFoundException, AppObjectAlreadyExists {
        System.out.println("Received assign request: " + request.getStudentId());


        RoomReadOnlyDTO room = wardenService.assignStudent(request.getStudentId(), request.getRoomId());
        return ResponseEntity.ok(room);

    }


    @Operation(
            summary = "Unassign a student from a room",
            description = "Removes the student from their assigned room and updates their status accordingly.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student successfully unassigned",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StudentReadOnlyDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request (e.g., student not assigned to any room)",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Student not found",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/unassign/{id}")
    public ResponseEntity<StudentReadOnlyDTO> unassignStudent(@PathVariable Long id) throws
            AppObjectInvalidArgumentException, AppObjectNotFoundException , AppServerException {
        StudentReadOnlyDTO removedStudent =  wardenService.removeStudentFromRoom(id);
        return new ResponseEntity<>(removedStudent,HttpStatus.OK);
    }

}
