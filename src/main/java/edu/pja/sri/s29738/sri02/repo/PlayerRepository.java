package edu.pja.sri.s29738.sri02.repo;

import edu.pja.sri.s29738.sri02.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    List<Player> findAll();

    @Query("select fc.players from FootballClub as fc where fc.id = :footballclubId")
    List<Player> findPlayersByFootballClubId(@PathVariable Long footballclubId);
}
