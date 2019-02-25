package udvari.HobbyTicTacToe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udvari.HobbyTicTacToe.domain.Game;
import udvari.HobbyTicTacToe.domain.GameStatus;
import udvari.HobbyTicTacToe.domain.Player;
import udvari.HobbyTicTacToe.domain.PlayerStatus;
import udvari.HobbyTicTacToe.repository.GameRepository;
import udvari.HobbyTicTacToe.repository.PlayerRepository;

import java.util.List;

@Service
@Transactional
public class GameService {

    private GameRepository gameRepository;
    private PlayerRepository playerRepository;


    @Autowired
    public GameService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    public String giveMePartnerName(String name) {
        List<Game> games = gameRepository.findAll();

        for (Game game : games) {
            if (game.getType() == GameStatus.RUNNING) {
                if (game.getPlayerOne().equals(name)) {
                    return game.getPlayerTwo();
                }
                if (game.getPlayerTwo().equals(name)) {
                    return game.getPlayerOne();
                }
            }
        }

        return "";
    }

    public String giveMeGameName(String name) {
        List<Game> games = gameRepository.findAll();

        for (Game game : games) {
            if (game.getType() == GameStatus.RUNNING) {
                if (game.getPlayerOne().equals(name) || game.getPlayerTwo().equals(name)) {
                    return game.getGameType().getDisplayName();
                }
            }
        }

        return "";
    }

    public void closeGame(String name) {
        List<Game> games = gameRepository.findAll();

        for (Game game : games) {
            if (game.getType() == GameStatus.RUNNING) {
                if (game.getPlayerOne().equals(name)) {
                    Player player = playerRepository.findByName(game.getPlayerTwo());
                    player.setType(PlayerStatus.FREE_AND_ACTIVE);
                    game.setType(GameStatus.BROKEN);
                    break;
                }
                if (game.getPlayerTwo().equals(name)) {
                    Player player = playerRepository.findByName(game.getPlayerOne());
                    player.setType(PlayerStatus.FREE_AND_ACTIVE);
                    game.setType(GameStatus.BROKEN);
                    break;
                }
            }
        }
    }
}
