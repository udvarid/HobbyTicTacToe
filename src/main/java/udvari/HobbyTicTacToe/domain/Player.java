package udvari.HobbyTicTacToe.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PlayerStatus type;

    public Player() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerStatus getType() {
        return type;
    }

    public void setType(PlayerStatus type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }
}
