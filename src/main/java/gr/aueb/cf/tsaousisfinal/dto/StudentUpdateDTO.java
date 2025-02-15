package gr.aueb.cf.tsaousisfinal.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentUpdateDTO {

   @Valid
   private UserUpdateDTO user;
}
