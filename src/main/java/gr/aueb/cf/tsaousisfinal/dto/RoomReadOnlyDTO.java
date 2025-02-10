package gr.aueb.cf.tsaousisfinal.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomReadOnlyDTO {


    private String roomName;
    private int roomCapacity;
    private Long roomId;

    private Boolean available;
}
