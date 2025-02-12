package gr.aueb.cf.tsaousisfinal.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomAssignmentDTO {

    @NotNull(message = "StudentId cannot be null")
    private Long studentId;

    @NotNull(message = "RoomId cannot be null")
    private Long roomId;
}
