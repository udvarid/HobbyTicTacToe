package udvari.HobbyTicTacToe.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private Player challenger;

    @OneToOne
    private Player challenged;

    private LocalDateTime timeStamp;

    @Enumerated(EnumType.STRING)
    private GameType gameType;

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

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}
