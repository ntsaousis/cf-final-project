package gr.aueb.cf.tsaousisfinal.rest;


import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.tsaousisfinal.dto.RoomAssignmentDTO;
import gr.aueb.cf.tsaousisfinal.dto.RoomReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.dto.WardenInsertDTO;
import gr.aueb.cf.tsaousisfinal.dto.WardenReadOnlyDTO;
import gr.aueb.cf.tsaousisfinal.service.WardenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wardens")
@RequiredArgsConstructor
public class WardenController {

    private final WardenService wardenService;

    @PostMapping("/register")
    public ResponseEntity<WardenReadOnlyDTO> createWarden(@RequestBody WardenInsertDTO wardenInsertDTO) {
        try {
            WardenReadOnlyDTO createdWarden = wardenService.createWarden(wardenInsertDTO);
            return new ResponseEntity<>(createdWarden, HttpStatus.CREATED);
        } catch (AppObjectAlreadyExists e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }





}
