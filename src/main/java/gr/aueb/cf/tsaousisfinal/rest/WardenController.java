package gr.aueb.cf.tsaousisfinal.rest;


import gr.aueb.cf.tsaousisfinal.core.exceptions.*;
import gr.aueb.cf.tsaousisfinal.dto.ResponseMessageDTO;
import gr.aueb.cf.tsaousisfinal.dto.RoomAssignmentDTO;
import gr.aueb.cf.tsaousisfinal.dto.RoomReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.service.StudentService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wardens")
@RequiredArgsConstructor
public class WardenController {

    private final WardenService wardenService;
    private final StudentService studentService;



    /**
     * Hard delete a student (permanently removes from database)
     */
    @Operation(
            summary = "Hard delete a student",
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


    @PostMapping("/assign")
    public ResponseEntity<?> assignStudentToRoom(@Valid @RequestBody RoomAssignmentDTO request) throws
            AppObjectNotFoundException, AppObjectAlreadyExists {
        System.out.println("Received assign request: " + request.getStudentId());


        RoomReadOnlyDTO room = wardenService.assignStudent(request.getStudentId(), request.getRoomId());
        return ResponseEntity.ok(room);

    }

    @PutMapping("/unassign/{id}")
    public ResponseEntity<StudentReadOnlyDTO> unassignStudent(@PathVariable Long id) throws
            AppObjectInvalidArgumentException, AppObjectNotFoundException , AppServerException {
        StudentReadOnlyDTO removedStudent =  wardenService.removeStudentFromRoom(id);
        return new ResponseEntity<>(removedStudent,HttpStatus.OK);
    }


//    @PostMapping("/assign-student-to-room")
//    public ResponseEntity<?> assignStudentToRoom(@Valid @RequestBody RoomAssignmentDTO request) {
//        System.out.println("Received assign request: " + request);
//        if (request.getRoomId() == null || request.getStudentId() == null) {
//            return ResponseEntity.badRequest().body("Invalid data: Missing roomId or studentId");
//        }
//
//        try {
//            RoomReadOnlyDTO room = wardenService.assignStudentToRoom(request.getStudentId(), request.getRoomId());
//            return ResponseEntity.ok(room);
//        } catch (AppObjectNotFoundException | AppObjectAlreadyExists e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

//    @PutMapping("/{id}/unassign")
//    @PreAuthorize("hasRole('WARDEN')")
//    public ResponseEntity<Void> unassignStudent(@PathVariable Long id) throws AppObjectInvalidArgumentException, AppObjectNotFoundException {
//        wardenService.removeStudentFromRoom(id);
//        return ResponseEntity.noContent().build();
//    }







}
