package gr.aueb.cf.tsaousisfinal.rest;


import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectInvalidArgumentException;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.tsaousisfinal.dto.RoomAssignmentDTO;
import gr.aueb.cf.tsaousisfinal.dto.RoomReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.dto.WardenInsertDTO;
import gr.aueb.cf.tsaousisfinal.dto.WardenReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.service.WardenService;
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
