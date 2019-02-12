package udvari.HobbyTicTacToe.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TicTacToeGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<TicTacToeMove> moves = new ArrayList<>();

    private String playerOne;

    private String playerTwo;

    private LocalDateTime timeStamp;

    private boolean running;

    @Enumerated(EnumType.STRING)
    private TicTacToeType type;

    public TicTacToeGame() {}

    public Long getId() {
        return id;
    }

    public TicTacToeGame(String playerOne, String playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.running = true;
        this.type = TicTacToeType.RUNNING;
        this.timeStamp = LocalDateTime.now();
    }

    public List<TicTacToeMove> getMoves() {
        return moves;
    }

    public void setMoves(List<TicTacToeMove> moves) {
        this.moves = moves;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(String playerOne) {
        this.playerOne = playerOne;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(String playerTwo) {
        this.playerTwo = playerTwo;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public TicTacToeType getType() {
        return type;
    }

    public void setType(TicTacToeType type) {
        this.type = type;
    }
}
