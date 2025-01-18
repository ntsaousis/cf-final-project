package gr.aueb.cf.tsaousisfinal.dto;

import gr.aueb.cf.tsaousisfinal.core.enums.RequestStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ComplaintInsertDTO {

    @NotBlank(message = "Complaint must have a title")
    @Size(max = 30)
    private String Title;

    @NotBlank(message = "Please add a description")
    @Size(max = 300)
    private String description;

    private Long studentId;

    private Long roomId;


}
