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

    public String giveMeWhoIsNext(String name) {
        List<Game> games = gameRepository.findAll();

        for (Game game : games) {
            if (game.getType() == GameStatus.RUNNING) {
                if (game.getPlayerOne().equals(name) || game.getPlayerTwo().equals(name)) {
                    return game.getWhoIsNext();
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


    public boolean makeMove(String name) {

        List<Game> games = gameRepository.findAll();

        for (Game game : games) {
            if (game.getType() == GameStatus.RUNNING && game.getWhoIsNext().equals(name)) {
                String otherPlayer = game.getPlayerOne().equals(name) ? game.getPlayerTwo() : game.getPlayerOne();
                game.setWhoIsNext(otherPlayer);
                return true;
            }
        }

        return false;

    }

    public boolean giveUpGame(String name) {
        List<Game> games = gameRepository.findAll();

        for (Game game : games) {
            if (game.getType() == GameStatus.RUNNING && game.getPlayerOne().equals(name)) {
                game.setType(GameStatus.WIN_TWO);
                setPlayersToBase(game);
                return true;
            }
            if (game.getType() == GameStatus.RUNNING && game.getPlayerTwo().equals(name)) {
                game.setType(GameStatus.WIN_ONE);
                setPlayersToBase(game);
                return true;
            }
        }

        return false;
    }

    private void setPlayersToBase(Game game) {
        Player playerOne = playerRepository.findByName(game.getPlayerOne());
        Player playerTwo = playerRepository.findByName(game.getPlayerTwo());
        playerOne.setType(PlayerStatus.FREE_AND_ACTIVE);
        playerTwo.setType(PlayerStatus.FREE_AND_ACTIVE);
    }
}
