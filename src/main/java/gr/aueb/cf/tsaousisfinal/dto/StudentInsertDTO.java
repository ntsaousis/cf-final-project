package gr.aueb.cf.tsaousisfinal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for inserting new Teacher data.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentInsertDTO {



    @NotNull(message = "User details are required")
    private UserInsertDTO user;
}
