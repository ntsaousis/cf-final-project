package gr.aueb.cf.tsaousisfinal.rest;

import gr.aueb.cf.tsaousisfinal.authentication.AuthenticationService;
import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectNotAuthorizedException;
import gr.aueb.cf.tsaousisfinal.dto.AuthenticationRequestDTO;
import gr.aueb.cf.tsaousisfinal.dto.AuthenticationResponseDTO;
import gr.aueb.cf.tsaousisfinal.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRestController.class);
    private final AuthenticationService authenticationService;


    @Operation(
            summary = "Authenticate a user",
            description = "Validates user credentials and returns a JWT token upon successful authentication.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User authenticated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
            }
    )
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO authenticationRequestDTO)
            throws AppObjectNotAuthorizedException {
        AuthenticationResponseDTO authenticationResponseDTO = authenticationService.authenticate(authenticationRequestDTO);
        LOGGER.info("User authenticated");
        return new ResponseEntity<>(authenticationResponseDTO, HttpStatus.OK);
    }

   

}