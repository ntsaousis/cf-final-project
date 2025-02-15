package gr.aueb.cf.tsaousisfinal.rest;


import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectInvalidArgumentException;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppServerException;
import gr.aueb.cf.tsaousisfinal.dto.RoomAssignmentDTO;
import gr.aueb.cf.tsaousisfinal.dto.RoomReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.service.RoomService;
import gr.aueb.cf.tsaousisfinal.service.StudentService;
import gr.aueb.cf.tsaousisfinal.service.WardenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {



    private final RoomService roomService;
    private final WardenService wardenService;

    @GetMapping
    public ResponseEntity<List<RoomReadOnlyDTO>> getRooms(
            ) {

        List<RoomReadOnlyDTO> roomsList = roomService.getAllRooms();
        return new ResponseEntity<>(roomsList, HttpStatus.OK);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<List<StudentReadOnlyDTO>> getStudentsByRoom(@PathVariable Long roomId) throws AppObjectNotFoundException  {

            List<StudentReadOnlyDTO> studentsInRoom = wardenService.getStudentsInRoom(roomId);
            return ResponseEntity.ok(studentsInRoom);

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

//    @DeleteMapping"(/{studentId}")
//    public ResponseEntity<StudentReadOnlyDTO> unassignStudentFromRoom(Long studentId) {
//
//        }

}
