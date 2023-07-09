package edu.pja.sri.s29738.sri02.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FootballClubDto extends RepresentationModel<FootballClubDto> {
    private Long id;
    private String name;


}
