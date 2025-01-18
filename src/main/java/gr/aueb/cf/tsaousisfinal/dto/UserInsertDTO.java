package gr.aueb.cf.tsaousisfinal.dto;

import gr.aueb.cf.tsaousisfinal.core.enums.GenderType;
import gr.aueb.cf.tsaousisfinal.core.enums.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInsertDTO {

    @NotBlank(message = "First name is required.")
    @Size(max = 50, message = "First name must not exceed 50 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(max = 50, message = "Last name must not exceed 50 characters.")
    private String lastName;

    @NotBlank(message = "Username is required.")
    @Size(max = 50, message = "Username must not exceed 50 characters.")
    private String username;

    @NotBlank(message = "Vat is required")
    @Size(min = 11, max = 11)
    private String vat;

    @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$",
            message = "Invalid Password")
    private String password;

    @NotNull(message = "Gender type is required")
    private GenderType genderType;

    @NotNull(message = "Is active must not be null")
    private Boolean isActive;

    @NotNull(message = "Role type is required")
    private RoleType role;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;
}
