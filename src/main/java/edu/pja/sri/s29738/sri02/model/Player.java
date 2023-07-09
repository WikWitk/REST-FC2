package edu.pja.sri.s29738.sri02.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Player {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    private Long id;

    private String firstName;

    private String lastName;
    private LocalDate birthDate;
    private String position;
    @ManyToOne
    @JoinColumn(name="player_id")
    private FootballClub player;

}
