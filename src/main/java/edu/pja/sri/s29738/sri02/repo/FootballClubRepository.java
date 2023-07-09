package edu.pja.sri.s29738.sri02.repo;

import edu.pja.sri.s29738.sri02.model.FootballClub;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FootballClubRepository extends CrudRepository<FootballClub, Long> {
    List<FootballClub> findAll();
    @Query("from FootballClub as fc left join fetch fc.players where fc.id=:footballclubId")
    Optional<FootballClub> getFootballClubInfoById(@Param("footballclubId") Long footballClubId);
}
