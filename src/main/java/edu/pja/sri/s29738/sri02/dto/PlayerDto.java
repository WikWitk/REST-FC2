package edu.pja.sri.s29738.sri02.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PlayerDto {
    private Long id;
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 255)
    private String lastname;
    private LocalDate birthDate;
    private String position;
    private String club;

}
