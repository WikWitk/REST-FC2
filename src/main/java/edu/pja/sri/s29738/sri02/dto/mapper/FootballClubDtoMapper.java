package edu.pja.sri.s29738.sri02.dto.mapper;

import edu.pja.sri.s29738.sri02.dto.FootballClubDto;
import edu.pja.sri.s29738.sri02.dto.FootballClubInfoDto;
import edu.pja.sri.s29738.sri02.dto.PlayerDto;
import edu.pja.sri.s29738.sri02.model.FootballClub;
import edu.pja.sri.s29738.sri02.model.Player;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FootballClubDtoMapper {
    private final ModelMapper modelMapper;

    public FootballClubInfoDto convertToDtoInfo(FootballClub f) {
        return modelMapper.map(f, FootballClubInfoDto.class);
    }
    public FootballClubDto convertToDto(FootballClub f) {
        return modelMapper.map(f, FootballClubDto.class);
    }
    private FootballClub convertToEntity(FootballClubDto  dto) {
        return modelMapper.map(dto, FootballClub.class);
    }

}
