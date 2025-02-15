package gr.aueb.cf.tsaousisfinal.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserUpdateDTO {

    @Email(message = "Invalid email format")
    @NotNull(message = "Email cannot be empty")
    private String email;
}
