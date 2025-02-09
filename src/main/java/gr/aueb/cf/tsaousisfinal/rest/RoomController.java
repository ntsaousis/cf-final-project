package gr.aueb.cf.tsaousisfinal.rest;


import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.tsaousisfinal.dto.RoomAssignmentDTO;
import gr.aueb.cf.tsaousisfinal.dto.RoomReadOnlyDTO;
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
