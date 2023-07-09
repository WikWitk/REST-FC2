package edu.pja.sri.s29738.sri02.rest;

import edu.pja.sri.s29738.sri02.dto.FootballClubDto;
import edu.pja.sri.s29738.sri02.dto.FootballClubInfoDto;
import edu.pja.sri.s29738.sri02.dto.PlayerDto;
import edu.pja.sri.s29738.sri02.dto.mapper.FootballClubDtoMapper;
import edu.pja.sri.s29738.sri02.model.FootballClub;
import edu.pja.sri.s29738.sri02.model.Player;
import edu.pja.sri.s29738.sri02.repo.FootballClubRepository;
import edu.pja.sri.s29738.sri02.repo.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/footballclubs")
@RequiredArgsConstructor
public class FootballClubController {
    private final PlayerRepository playerRepository;
    private final FootballClubRepository footballClubRepository;
    private final ModelMapper modelMapper;
    private final FootballClubDtoMapper footballClubDtoMapper;



    private PlayerDto convertToDto(Player p) {
      return modelMapper.map(p, PlayerDto.class);
    }

    private Player convertToEntity(PlayerDto dto) {
        return modelMapper.map(dto, Player.class);
    }
    private FootballClub convertToEntity(FootballClubDto  dto) {
        return modelMapper.map(dto, FootballClub.class);
    }
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<FootballClubDto>> getFootballClubs() {
        List<FootballClub> allFootballClubsEntities = footballClubRepository.findAll();
        List<FootballClubDto> result = allFootballClubsEntities.stream()
                .map(footballClubDtoMapper::convertToDto)
                .collect(Collectors.toList());
        for(FootballClubDto dto : result){
            dto.add(createFootballClubSelfLink(dto.getId()));
            dto.add(createFootballClubPlayersLink(dto.getId()));

        }
        Link linkSelf = WebMvcLinkBuilder.linkTo(methodOn(FootballClubController.class).getFootballClubs()).withSelfRel();
        CollectionModel<FootballClubDto> res = CollectionModel.of(result , linkSelf);
        return new ResponseEntity<>(res, HttpStatus.OK);
  }
    @GetMapping(value = "/{footballclubId}",produces = {"application/hal+json"})
        public ResponseEntity<FootballClubInfoDto>
    getFootballClubById(@PathVariable Long fcId) {
            Optional<FootballClub> fc = footballClubRepository.findById(fcId);
        if(fc.isPresent()) {
            FootballClubInfoDto footballClubInfoDto = footballClubDtoMapper.convertToDtoInfo(fc.get());
//            Link linkSelf = new Link("http://localhost:8080/footballclubs/"+fcId);
            Link linkSelf = createFootballClubSelfLink(fcId);
            footballClubInfoDto.add(linkSelf);
            return new ResponseEntity<>(footballClubInfoDto, HttpStatus.OK); }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } }

    @GetMapping("/{footballclubId}/players")
    public ResponseEntity<Collection<PlayerDto>> getPlayersByFootballClubId(@PathVariable Long footballclubId) {
        List<Player> allPlayers = playerRepository.findPlayersByFootballClubId(footballclubId);
        List<PlayerDto> result = allPlayers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/footballclub")
    public ResponseEntity saveNewPlayer(@RequestBody FootballClubDto footballClubDto) {
        FootballClub entity = convertToEntity(footballClubDto);
        footballClubRepository.save(entity);
        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{fcId}")
                .buildAndExpand(entity.getId())
                .toUri();
        headers.add("Location", location.toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
    @PutMapping("/{fcId}")
    public ResponseEntity updateFootballClub(@PathVariable Long fcId, @RequestBody FootballClubDto footballClubDto) {
        Optional<FootballClub> currentFc = footballClubRepository.findById(fcId);
        if(currentFc.isPresent()) {
            footballClubDto.setId(fcId);
            FootballClub entity = convertToEntity(footballClubDto); footballClubRepository.save(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } }

    @DeleteMapping("/{fcId}")
    public ResponseEntity deleteFootballClub(@PathVariable Long fcId) {
        footballClubRepository.deleteById(fcId);
        return new ResponseEntity(HttpStatus.NO_CONTENT); }


        private Link createFootballClubSelfLink(Long fcId){
            Link linkSelf = WebMvcLinkBuilder.linkTo(methodOn(FootballClubController.class).getFootballClubById(fcId)).withSelfRel();
            return linkSelf;

        }
    private Link createFootballClubPlayersLink(Long fcId){
        Link linkSelf = WebMvcLinkBuilder.linkTo(methodOn(FootballClubController.class).getPlayersByFootballClubId(fcId)).withSelfRel();
        return linkSelf;

    }
    }

