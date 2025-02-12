package gr.aueb.cf.tsaousisfinal.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WardenInsertDTO {


    @Valid
    @NotNull(message = "User details are required")
    private UserInsertDTO user;
}
