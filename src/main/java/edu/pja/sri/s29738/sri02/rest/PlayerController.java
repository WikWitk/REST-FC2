package edu.pja.sri.s29738.sri02.rest;

import edu.pja.sri.s29738.sri02.dto.PlayerDto;
import edu.pja.sri.s29738.sri02.model.Player;
import edu.pja.sri.s29738.sri02.repo.PlayerRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/players")

public class PlayerController {
    private PlayerRepository playerRepository;
    private ModelMapper modelMapper;

    public PlayerController(PlayerRepository playerRepository, ModelMapper modelMapper){
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
    }

    private PlayerDto convertToDto(Player p) {
        return modelMapper.map(p, PlayerDto.class);
    }

    private Player convertToEntity(PlayerDto dto) {
        return modelMapper.map(dto, Player.class);
    }
    @GetMapping
    public ResponseEntity<Collection<PlayerDto>> getPlayers() {
        List<Player> allPlayers = playerRepository.findAll();
        List<PlayerDto> result = allPlayers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/{plrIds}")
        public ResponseEntity<PlayerDto>
    getPlayerById(@PathVariable Long plrIds) {
            Optional<Player> plr = playerRepository.findById(plrIds);
        if(plr.isPresent()) {
            PlayerDto playerDto = convertToDto(plr.get());
            return new ResponseEntity<>(playerDto, HttpStatus.OK); }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } }
    @PostMapping("/players")
    public ResponseEntity saveNewPlayer(@Valid @RequestBody PlayerDto plr) {
        Player entity = convertToEntity(plr);
        playerRepository.save(entity);
        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{plrId}")
                .buildAndExpand(entity.getId())
                .toUri();
        headers.add("Location", location.toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
    @PutMapping("/{plrId}")
    public ResponseEntity updatePlayer(@PathVariable Long plrId, @Valid @RequestBody PlayerDto playerDto) {
        Optional<Player> currentPlr = playerRepository.findById(plrId);
        if(currentPlr.isPresent()) {
            playerDto.setId(plrId);
            Player entity = convertToEntity(playerDto); playerRepository.save(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } }

    @DeleteMapping("/{plrId}")
    public ResponseEntity deletePlayer(@PathVariable Long plrId) {
        playerRepository.deleteById(plrId);
        return new ResponseEntity(HttpStatus.NO_CONTENT); }

}
