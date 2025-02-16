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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);
    private final RoomService roomService;
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<RoomReadOnlyDTO>> getRooms(
            ) throws AppObjectNotFoundException {

        List<RoomReadOnlyDTO> roomsList = roomService.getAllRooms();
        return new ResponseEntity<>(roomsList, HttpStatus.OK);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<List<StudentReadOnlyDTO>> getStudentsByRoom(@PathVariable Long roomId) throws AppObjectNotFoundException  {

            List<StudentReadOnlyDTO> studentsInRoom = studentService.getStudentsInRoom(roomId);
            return ResponseEntity.ok(studentsInRoom);

    }

}
