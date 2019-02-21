package udvari.HobbyTicTacToe.domain;

import javax.persistence.*;

@Entity
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private Player challenger;

    @OneToOne
    private Player challenged;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getChallenger() {
        return challenger;
    }

    public void setChallenger(Player challenger) {
        this.challenger = challenger;
    }

    public Player getChallenged() {
        return challenged;
    }

    public void setChallenged(Player challenged) {
        this.challenged = challenged;
    }
}
