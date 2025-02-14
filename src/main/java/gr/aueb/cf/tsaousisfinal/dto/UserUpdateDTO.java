package gr.aueb.cf.tsaousisfinal.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserUpdateDTO {

    @Valid
    @NotNull
    private String email;
}
