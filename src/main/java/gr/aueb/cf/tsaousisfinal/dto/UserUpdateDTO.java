package gr.aueb.cf.tsaousisfinal.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserUpdateDTO {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    private String email;
}
