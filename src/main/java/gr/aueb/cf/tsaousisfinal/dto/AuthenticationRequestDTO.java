package gr.aueb.cf.tsaousisfinal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDTO {

    @NotNull
    private String username;
    @NotNull
    private String password;
}
