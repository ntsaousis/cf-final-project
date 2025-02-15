package gr.aueb.cf.tsaousisfinal.dto;


import gr.aueb.cf.tsaousisfinal.core.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReadOnlyDTO {


    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String role;
    private String Email;
}
