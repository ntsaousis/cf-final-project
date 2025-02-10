package gr.aueb.cf.tsaousisfinal.rest;


import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.tsaousisfinal.dto.RoomAssignmentDTO;
import gr.aueb.cf.tsaousisfinal.dto.RoomReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.service.RoomService;
import gr.aueb.cf.tsaousisfinal.service.WardenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<List<StudentReadOnlyDTO>> getStudentsByRoom(@PathVariable Long roomId) {

        try {
            List<StudentReadOnlyDTO> studentsInRoom = wardenService.getStudentsInRoom(roomId);
            return ResponseEntity.ok(studentsInRoom);
        } catch (AppObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignStudentToRoom(@RequestBody RoomAssignmentDTO request) {
        try {
            RoomReadOnlyDTO room = wardenService.assignStudentToRoom(request.getStudentId(), request.getRoomId());
            return ResponseEntity.ok(room);
        } catch (AppObjectNotFoundException | AppObjectAlreadyExists e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }





}
