package udvari.HobbyTicTacToe.domain;

import javax.persistence.*;

@Entity
public class TicTacToeMove {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TicTacToeGame game;

    @Enumerated(EnumType.STRING)
    private PlayerType player;

    private Integer moveX;

    private Integer moveY;

    public TicTacToeMove() {}

    public TicTacToeMove(TicTacToeGame game, PlayerType player, Integer moveX, Integer moveY) {
        this.game = game;
        this.player = player;
        this.moveX = moveX;
        this.moveY = moveY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicTacToeGame getGame() {
        return game;
    }

    public void setGame(TicTacToeGame game) {
        this.game = game;
    }

    public PlayerType getPlayer() {
        return player;
    }

    public void setPlayer(PlayerType player) {
        this.player = player;
    }

    public Integer getMoveX() {
        return moveX;
    }

    public void setMoveX(Integer moveX) {
        this.moveX = moveX;
    }

    public Integer getMoveY() {
        return moveY;
    }

    public void setMoveY(Integer moveY) {
        this.moveY = moveY;
    }
}
