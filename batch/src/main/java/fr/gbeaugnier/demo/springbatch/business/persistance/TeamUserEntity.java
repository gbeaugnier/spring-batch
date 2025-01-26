package fr.gbeaugnier.demo.springbatch.business.persistance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Table(name = "t_team_user")
@Getter @Setter
public class TeamUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamId;
    private String firstname;
    private String lastname;

}
