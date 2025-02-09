package gr.aueb.cf.tsaousisfinal.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomAssignmentDTO {


    private Long studentId;
    private Long roomId;
}
