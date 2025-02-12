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


    private Long roomId;
    private String roomName;
    private int roomCapacity;
    private Boolean available;
}
