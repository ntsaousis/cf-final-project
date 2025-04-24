package gr.aueb.cf.tsaousisfinal.authentication;


import gr.aueb.cf.tsaousisfinal.core.exceptions.AppObjectNotAuthorizedException;
import gr.aueb.cf.tsaousisfinal.dto.AuthenticationRequestDTO;
import gr.aueb.cf.tsaousisfinal.dto.AuthenticationResponseDTO;
import gr.aueb.cf.tsaousisfinal.model.User;
import gr.aueb.cf.tsaousisfinal.repositories.UserRepository;
import gr.aueb.cf.tsaousisfinal.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO dto) throws AppObjectNotAuthorizedException {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new AppObjectNotAuthorizedException("User", "User not authorized"));

        String token = jwtService.generateToken(authentication.getName(), user.getRole().name(), user.getId());
        return new AuthenticationResponseDTO(user.getFirstName(), user.getLastName(), token);
    }
}
