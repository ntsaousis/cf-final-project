package gr.aueb.cf.tsaousisfinal.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {
    private String firstname;
    private String lastname;
    private String token;
}
