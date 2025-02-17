package gr.aueb.cf.tsaousisfinal.rest;


import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.tsaousisfinal.dto.RoomReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.dto.StudentReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.service.RoomService;
import gr.aueb.cf.tsaousisfinal.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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


    /**
     * Retrieves a list of all rooms.
     *
     * @return A list of `RoomReadOnlyDTO` objects representing available rooms.
     * @throws AppObjectNotFoundException if no rooms are found.
     */
    @Operation(
            summary = "Get all rooms",
            description = "Fetches a list of all rooms available in the dorm, including room details and available beds.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of rooms",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RoomReadOnlyDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No rooms found",
                            content = @Content(mediaType = "application/json"))
            }
    )
    @SecurityRequirement(name = "bearerAuth") // Requires JWT authentication
    @GetMapping
    public ResponseEntity<List<RoomReadOnlyDTO>> getRooms(
            ) throws AppObjectNotFoundException {

        List<RoomReadOnlyDTO> roomsList = roomService.getAllRooms();
        return new ResponseEntity<>(roomsList, HttpStatus.OK);
    }


    @Operation(summary = "Get students in a specific room", description = "Fetches a list of students assigned to a given room.",
    responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved students"),
            @ApiResponse(responseCode = "404", description = "Room not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @GetMapping("/{roomId}")
    public ResponseEntity<List<StudentReadOnlyDTO>> getStudentsInRoom(@PathVariable Long roomId) throws AppObjectNotFoundException  {

            List<StudentReadOnlyDTO> studentsInRoom = studentService.getStudentsInRoom(roomId);
            return ResponseEntity.ok(studentsInRoom);

    }

}
