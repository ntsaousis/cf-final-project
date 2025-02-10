package gr.aueb.cf.tsaousisfinal.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentReadOnlyDTO {

    private Long id;
    private String uuid;


    private UserReadOnlyDTO user;

    private RoomReadOnlyDTO room;
}
