package edu.pja.sri.s29738.sri02.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FootballClubInfoDto extends RepresentationModel<FootballClubInfoDto> {
    private Long id;
    private String name;
    private Set<PlayerDto> players;

}
