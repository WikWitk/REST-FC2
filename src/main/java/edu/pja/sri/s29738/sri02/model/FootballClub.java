package edu.pja.sri.s29738.sri02.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FootballClub {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    private Long id;
    private String name;

    @OneToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Player> players;
}
